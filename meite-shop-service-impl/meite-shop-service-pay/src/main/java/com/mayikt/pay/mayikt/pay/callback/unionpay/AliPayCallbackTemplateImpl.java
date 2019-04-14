package com.mayikt.pay.mayikt.pay.callback.unionpay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.mayikt.pay.mayikt.pay.mapper.PaymentTransactionMapper;
import com.mayikt.pay.mayikt.pay.mapper.entity.PaymentTransactionEntity;
import com.mayikt.pay.alipay.config.AlipayConfig;
import com.mayikt.pay.mayikt.constants.PayConstants;
import com.mayikt.pay.mayikt.pay.callback.AbstractPayCallbackTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @description:
 * @author: liuwq
 * @date: 2019/4/6 0006 15:24
 * @version: V1.0
 * @Copyright:该项目“基于SpringCloud2.x构建微服务电商项目”由每特教育|蚂蚁课堂版权所有，未经过允许的情况下， 私自分享视频和源码属于违法行为。
 */
@Component
@Slf4j
public class AliPayCallbackTemplateImpl extends AbstractPayCallbackTemplate {

    @Autowired
    private PaymentTransactionMapper paymentTransactionMapper;

    @Override
    public Map<String, String> verifySignature(HttpServletRequest request, HttpServletResponse resp) {
        //获取支付宝POST过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
//                valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        boolean signVerified = false; //调用SDK验证签名
        try {
            signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        //——请在这里编写您的程序（以下代码仅作参考）——

        /* 实际验证过程建议商户务必添加以下校验：
        1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
        2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
        3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
        4、验证app_id是否为该商户本身。
        */
        if(signVerified) {//验证成功
            try {
                //商户订单号
                String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

                //支付宝交易号
                String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

                //交易状态
                String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");

                if(trade_status.equals("TRADE_FINISHED")){
                    //判断该笔订单是否在商户网站中已经做过处理
                    //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                    //如果有做过处理，不执行商户的业务程序

                    //注意：
                    //退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
                }else if (trade_status.equals("TRADE_SUCCESS")){
                    //判断该笔订单是否在商户网站中已经做过处理
                    //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                    //如果有做过处理，不执行商户的业务程序

                    //注意：
                    //付款完成后，支付宝系统发送该交易状态通知
                }

                params.put("paymentId", out_trade_no);
                params.put(PayConstants.RESULT_NAME, PayConstants.RESULT_PAYCODE_200);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

//            out.println("success");

        }else {//验证失败
//            out.println("fail");
            params.put(PayConstants.RESULT_NAME, PayConstants.RESULT_PAYCODE_201);

            //调试用，写文本函数记录程序运行情况是否正常
            //String sWord = AlipaySignature.getSignCheckContentV1(params);
            //AlipayConfig.logResult(sWord);
        }
        return params;
    }

    @Override
    public String asyncService(Map<String, String> verifySignature) {
        String paymentId = verifySignature.get("paymentId"); //获取后台通知的数据，其他字段也可用类似方式获取
        String trade_status = verifySignature.get("trade_status");
        System.out.println("paymentId:" + paymentId + ",trade_status:" + trade_status);

        /**
         * 0    WAIT_BUYER_PAY	    交易创建，等待买家付款
         * 3    TRADE_CLOSED	    未付款交易超时关闭，或支付完成后全额退款
         * 1    RADE_SUCCESS	    交易支付成功
         * 2    TRADE_FINISHED	    交易结束，不可退款
         */
        if (!("TRADE_SUCCESS".equals(trade_status) || "TRADE_FINISHED".equals(trade_status))) {
            log.info("订单未支付成功");
            return failResult();
        }

        // 防止幂等性
        PaymentTransactionEntity paymentTransactionEntity = paymentTransactionMapper.selectByPaymentId(paymentId);
        if (PayConstants.PAY_STATUS_SUCCESS.equals(paymentTransactionEntity.getPaymentStatus())) {
            log.info("之前已经支付成功过");
            return successResult();
        }

        // 2.状态改为已支付
        paymentTransactionMapper.updatePaymentStatus(PayConstants.PAY_STATUS_SUCCESS.toString(), paymentId);

        // 3.调用积分服务增加积分(MQ分布式事物)

        return successResult();
    }

    @Override
    public String failResult() {
        return PayConstants.ALI_RESULT_FAIL;
    }

    @Override
    public String successResult() {
        return PayConstants.ALI_RESULT_SUCCESS;
    }
}
