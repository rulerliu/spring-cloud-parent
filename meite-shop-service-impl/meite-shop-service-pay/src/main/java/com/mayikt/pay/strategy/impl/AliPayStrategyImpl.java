package com.mayikt.pay.strategy.impl;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.config.AlipayConfig;
import com.mayikt.pay.mapper.entity.PaymentChannelEntity;
import com.mayikt.pay.output.dto.PayMentTransacOutDTO;
import com.mayikt.pay.strategy.PayStrategy;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

/**
 * @description:
 * @author: liuwq
 * @date: 2019/3/29 0029 上午 10:17
 * @version: V1.0
 * @Copyright:该项目“基于SpringCloud2.x构建微服务电商项目”由每特教育|蚂蚁课堂版权所有，未经过允许的情况下， 私自分享视频和源码属于违法行为。
 */
@Slf4j
public class AliPayStrategyImpl implements PayStrategy {

    @Override
    public String toPayHtml(PaymentChannelEntity paymentChannelEntity, PayMentTransacOutDTO payMentTransacOutDTO) {
        log.info(">>>>>支付宝参数封装开始<<<<<<<<");

        // 获得初始化的AlipayClient(实际应该在数据库中配置)
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id,
                AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key,
                AlipayConfig.sign_type);

        // 设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(AlipayConfig.return_url);
        alipayRequest.setNotifyUrl(AlipayConfig.notify_url);

        // 商户订单号，商户网站订单系统中唯一订单号，必填
        String outTradeNo = payMentTransacOutDTO.getPaymentId();
        // 付款金额，必填，单位是元
        String totalAmount = changeF2Y(payMentTransacOutDTO.getPayAmount() + "");
        // 订单名称，必填
        String subject = "每特教育微服务电商项目";
        // 商品描述，可空
        String body = "每特教育微服务电商项目";

        alipayRequest.setBizContent("{\"out_trade_no\":\"" + outTradeNo + "\"," + "\"total_amount\":\"" + totalAmount
                + "\"," + "\"subject\":\"" + subject + "\"," + "\"body\":\"" + body + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        // 请求
        try {
            String result = alipayClient.pageExecute(alipayRequest).getBody();
            return result;
        } catch (Exception e) {
            return null;
        }

    }

    /** 金额为分的格式 */
    public static final String CURRENCY_FEN_REGEX = "\\-?[0-9]+";

    /**
     * 将分为单位的转换为元 （除100）
     *
     * @param amount
     * @return
     * @throws Exception
     */
    public static String changeF2Y(String amount) {
        if (!amount.matches(CURRENCY_FEN_REGEX)) {
            return null;
        }
        return BigDecimal.valueOf(Long.valueOf(amount)).divide(new BigDecimal(100)).toString();
    }

}