package com.mayikt.constants;

/**
 * @description:
 * @author: liuwq
 * @date: 2019/4/6 0006 0:13
 * @version: V1.0
 * @Copyright:该项目“基于SpringCloud2.x构建微服务电商项目”由每特教育|蚂蚁课堂版权所有，未经过允许的情况下， 私自分享视频和源码属于违法行为。
 */
public interface PayConstants {

    String PAYMENT_CHANNEL_YINLIAN_PAY = "yinlian_pay";
    String PAYMENT_CHANNEL_ALI_PAY = "ali_pay";

    String RESULT_NAME = "resultCode";
    String RESULT_PAYCODE_201 = "201";
    String RESULT_PAYCODE_200 = "200";
    /**
     * 已经支付成功状态
     */
    Integer PAY_STATUS_SUCCESS = 1;
    /**
     * 返回银联通知成功
     */
    String YINLIAN_RESULT_SUCCESS = "ok";
    /**
     * 返回银联失败通知
     */
    String YINLIAN_RESULT_FAIL = "fail";
    /**
     * 返回银联通知成功
     */
    String ALI_RESULT_SUCCESS = "success";
    /**
     * 返回银联失败通知
     */
    String ALI_RESULT_FAIL = "fail";

    /**
     * 银联回调模板benaid
     */
    String UNIONPAY_CALLBACK_TEMPLATE = "unionPayCallbackTemplateImpl";

    /**
     * 支付宝回调模板benaid
     */
    String ALIPAY_CALLBACK_TEMPLATE = "aliPayCallbackTemplateImpl";

    /**
     * 添加积分队列名称
     */
    String INTEGRAL_DIC_QUEUE = "integral_queue";

    /**
     * 支付补偿队列名称
     */
    String PAY_COMPENSATION_QUEUE = "pay_compensation_queue";

    /**
     * 积分交换机名称
     */
    String INTEGRAL_EXCHANGE_NAME = "integral_exchange";

    /**
     * 路由键
     */
    String INTEGRAL_ROUTINT_KEY = "integralRoutingKey";

}
