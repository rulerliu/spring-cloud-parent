package com.mayikt.weixin.service.impl;

import org.springframework.web.bind.annotation.RestController;

import com.mayikt.weixin.entity.AppEntity;
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
public class WeiXinAppServiceImpl implements WeiXinAppService {

	@Override
	public AppEntity getApp() {
		return new AppEntity("robbieliu", "abc1234");
	}

}
