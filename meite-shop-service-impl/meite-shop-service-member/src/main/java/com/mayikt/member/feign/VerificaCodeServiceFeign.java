package com.mayikt.member.feign;

import org.springframework.cloud.openfeign.FeignClient;

import com.mayikt.pay.mayikt.weixin.service.VerificaCodeService;

/**
 * @description:
 * @author: liuwq
 * @date: 2019年  下午11:57:42
 * @version: V1.0
 * @Copyright:该项目“基于SpringCloud2.x构建微服务电商项目”由每特教育|蚂蚁课堂版权所有，未经过允许的情况下，
 *            私自分享视频和源码属于违法行为。
 */
@FeignClient(name = "app-mayikt-weixin")
public interface VerificaCodeServiceFeign extends VerificaCodeService {

}
