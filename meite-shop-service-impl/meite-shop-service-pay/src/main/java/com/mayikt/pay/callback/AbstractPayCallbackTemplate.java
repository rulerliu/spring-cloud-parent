package com.mayikt.pay.callback;

import com.mayikt.constants.PayConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @description:
 * @author: liuwq
 * @date: 2019/4/6 0006 0:11
 * @version: V1.0
 * @Copyright:该项目“基于SpringCloud2.x构建微服务电商项目”由每特教育|蚂蚁课堂版权所有，未经过允许的情况下， 私自分享视频和源码属于违法行为。
 */
@Slf4j
public abstract class AbstractPayCallbackTemplate {

    /**
     * 获取所有的请求参数，封装成Map集合，并且验证是否又被篡改
     *
     * @param req
     * @param resp
     * @return
     */
    public abstract Map<String, String> verifySignature(HttpServletRequest req, HttpServletResponse resp);

    /**
     * 异步回调执行业务逻辑
     *
     * @return
     */
    public abstract String asyncService(Map<String, String> verifySignature);

    /**
     * 失败结果
     *
     * @return
     */
    public abstract String failResult();

    /**
     * 失败结果
     *
     * @return
     */
    public abstract String successResult();

    public String asyncCallBack(HttpServletRequest req, HttpServletResponse resp) {
        // 1.验证报文参数 相同点 获取所有的请求参数封装成map集合，并且进行参数验签
        Map<String, String> verifySignature = verifySignature(req, resp);
        String paymentId = verifySignature.get("paymentId");
        if (StringUtils.isBlank(paymentId)) {
            return failResult();
        }

        // 2.将支付参数信息根据支付id插入到数据库中
        payLog(verifySignature);

        if (!verifySignature.get(PayConstants.RESULT_NAME).equals(PayConstants.RESULT_PAYCODE_200)) {
            return failResult();
        }

        // 3.处理异步回调相关日志信息
        return asyncService(verifySignature);
    }

    /**
     * 将log写入到日志中
     *
     * @param verifySignature
     */
    private void payLog(Map<String, String> verifySignature) {
        String paymentId = verifySignature.get("paymentId");
        log.info(">>>>>paymentId:{}, verifySignature:{}", paymentId, verifySignature);
    }

}
