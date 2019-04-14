package com.mayikt.pay.mayikt.init;

import com.mayikt.pay.unionpay.acp.sdk.SDKConfig;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: liuwq
 * @date: 2019/4/3 0003 22:26
 * @version: V1.0
 * @Copyright:该项目“基于SpringCloud2.x构建微服务电商项目”由每特教育|蚂蚁课堂版权所有，未经过允许的情况下， 私自分享视频和源码属于违法行为。
 */
@Component
public class IninUnionpayCerts implements ApplicationRunner {

    /**
     * springboot项目启动，执行该方法
     * @param args
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("开始加载证书...");
        SDKConfig.getConfig().loadPropertiesFromSrc();
    }
}
