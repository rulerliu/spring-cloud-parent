package com.mayikt.pay.mayikt.pay.callback.factory;

import com.mayikt.pay.mayikt.core.utils.SpringContextUtil;
import com.mayikt.pay.mayikt.pay.callback.AbstractPayCallbackTemplate;

/**
 * @description:
 * @author: liuwq
 * @date: 2019/4/6 0006 14:05
 * @version: V1.0
 * @Copyright:该项目“基于SpringCloud2.x构建微服务电商项目”由每特教育|蚂蚁课堂版权所有，未经过允许的情况下， 私自分享视频和源码属于违法行为。
 */
public class PayCallbackFactory {

    public static AbstractPayCallbackTemplate getPayCallbackTemplate(String beanId) {
        return (AbstractPayCallbackTemplate) SpringContextUtil.getBean(beanId);
    }

}
