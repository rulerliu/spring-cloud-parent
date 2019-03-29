package com.mayikt.pay.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.mayikt.base.BaseApiService;
import com.mayikt.base.BaseResponse;
import com.mayikt.pay.factory.PayStrategyFactory;
import com.mayikt.pay.mapper.PaymentChannelMapper;
import com.mayikt.pay.mapper.entity.PaymentChannelEntity;
import com.mayikt.pay.output.dto.PayMentTransacOutDTO;
import com.mayikt.pay.service.PayContextService;
import com.mayikt.pay.service.PayMentTransacInfoService;
import com.mayikt.pay.strategy.PayStrategy;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: liuwq
 * @date: 2019/3/29 0029 上午 10:24
 * @version: V1.0
 * @Copyright:该项目“基于SpringCloud2.x构建微服务电商项目”由每特教育|蚂蚁课堂版权所有，未经过允许的情况下， 私自分享视频和源码属于违法行为。
 */
@RestController
public class PayContextServiceImpl extends BaseApiService<JSONObject> implements PayContextService {

    @Autowired
    private PaymentChannelMapper paymentChannelMapper;

    @Autowired
    private PayMentTransacInfoService payMentTransacInfoService;

    @Override
    public BaseResponse<JSONObject> toPayHtml(String channelId, String payToken) {
        if (StringUtils.isBlank(channelId)) {
            return setResultError("channelId不能为空");
        }
        if (StringUtils.isBlank(payToken)) {
            return setResultError("payToken不能为空");
        }

        // 1.使用渠道id获取渠道信息 classAddres
        PaymentChannelEntity paymentChannelEntity = paymentChannelMapper.selectBychannelId(channelId);
        if (paymentChannelEntity == null) {
            return setResultError("channelId：" + channelId + "不存在");
        }
        String classAddres = paymentChannelEntity.getClassAddres();
        if (StringUtils.isBlank(classAddres)) {
            return setResultError("channelId：" + channelId + ",classAddres不能为空");
        }

        // 2.使用payToken获取支付参数
        BaseResponse<PayMentTransacOutDTO> payMentTransacOutDTO = payMentTransacInfoService
                .tokenByPayMentTransac(payToken);
        if (!isSuccess(payMentTransacOutDTO)) {
            return setResultError(payMentTransacOutDTO.getMsg());
        }

        // 3.使用工厂模式获取PayStrategy对象
        PayStrategy payStrategy = PayStrategyFactory.getPayStrategy(classAddres);
        String toPayHtml = payStrategy.toPayHtml(paymentChannelEntity, payMentTransacOutDTO.getData());
        if (StringUtils.isBlank(toPayHtml)) {
            return setResultError("toPayHtml为空");
        }

        // 4.直接返回html
        JSONObject data = new JSONObject();
        data.put("payHtml", toPayHtml);
        return setResultSuccess(data);
    }

}
