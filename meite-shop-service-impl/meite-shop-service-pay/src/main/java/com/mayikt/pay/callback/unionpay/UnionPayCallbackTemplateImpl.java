package com.mayikt.pay.callback.unionpay;

import com.mayikt.constants.PayConstants;
import com.mayikt.pay.callback.AbstractPayCallbackTemplate;
import com.mayikt.pay.mapper.PaymentTransactionMapper;
import com.mayikt.pay.mapper.entity.PaymentTransactionEntity;
import com.unionpay.acp.sdk.AcpService;
import com.unionpay.acp.sdk.LogUtil;
import com.unionpay.acp.sdk.SDKConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: liuwq
 * @date: 2019/4/6 0006 13:35
 * @version: V1.0
 * @Copyright:该项目“基于SpringCloud2.x构建微服务电商项目”由每特教育|蚂蚁课堂版权所有，未经过允许的情况下， 私自分享视频和源码属于违法行为。
 */
@Component
@Slf4j
public class UnionPayCallbackTemplateImpl extends AbstractPayCallbackTemplate {

    @Autowired
    private PaymentTransactionMapper paymentTransactionMapper;

    @Override
    public Map<String, String> verifySignature(HttpServletRequest req, HttpServletResponse resp) {
        LogUtil.writeLog("BackRcvResponse接收后台通知开始");

        String encoding = req.getParameter(SDKConstants.param_encoding);
        // 获取银联通知服务器发送的后台通知参数
        Map<String, String> reqParam = getAllRequestParam(req);
        LogUtil.printRequestLog(reqParam);

        //重要！验证签名前不要修改reqParam中的键值对的内容，否则会验签不过
        if (!AcpService.validate(reqParam, encoding)) {
            LogUtil.writeLog("验证签名结果[失败].");
            //验签失败，需解决验签问题
            reqParam.put(PayConstants.RESULT_NAME, PayConstants.RESULT_PAYCODE_201);

        } else {
            LogUtil.writeLog("验证签名结果[成功].");
            //【注：为了安全验签成功才应该写商户的成功处理逻辑】交易成功，更新商户订单状态

            String paymentId = reqParam.get("orderId"); //获取后台通知的数据，其他字段也可用类似方式获取
            String respCode = reqParam.get("respCode");
            //判断respCode=00、A6后，对涉及资金类的交易，请再发起查询接口查询，确定交易成功后更新数据库。
            reqParam.put("paymentId", paymentId);
            reqParam.put(PayConstants.RESULT_NAME, PayConstants.RESULT_PAYCODE_200);
            System.out.println("paymentId:" + paymentId + ",respCode:" + respCode);

        }

        LogUtil.writeLog("BackRcvResponse接收后台通知结束");
        //返回给银联服务器http 200  状态码
//        resp.getWriter().print("ok");
        return reqParam;
    }

    @Override
    public String asyncService(Map<String, String> verifySignature) {
        String paymentId = verifySignature.get("orderId"); //获取后台通知的数据，其他字段也可用类似方式获取
        String respCode = verifySignature.get("respCode");
        //判断respCode=00、A6后，对涉及资金类的交易，请再发起查询接口查询，确定交易成功后更新数据库。
        System.out.println("paymentId:" + paymentId + ",respCode:" + respCode);
        if (!("00".equals(respCode) || "A6".equals(respCode))) {
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
        return PayConstants.YINLIAN_RESULT_FAIL;
    }

    @Override
    public String successResult() {
        return PayConstants.YINLIAN_RESULT_SUCCESS;
    }

    /**
     * 获取请求参数中所有的信息
     * 当商户上送frontUrl或backUrl地址中带有参数信息的时候，
     * 这种方式会将url地址中的参数读到map中，会导多出来这些信息从而致验签失败，这个时候可以自行修改过滤掉url中的参数或者使用getAllRequestParamStream方法。
     * @param request
     * @return
     */
    public static Map<String, String> getAllRequestParam(final HttpServletRequest request) {
        Map<String, String> res = new HashMap<String, String>();
        Enumeration<?> temp = request.getParameterNames();
        if (null != temp) {
            while (temp.hasMoreElements()) {
                String en = (String) temp.nextElement();
                String value = request.getParameter(en);
                res.put(en, value);
                // 在报文上送时，如果字段的值为空，则不上送<下面的处理为在获取所有参数数据时，判断若值为空，则删除这个字段>
                if (res.get(en) == null || "".equals(res.get(en))) {
                    // System.out.println("======为空的字段名===="+en);
                    res.remove(en);
                }
            }
        }
        return res;
    }
}
