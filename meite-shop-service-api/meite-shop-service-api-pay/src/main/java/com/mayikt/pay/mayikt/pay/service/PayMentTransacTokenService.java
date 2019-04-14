package com.mayikt.pay.mayikt.pay.service;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;

import com.alibaba.fastjson.JSONObject;
import com.mayikt.pay.mayikt.base.BaseResponse;
import com.mayikt.pay.mayikt.pay.input.dto.CreatePayTokenInpDTO;

/**
 * @description:
 * @author: liuwq
 * @date: 2019年3月26日 下午5:38:55
 * @version: V1.0
 * @Copyright:该项目“基于SpringCloud2.x构建微服务电商项目”由每特教育|蚂蚁课堂版权所有，未经过允许的情况下，
 *            私自分享视频和源码属于违法行为。
 */
public interface PayMentTransacTokenService {

	/**
	 * 创建支付令牌
	 */
	@GetMapping("/cratePayToken")
	BaseResponse<JSONObject> createPayToken(@Validated CreatePayTokenInpDTO createPayTokenInpDTO);
	
}
