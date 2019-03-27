package com.mayikt.pay.service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mayikt.base.BaseResponse;
import com.mayikt.pay.output.dto.PayMentTransacOutDTO;

public interface PayMentTransacInfoService {
	
	@GetMapping("/tokenByPayMentTransac")
	public BaseResponse<PayMentTransacOutDTO> tokenByPayMentTransac(@RequestParam("token") String token);
	
}