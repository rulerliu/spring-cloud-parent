package com.mayikt.zuul.chain.handler;

import com.netflix.zuul.context.RequestContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description:
 * @author: liuwq
 * @date: 2019/6/1 0001 21:02
 * @version: V1.0
 * @Copyright:该项目“基于SpringCloud2.x构建微服务电商项目”由每特教育|蚂蚁课堂版权所有，未经过允许的情况下， 私自分享视频和源码属于违法行为。
 */
public abstract class GatewayHandler {

    protected GatewayHandler nextGatewayHandler;

    /**
     * handler处理器
     * @return
     */
    public abstract void service(RequestContext ctx, HttpServletRequest request, HttpServletResponse response);

    /**
     * 指向下一个handler
     */
    public void setNextGatewayHandler(GatewayHandler nextGatewayHandler) {
        if (nextGatewayHandler != null) {
            this.nextGatewayHandler = nextGatewayHandler;
        }
    }

    public void resultError(Integer code, RequestContext ctx, String errorMsg) {
        ctx.setResponseStatusCode(code);
        // 网关响应为false 不会转发服务
        ctx.setSendZuulResponse(false);
        ctx.setResponseBody(errorMsg);
        ctx.getResponse().setContentType("text/html;charset=UTF-8");
    }

    protected void resultError(RequestContext ctx, String errorMsg) {
        ctx.setResponseStatusCode(401);
        ctx.setSendZuulResponse(false);
        ctx.setResponseBody(errorMsg);
    }

}
