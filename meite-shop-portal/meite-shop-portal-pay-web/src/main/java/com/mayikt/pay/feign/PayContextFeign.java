package com.mayikt.pay.feign;

import org.springframework.cloud.openfeign.FeignClient;

import com.mayikt.pay.service.PayContextService;

@FeignClient("app-mayikt-pay")
public interface PayContextFeign extends PayContextService {

}
