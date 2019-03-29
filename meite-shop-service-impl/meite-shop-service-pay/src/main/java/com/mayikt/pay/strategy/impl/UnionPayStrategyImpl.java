package com.mayikt.pay.strategy.impl;

import com.mayikt.pay.mapper.entity.PaymentChannelEntity;
import com.mayikt.pay.output.dto.PayMentTransacOutDTO;
import com.mayikt.pay.strategy.PayStrategy;
import lombok.extern.slf4j.Slf4j;

/**
 * @description:
 * @author: liuwq
 * @date: 2019/3/29 0029 上午 10:20
 * @version: V1.0
 * @Copyright:该项目“基于SpringCloud2.x构建微服务电商项目”由每特教育|蚂蚁课堂版权所有，未经过允许的情况下， 私自分享视频和源码属于违法行为。
 */
@Slf4j
public class UnionPayStrategyImpl implements PayStrategy {
    @Override
    public String toPayHtml(PaymentChannelEntity paymentChannelEntity, PayMentTransacOutDTO payMentTransacOutDTO) {
        log.info("进入银联支付跳转HTML...");
        return "success";
    }
}
