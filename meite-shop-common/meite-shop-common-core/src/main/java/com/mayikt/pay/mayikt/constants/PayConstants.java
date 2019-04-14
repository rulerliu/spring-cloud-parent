package com.mayikt.pay.mayikt.constants;

/**
 * @description:
 * @author: liuwq
 * @date: 2019/4/6 0006 0:13
 * @version: V1.0
 * @Copyright:该项目“基于SpringCloud2.x构建微服务电商项目”由每特教育|蚂蚁课堂版权所有，未经过允许的情况下， 私自分享视频和源码属于违法行为。
 */
public interface PayConstants {

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

}
