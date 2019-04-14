package com.mayikt.pay.callback;

import com.mayikt.constants.PayConstants;
import com.mayikt.pay.mapper.PaymentTransactionLogMapper;
import com.mayikt.pay.mapper.entity.PaymentTransactionLogEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

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

    @Autowired
    private PaymentTransactionLogMapper paymentTransactionLogMapper;

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

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
//        payLog(verifySignature);
        log.info(">>>>>asyncCallBack service 01");
        threadPoolTaskExecutor.execute(new PayLogThread(verifySignature));
        log.info(">>>>>asyncCallBack service 04");

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
        PaymentTransactionLogEntity paymentTransactionLog = new PaymentTransactionLogEntity();
        paymentTransactionLog.setTransactionId(paymentId);
        paymentTransactionLog.setAsyncLog(verifySignature.toString());
        paymentTransactionLogMapper.insertTransactionLog(paymentTransactionLog);
    }

    // A 1423 B 1234
    /**
     * 使用多线程写入日志目的：加快响应 提高程序效率 使用线程池维护线程
     */
    class PayLogThread implements Runnable {
        private Map<String, String> verifySignature;

        public PayLogThread( Map<String, String> verifySignature) {
            this.verifySignature = verifySignature;
        }

        @Override
        public void run() {
            log.info(">>>>>asyncCallBack service 02");
            payLog(verifySignature);
            log.info(">>>>>asyncCallBack service 03");
        }

    }

}
