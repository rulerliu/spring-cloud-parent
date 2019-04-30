package com.mayikt.pay.service.impl;

import com.mayikt.base.BaseApiService;
import com.mayikt.base.BaseResponse;
import com.mayikt.core.bean.utils.MeiteBeanUtils;
import com.mayikt.core.token.GenerateToken;
import com.mayikt.core.utils.NumberCastUtils;
import com.mayikt.pay.mapper.PaymentTransactionMapper;
import com.mayikt.pay.mapper.entity.PaymentTransactionEntity;
import com.mayikt.pay.output.dto.PayMentTransacOutDTO;
import com.mayikt.pay.service.PayMentTransacInfoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PayMentTransacInfoServiceImpl extends BaseApiService<PayMentTransacOutDTO> implements PayMentTransacInfoService {
	
	@Autowired
	private GenerateToken generateToken;
	
	@Autowired
	private PaymentTransactionMapper paymentTransactionMapper;

	@Override
	public BaseResponse<PayMentTransacOutDTO> tokenByPayMentTransac(String token) {
		// 1.验证token是否为空
		if (StringUtils.isEmpty(token)) {
			return setResultError("token参数不能空!");
		}
		
		// 2.使用token查询redisPayMentTransacID，获取payId
		String value = generateToken.getToken(token);
		if (StringUtils.isEmpty(value)) {
			return setResultError("该token可能已经失效或者已经过期");
		}
		
		// 3.转换为整数类型
		Long transactionId = NumberCastUtils.toLong(value);
		// 4.使用transactionId查询支付信息
		PaymentTransactionEntity paymentTransaction = paymentTransactionMapper.selectById(transactionId);
		if (paymentTransaction == null) {
			return setResultError("未查询到该支付信息");
		}
		return setResultSuccess(MeiteBeanUtils.doToDto(paymentTransaction, PayMentTransacOutDTO.class));
	}

}