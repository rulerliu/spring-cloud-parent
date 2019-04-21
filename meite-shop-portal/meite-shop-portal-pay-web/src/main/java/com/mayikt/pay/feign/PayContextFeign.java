package com.mayikt.pay.feign;

import com.mayikt.pay.service.PayContextService;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("app-mayikt-pay")
public interface PayContextFeign extends PayContextService {

}
