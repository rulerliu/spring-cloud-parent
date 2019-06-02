package com.mayikt.zuul.chain.handler.factory;

import com.mayikt.core.utils.SpringContextUtil;
import com.mayikt.zuul.chain.handler.GatewayHandler;
import com.mayikt.zuul.mapper.GatewayHandlerMapper;
import com.mayikt.zuul.mapper.entity.GatewayHandlerEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: liuwq
 * @date: 2019/6/1 0001 21:09
 * @version: V1.0
 * @Copyright:该项目“基于SpringCloud2.x构建微服务电商项目”由每特教育|蚂蚁课堂版权所有，未经过允许的情况下， 私自分享视频和源码属于违法行为。
 */
@Component
@Slf4j
public class FactoryHandler2 {

    @Autowired
    private GatewayHandlerMapper gatewayHandlerMapper;

    private GatewayHandler firstGatewayHandler;

    /**
     * 责任链启动方式
     * @return
     */
    public GatewayHandler getFirstGatewayHander() {
        /*GatewayHandler blackListHandler = (GatewayHandler) SpringContextUtil.getBean("blackListHandler");
        GatewayHandler apiVerifyHandler = (GatewayHandler) SpringContextUtil.getBean("apiVerifyHandler");
        blackListHandler.setNextGatewayHandler(apiVerifyHandler);

        GatewayHandler apiAuthorityHandler = (GatewayHandler) SpringContextUtil.getBean("apiAuthorityHandler");
        apiVerifyHandler.setNextGatewayHandler(apiAuthorityHandler);

        return blackListHandler;*/

        if (this.firstGatewayHandler != null) {
            return this.firstGatewayHandler;
        }
        log.info(">>>>>从数据库中获取最新的Handler开始<<<<<<<<<<<<<<<<<<");
        // 1.数据库查询第一个Handler
        GatewayHandlerEntity firstGatewayHandlerEntity = gatewayHandlerMapper.getFirstGatewayHandler();
        if (firstGatewayHandlerEntity == null) {
            return null;
        }
        // 2.获取第一个handlerId springbean注入id
        String firstHandlerId = firstGatewayHandlerEntity.getHandlerId();
        if (StringUtils.isEmpty(firstHandlerId)) {
            return null;
        }
        // 3.从spring容器中加载第一个handler
        GatewayHandler firstGatewayHandler = (GatewayHandler) SpringContextUtil.getBean(firstHandlerId);
        // 4.获取下一个Handlerid
        String nextHandlerId = firstGatewayHandlerEntity.getNextHandlerId();
        // 5.记录每一次循环控制的hanlder
        GatewayHandler tempGatewayHandler = firstGatewayHandler;
        while (!StringUtils.isEmpty(nextHandlerId)) {
            // 3.查询数据库下一个Handler信息
            GatewayHandlerEntity nextGatewayHandlerEntity = gatewayHandlerMapper.getByHandler(nextHandlerId);
            if (nextGatewayHandlerEntity == null) {
                break;
            }
            // 4.从springboot中获取下一个handlerbean细细
            String handlerId = nextGatewayHandlerEntity.getHandlerId();
            GatewayHandler nextGatewayHandler = (GatewayHandler) SpringContextUtil.getBean(handlerId);
            // 5.执行下一个hanlder
            nextHandlerId = nextGatewayHandlerEntity.getNextHandlerId();
            // 设置指向下一个handerl
            tempGatewayHandler.setNextGatewayHandler(nextGatewayHandler);
            tempGatewayHandler = nextGatewayHandler;
        }
        // 3.直接把第一个Handler 放入到缓存中..
        log.info(">>>>>从数据库中获取最新的Handler结束<<<<<<<<<<<<<<<<<<");
        this.firstGatewayHandler = firstGatewayHandler;
        return firstGatewayHandler;
    }

}
