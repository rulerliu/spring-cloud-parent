package com.mayikt.spike.api.service;

import com.alibaba.fastjson.JSONObject;
import com.mayikt.base.BaseResponse;
import org.springframework.web.bind.annotation.RequestMapping;

public interface OrderSeckillService {

	@RequestMapping("/getOrder")
	public BaseResponse<JSONObject> getOrder(String phone, Long seckillId);

}