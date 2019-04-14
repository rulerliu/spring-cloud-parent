package com.mayikt.pay.mayikt.pay.factory;

import com.mayikt.pay.mayikt.pay.strategy.PayStrategy;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description:
 * @author: liuwq
 * @date: 2019/3/29 0029 上午 10:46
 * @version: V1.0
 * @Copyright:该项目“基于SpringCloud2.x构建微服务电商项目”由每特教育|蚂蚁课堂版权所有，未经过允许的情况下， 私自分享视频和源码属于违法行为。
 */
@Slf4j
public class PayStrategyFactory {

    public static Map<String, PayStrategy> strategyBeanMap = new ConcurrentHashMap<String, PayStrategy>();

    public static PayStrategy getPayStrategy(String classAddres) {
        if (StringUtils.isBlank(classAddres)) {
            return null;
        }
        try {
            PayStrategy payStrategy = strategyBeanMap.get(classAddres);
            if (payStrategy != null) {
                return payStrategy;
            }

            Class<?> clazz = Class.forName(classAddres);
            payStrategy = (PayStrategy) clazz.newInstance();
            strategyBeanMap.put(classAddres, payStrategy);
            return payStrategy;
        } catch (Exception e) {
            log.error("payStrategy 初始化异常：{}", e);
            return null;
        }
    }

}
