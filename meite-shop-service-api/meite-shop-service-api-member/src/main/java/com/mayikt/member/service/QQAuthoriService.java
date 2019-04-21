package com.mayikt.member.service;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;
import com.mayikt.pay.mayikt.base.BaseResponse;

import io.swagger.annotations.Api;

/**
 * @description:
 * @author: liuwq
 * @date: 2019年  下午10:22:37
 * @version: V1.0
 * @Copyright:该项目“基于SpringCloud2.x构建微服务电商项目”由每特教育|蚂蚁课堂版权所有，未经过允许的情况下，
 *            私自分享视频和源码属于违法行为。
 */
@Api(tags = "QQ授权接口")
public interface QQAuthoriService {

	/**
	 * 根据 openid查询是否已经绑定,如果已经绑定，则直接实现自动登陆
	 * 
	 * @param openid
	 * @return
	 */
	@RequestMapping("/findByOpenId")
	BaseResponse<JSONObject> findByOpenId(@RequestParam("qqOpenId") String qqOpenId);

}
