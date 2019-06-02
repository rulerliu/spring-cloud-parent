package com.mayikt.zuul.chain.handler.client;

import com.mayikt.zuul.chain.handler.GatewayHandler;
import com.mayikt.zuul.chain.handler.factory.FactoryHandler;
import com.netflix.zuul.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @description:
 * @author: liuwq
 * @date: 2019/6/1 0001 21:13
 * @version: V1.0
 * @Copyright:该项目“基于SpringCloud2.x构建微服务电商项目”由每特教育|蚂蚁课堂版权所有，未经过允许的情况下， 私自分享视频和源码属于违法行为。
 */
@Component
public class ChainClient {

    @Autowired
    private FactoryHandler factoryHandler;

    public void run(RequestContext ctx, HttpServletRequest request, HttpServletResponse response) {
        List<GatewayHandler> allHandler = factoryHandler.getAllHandler();
        for (GatewayHandler gatewayHandler : allHandler) {
            gatewayHandler.service(ctx, request, response);
        }
    }

}
