package com.mayikt.pay.mayikt.pay.feign;

import org.springframework.cloud.openfeign.FeignClient;

import com.mayikt.pay.mayikt.pay.service.PaymentChannelService;

@FeignClient("app-mayikt-pay")
public interface PaymentChannelFeign extends PaymentChannelService {

}
