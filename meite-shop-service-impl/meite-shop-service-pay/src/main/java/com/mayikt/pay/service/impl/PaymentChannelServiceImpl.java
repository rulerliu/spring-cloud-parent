package com.mayikt.pay.service.impl;

import com.mayikt.base.BaseApiService;
import com.mayikt.core.utils.MapperUtils;
import com.mayikt.pay.mapper.PaymentChannelMapper;
import com.mayikt.pay.mapper.entity.PaymentChannelEntity;
import com.mayikt.pay.output.dto.PaymentChannelOutDTO;
import com.mayikt.pay.service.PaymentChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
