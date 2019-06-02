package com.mayikt.zuul.chain.handler.factory;

import com.mayikt.core.utils.SpringContextUtil;
import com.mayikt.zuul.chain.handler.GatewayHandler;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: liuwq
 * @date: 2019/6/1 0001 21:09
 * @version: V1.0
 * @Copyright:该项目“基于SpringCloud2.x构建微服务电商项目”由每特教育|蚂蚁课堂版权所有，未经过允许的情况下， 私自分享视频和源码属于违法行为。
 */
@Component
public class FactoryHandler {

    /**
     * 责任链启动方式
     * @return
     */
    public List<GatewayHandler> getAllHandler() {
        GatewayHandler blackListHandler = (GatewayHandler) SpringContextUtil.getBean("blackListHandler");
        GatewayHandler apiVerifyHandler = (GatewayHandler) SpringContextUtil.getBean("apiVerifyHandler");
        GatewayHandler apiAuthorityHandler = (GatewayHandler) SpringContextUtil.getBean("apiAuthorityHandler");

        List<GatewayHandler> list = new ArrayList<>();
        list.add(blackListHandler);
        list.add(apiVerifyHandler);
        list.add(apiAuthorityHandler);
        return list;
    }

}
