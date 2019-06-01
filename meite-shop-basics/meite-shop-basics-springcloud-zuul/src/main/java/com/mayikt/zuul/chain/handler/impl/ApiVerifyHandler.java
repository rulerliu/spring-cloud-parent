package com.mayikt.zuul.chain.handler.impl;

import com.mayikt.zuul.chain.handler.GatewayHandler;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description:
 * @author: liuwq
 * @date: 2019/6/1 0001 21:04
 * @version: V1.0
 * @Copyright:该项目“基于SpringCloud2.x构建微服务电商项目”由每特教育|蚂蚁课堂版权所有，未经过允许的情况下， 私自分享视频和源码属于违法行为。
 */
@Component
@Slf4j
public class ApiVerifyHandler extends GatewayHandler {

    @Override
    public void service(RequestContext ctx, String ipAddres, HttpServletRequest request, HttpServletResponse response) {
        log.info(">>>流程2：接口验签拦截");

        nextGatewayHandler.service(ctx, ipAddres, request, response);
    }

}
