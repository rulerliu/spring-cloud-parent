package com.mayikt.zuul.chain.handler.client;

import com.mayikt.zuul.chain.handler.GatewayHandler;
import com.mayikt.zuul.chain.handler.factory.FactoryHandler2;
import com.netflix.zuul.context.RequestContext;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description:
 * @author: liuwq
 * @date: 2019/6/1 0001 21:13
 * @version: V1.0
 * @Copyright:该项目“基于SpringCloud2.x构建微服务电商项目”由每特教育|蚂蚁课堂版权所有，未经过允许的情况下， 私自分享视频和源码属于违法行为。
 */
@Component
public class ChainClient2 {

    public void run(RequestContext ctx, String ipAddres, HttpServletRequest request, HttpServletResponse response) {
        GatewayHandler firstGatewayHander = FactoryHandler2.getFirstGatewayHander();
        firstGatewayHander.service(ctx, ipAddres, request, response);
    }

}
