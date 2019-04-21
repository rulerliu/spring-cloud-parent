package com.mayikt.pay.service;

import com.mayikt.pay.output.dto.PaymentChannelOutDTO;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public interface PaymentChannelService {
	/**
	 * 查询所有支付渠道
	 * 
	 * @return
	 */
	@GetMapping("/selectAll")
	public List<PaymentChannelOutDTO> selectAll();
}
