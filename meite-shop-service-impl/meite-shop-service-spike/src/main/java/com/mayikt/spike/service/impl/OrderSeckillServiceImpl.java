package com.mayikt.spike.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.mayikt.base.BaseApiService;
import com.mayikt.base.BaseResponse;
import com.mayikt.spike.api.service.OrderSeckillService;
import com.mayikt.spike.mapper.OrderMapper;
import com.mayikt.spike.mapper.entity.OrderEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: liuwq
 * @date: 2019/6/2 0002 21:28
 * @version: V1.0
 * @Copyright:该项目“基于SpringCloud2.x构建微服务电商项目”由每特教育|蚂蚁课堂版权所有，未经过允许的情况下， 私自分享视频和源码属于违法行为。
 */
@RestController
public class OrderSeckillServiceImpl extends BaseApiService<JSONObject> implements OrderSeckillService {
    @Autowired
    private OrderMapper orderMapper;

    /**
     * 前端写个定时器ajax，2s查询一次该接口的秒杀结果返回页面
     * @param phone
     * @param seckillId
     * @return
     */
    @Override
    public BaseResponse<JSONObject> getOrder(String phone, Long seckillId) {
        if (StringUtils.isEmpty(phone)) {
            return setResultError("手机号码不能为空!");
        }
        if (seckillId == null) {
            return setResultError("商品库存id不能为空!");
        }

        OrderEntity orderEntity = orderMapper.findByOrder(phone, seckillId);
        if (orderEntity == null) {
            return setResultError("正在排队中.....");
        }
        return setResultSuccess("恭喜你秒杀成功!");
    }

}
