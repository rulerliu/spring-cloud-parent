package com.mayikt.pay.mayikt.pay.feign;

import com.mayikt.pay.mayikt.pay.service.PayContextService;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("app-mayikt-pay")
public interface PayContextFeign extends PayContextService {

}
