package com.mayikt.pay.mayikt.pay.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.mayikt.pay.mayikt.base.BaseApiService;
import com.mayikt.pay.mayikt.base.BaseResponse;
import com.mayikt.pay.mayikt.core.token.GenerateToken;
import com.mayikt.pay.mayikt.core.utils.SnowflakeIdUtils;
import com.mayikt.pay.mayikt.pay.input.dto.CreatePayTokenInpDTO;
import com.mayikt.pay.mayikt.pay.mapper.PaymentTransactionMapper;
import com.mayikt.pay.mayikt.pay.mapper.entity.PaymentTransactionEntity;
import com.mayikt.pay.mayikt.pay.service.PayMentTransacTokenService;

/**
 * @description:
 * @author: liuwq
 * @date: 2019年3月26日 下午5:48:17
 * @version: V1.0
 * @Copyright:该项目“基于SpringCloud2.x构建微服务电商项目”由每特教育|蚂蚁课堂版权所有，未经过允许的情况下，
 *            私自分享视频和源码属于违法行为。
 */
@RestController
public class PayMentTransacTokenServiceImpl extends BaseApiService<JSONObject> implements PayMentTransacTokenService {

	@Autowired
	private PaymentTransactionMapper paymentTransactionMapper;
	
	@Autowired
	private GenerateToken generateToken;
	
	@Override
	public BaseResponse<JSONObject> createPayToken(CreatePayTokenInpDTO createPayTokenInpDTO) {
		String orderId = createPayTokenInpDTO.getOrderId();
		if (StringUtils.isEmpty(orderId)) {
			return setResultError("订单号码不能为空!");
		}
		Long payAmount = createPayTokenInpDTO.getPayAmount();
		if (payAmount == null) {
			return setResultError("金额不能为空!");
		}
		Long userId = createPayTokenInpDTO.getUserId();
		if (userId == null) {
			return setResultError("userId不能为空!");
		}
		
		// 2.将输入插入数据库中
		PaymentTransactionEntity paymentTransactionEntity = new PaymentTransactionEntity();
		paymentTransactionEntity.setOrderId(orderId);
		paymentTransactionEntity.setPayAmount(payAmount);
		paymentTransactionEntity.setUserId(userId);
		// 使用雪花算法 生成全局支付id
		paymentTransactionEntity.setPaymentId(SnowflakeIdUtils.nextId());
		int result = paymentTransactionMapper.insertPaymentTransaction(paymentTransactionEntity);
		if (!toDaoResult(result)) {
			return setResultError("新增支付表失败!");
		}
		Long payId = paymentTransactionEntity.getId();
		if (payId == null) {
			return setResultError("新增支付表失败!");
		}

		// 3.生成对应支付令牌
		String keyPrefix = "pay_";
		String token = generateToken.createToken(keyPrefix, payId + "");
		// 把令牌返回页面
		JSONObject dataResult = new JSONObject();
		dataResult.put("token", token);
		return setResultSuccess(dataResult);
	}

}
