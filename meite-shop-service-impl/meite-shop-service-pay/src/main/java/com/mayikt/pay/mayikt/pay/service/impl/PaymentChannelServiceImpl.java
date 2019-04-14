package com.mayikt.pay.mayikt.pay.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.mayikt.pay.mayikt.base.BaseApiService;
import com.mayikt.pay.mayikt.core.utils.MapperUtils;
import com.mayikt.pay.mayikt.pay.mapper.PaymentChannelMapper;
import com.mayikt.pay.mayikt.pay.mapper.entity.PaymentChannelEntity;
import com.mayikt.pay.mayikt.pay.output.dto.PaymentChannelOutDTO;
import com.mayikt.pay.mayikt.pay.service.PaymentChannelService;

@RestController
public class PaymentChannelServiceImpl extends BaseApiService<List<PaymentChannelOutDTO>>
		implements PaymentChannelService {
	@Autowired
	private PaymentChannelMapper paymentChannelMapper;

	@Override
	public List<PaymentChannelOutDTO> selectAll() {
		List<PaymentChannelEntity> paymentChanneList = paymentChannelMapper.selectAll();
		return MapperUtils.mapAsList(paymentChanneList, PaymentChannelOutDTO.class);
	}

}
