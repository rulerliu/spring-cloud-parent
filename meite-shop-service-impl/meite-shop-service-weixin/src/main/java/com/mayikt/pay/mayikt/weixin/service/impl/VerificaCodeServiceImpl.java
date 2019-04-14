package com.mayikt.pay.mayikt.weixin.service.impl;

import com.mayikt.pay.mayikt.base.BaseApiService;
import com.mayikt.pay.mayikt.base.BaseResponse;
import com.mayikt.pay.mayikt.constants.Constants;
import com.mayikt.pay.mayikt.core.utils.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.mayikt.pay.mayikt.weixin.service.VerificaCodeService;

@RestController
public class VerificaCodeServiceImpl extends BaseApiService<JSONObject> implements VerificaCodeService {
	@Autowired
	private RedisUtil redisUtil;

	/**
	 * 根据手机号码验证，注册码是否正确
	 */
	@Override
	public BaseResponse<JSONObject> verificaWeixinCode(String phone, String weixinCode) {
		// 1.判断参数是否为空
		if (StringUtils.isBlank(phone)) {
			return setResultError("手机号码不能为空");
		}
		if (StringUtils.isBlank(weixinCode)) {
			return setResultError("注册码不能为空");
		}
		
		// 2.根据手机号码查询redis中的注册码
		String registCode = redisUtil.getString(Constants.WEIXINCODE_KEY + phone);
		if (StringUtils.isBlank(registCode)) {
			return setResultError("注册码不存在或者已经过期");
		}
		
		// 3.校验注册码是否一致
		if (!weixinCode.equals(registCode)) {
			return setResultError("注册码不正确");
		}
		
		// 4.移除redis中的key
		redisUtil.delKey(Constants.WEIXINCODE_KEY + phone);
		return setResultSuccess("注册码正确");
	}

}
