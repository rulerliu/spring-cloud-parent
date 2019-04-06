package com.mayikt.pay.service.impl;

import com.mayikt.constants.PayConstants;
import com.mayikt.pay.callback.AbstractPayCallbackTemplate;
import com.mayikt.pay.callback.factory.PayCallbackFactory;
import com.mayikt.pay.service.PayAsynCallbackService;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description:
 * @author: liuwq
 * @date: 2019/4/6 0006 14:02
 * @version: V1.0
 * @Copyright:该项目“基于SpringCloud2.x构建微服务电商项目”由每特教育|蚂蚁课堂版权所有，未经过允许的情况下， 私自分享视频和源码属于违法行为。
 */
@RestController
public class PayAsynCallbackServiceImpl implements PayAsynCallbackService {

    /**
     * 银联异步回调接口执行代码
     *
     * @param req
     * @param resp
     * @return
     */
    public String unionPayAsynCallback(HttpServletRequest req, HttpServletResponse resp) {
        AbstractPayCallbackTemplate payCallbackTemplate = PayCallbackFactory.getPayCallbackTemplate(PayConstants.UNIONPAY_CALLBACK_TEMPLATE);
        return payCallbackTemplate.asyncCallBack(req, resp);
    }

    /**
     * 阿里异步回调接口执行代码
     *
     * @param req
     * @param resp
     * @return
     */
    @Override
    public String aliPayAsynCallback(HttpServletRequest req, HttpServletResponse resp) {
        AbstractPayCallbackTemplate payCallbackTemplate = PayCallbackFactory.getPayCallbackTemplate(PayConstants.ALIPAY_CALLBACK_TEMPLATE);
        return payCallbackTemplate.asyncCallBack(req, resp);
    }

}
