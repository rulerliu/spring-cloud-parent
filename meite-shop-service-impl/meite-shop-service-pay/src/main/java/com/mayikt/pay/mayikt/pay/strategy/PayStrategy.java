package com.mayikt.pay.mayikt.pay.strategy;

import com.mayikt.pay.mayikt.pay.mapper.entity.PaymentChannelEntity;
import com.mayikt.pay.mayikt.pay.output.dto.PayMentTransacOutDTO;

/**
 * @description:
 * @author: liuwq
 * @date: 2019/3/29 0029 上午 10:14
 * @version: V1.0
 * @Copyright:该项目“基于SpringCloud2.x构建微服务电商项目”由每特教育|蚂蚁课堂版权所有，未经过允许的情况下， 私自分享视频和源码属于违法行为。
 */
public interface PayStrategy {

    /**
     *
     * @param paymentChannelEntity
     *            渠道参数
     * @param payMentTransacOutDTO
     *            支付参数
     * @return
     */
    String toPayHtml(PaymentChannelEntity paymentChannelEntity, PayMentTransacOutDTO payMentTransacOutDTO);

}
