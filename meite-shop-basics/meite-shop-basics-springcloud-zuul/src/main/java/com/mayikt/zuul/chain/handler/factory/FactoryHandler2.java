package com.mayikt.zuul.chain.handler.factory;

import com.mayikt.core.utils.SpringContextUtil;
import com.mayikt.zuul.chain.handler.GatewayHandler;

/**
 * @description:
 * @author: liuwq
 * @date: 2019/6/1 0001 21:09
 * @version: V1.0
 * @Copyright:该项目“基于SpringCloud2.x构建微服务电商项目”由每特教育|蚂蚁课堂版权所有，未经过允许的情况下， 私自分享视频和源码属于违法行为。
 */
public class FactoryHandler2 {

    /**
     * 责任链启动方式
     * @return
     */
    public static GatewayHandler getFirstGatewayHander() {
        GatewayHandler blackListHandler = (GatewayHandler) SpringContextUtil.getBean("blackListHandler");
        GatewayHandler apiVerifyHandler = (GatewayHandler) SpringContextUtil.getBean("apiVerifyHandler");
        blackListHandler.setNextGatewayHandler(apiVerifyHandler);

        GatewayHandler apiAuthorityHandler = (GatewayHandler) SpringContextUtil.getBean("apiAuthorityHandler");
        apiVerifyHandler.setNextGatewayHandler(apiAuthorityHandler);

        return blackListHandler;
    }

}
