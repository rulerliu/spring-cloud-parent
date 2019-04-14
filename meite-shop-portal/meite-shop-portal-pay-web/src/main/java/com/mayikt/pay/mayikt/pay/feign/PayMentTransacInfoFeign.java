package com.mayikt.pay.mayikt.pay.feign;

import org.springframework.cloud.openfeign.FeignClient;

import com.mayikt.pay.mayikt.pay.service.PayMentTransacInfoService;

@FeignClient("app-mayikt-pay")
public interface PayMentTransacInfoFeign extends PayMentTransacInfoService {

}
