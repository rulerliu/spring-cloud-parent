package com.mayikt.member.feign;

import org.springframework.cloud.openfeign.FeignClient;

import com.mayikt.pay.mayikt.weixin.service.WeiXinAppService;

/**
 * @description: 会员调用微信服务feign
 * @author: liuwq
 * @date: 2019年  下午11:41:44
 * @version: V1.0
 * @Copyright:该项目“基于SpringCloud2.x构建微服务电商项目”由每特教育|蚂蚁课堂版权所有，未经过允许的情况下，
 *            私自分享视频和源码属于违法行为。
 */
@FeignClient(name = "app-mayikt-weixin")
public interface WeiXinAppServiceFeign extends WeiXinAppService {

//	 /**
//	 * 功能说明： 应用服务接口
//	 */
//	 @GetMapping("/getApp")
//	 public AppEntity getApp();
	
}
