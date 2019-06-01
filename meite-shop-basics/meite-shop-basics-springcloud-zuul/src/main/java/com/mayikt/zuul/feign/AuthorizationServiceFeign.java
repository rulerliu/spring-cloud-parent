package com.mayikt.zuul.feign;

import com.mayikt.authorization.api.service.AuthorizationService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @description:
 * @author: liuwq
 * @date: 2019/6/1 0001 20:31
 * @version: V1.0
 * @Copyright:该项目“基于SpringCloud2.x构建微服务电商项目”由每特教育|蚂蚁课堂版权所有，未经过允许的情况下， 私自分享视频和源码属于违法行为。
 */
@FeignClient("app-mayikt-authorization")
public interface AuthorizationServiceFeign extends AuthorizationService {

}
