package com.mayikt.weixin.service.impl;

import com.mayikt.weixin.output.dto.AppOutDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

import com.mayikt.base.BaseApiService;
import com.mayikt.base.BaseResponse;
import com.mayikt.weixin.service.WeiXinAppService;

/**
 * @description:
 * @author: liuwq
 * @date: 2019年  下午11:25:50
 * @version: V1.0
 * @Copyright:该项目“基于SpringCloud2.x构建微服务电商项目”由每特教育|蚂蚁课堂版权所有，未经过允许的情况下，
 *            私自分享视频和源码属于违法行为。
 */
@RestController
public class WeiXinAppServiceImpl extends BaseApiService<AppOutDTO> implements WeiXinAppService {

	@Value("${mayikt.weixin.app.name}")
	private String appName;

	@Override
	public BaseResponse<AppOutDTO> getApp() {
		return setResultSuccess(new AppOutDTO("1", "liuwq"));
	}

}
