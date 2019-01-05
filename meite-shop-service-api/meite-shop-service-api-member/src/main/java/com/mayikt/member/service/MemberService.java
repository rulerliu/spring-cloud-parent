package com.mayikt.member.service;

import org.springframework.web.bind.annotation.GetMapping;

import com.mayikt.weixin.entity.AppEntity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @description:
 * @author: liuwq
 * @date: 2019年  下午11:32:44
 * @version: V1.0
 * @Copyright:该项目“基于SpringCloud2.x构建微服务电商项目”由每特教育|蚂蚁课堂版权所有，未经过允许的情况下，
 *            私自分享视频和源码属于违法行为。
 */
@Api(tags = "会员服务接口")
public interface MemberService {

	/**
	 * 会员服务接口调用微信接口
	 * @return
	 */
	@GetMapping("/memberInvokeWeixin")
	@ApiOperation("会员服务调用微信服务接口")
	public AppEntity memberInvokeWeixin();
	
}
