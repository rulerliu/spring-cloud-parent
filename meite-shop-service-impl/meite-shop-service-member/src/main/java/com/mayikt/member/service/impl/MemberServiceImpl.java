package com.mayikt.member.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.mayikt.base.BaseResponse;
import com.mayikt.member.feign.WeiXinAppServiceFeign;
import com.mayikt.member.service.MemberService;
import com.mayikt.weixin.entity.AppEntity;

/**
 * @description:
 * @author: liuwq
 * @date: 2019年  下午11:40:13
 * @version: V1.0
 * @Copyright:该项目“基于SpringCloud2.x构建微服务电商项目”由每特教育|蚂蚁课堂版权所有，未经过允许的情况下，
 *            私自分享视频和源码属于违法行为。
 */
@RestController
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	private WeiXinAppServiceFeign weiXinAppServiceFeign;

	/**
	 * 会员调用微信服务
	 */
	@Override
	public BaseResponse<AppEntity> memberInvokeWeixin() {
		return weiXinAppServiceFeign.getApp();
	}

}
