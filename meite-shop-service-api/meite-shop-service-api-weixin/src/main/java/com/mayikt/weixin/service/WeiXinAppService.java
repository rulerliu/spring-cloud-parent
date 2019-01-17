package com.mayikt.weixin.service;

import org.springframework.web.bind.annotation.GetMapping;

import com.mayikt.base.BaseResponse;
import com.mayikt.weixin.entity.AppEntity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @description:
 * @author: liuwq
 * @date: 2019年  下午11:22:26
 * @version: V1.0
 * @Copyright:该项目“基于SpringCloud2.x构建微服务电商项目”由每特教育|蚂蚁课堂版权所有，未经过允许的情况下，
 *            私自分享视频和源码属于违法行为。
 */
@Api(tags = "微信服务接口")
public interface WeiXinAppService {
	
	/**
	 * 功能说明： 应用服务接口
	 */
	@ApiOperation("查询微信服务详情接口")
	@GetMapping("/getApp")
	public BaseResponse<AppEntity> getApp();

}
