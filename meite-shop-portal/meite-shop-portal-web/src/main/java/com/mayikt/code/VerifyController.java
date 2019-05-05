package com.mayikt.code;

import com.mayikt.web.utils.RandomValidateCodeUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class VerifyController {

	/**
	 * 生成图形验证码
	 */
	@GetMapping(value = "/getVerify")
	public void getVerify(HttpServletRequest request, HttpServletResponse response) {
		try {
			// 设置相应类型,告诉浏览器输出的内容为图片
			response.setContentType("image/jpeg");
			// 设置响应头信息，告诉浏览器不要缓存此内容
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expire", 0);
			RandomValidateCodeUtil randomValidateCode = new RandomValidateCodeUtil();
			// 输出验证码图片方法
			randomValidateCode.getRandcode(request, response);
		} catch (Exception e) {

		}
	}
}