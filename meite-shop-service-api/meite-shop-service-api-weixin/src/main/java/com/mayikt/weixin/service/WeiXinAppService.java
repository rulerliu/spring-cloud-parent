package com.mayikt.weixin.service;

import com.mayikt.base.BaseResponse;
import com.mayikt.weixin.output.dto.AppOutDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;

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
	BaseResponse<AppOutDTO> getApp();
	
	@GetMapping("/qqLoginBack")
	BaseResponse<AppOutDTO> qqLoginBack(String code);

}
