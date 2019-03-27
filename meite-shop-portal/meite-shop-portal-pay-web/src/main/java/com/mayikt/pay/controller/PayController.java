package com.mayikt.pay.controller;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mayikt.base.BaseResponse;
import com.mayikt.pay.feign.PayMentTransacInfoFeign;
import com.mayikt.pay.feign.PaymentChannelFeign;
import com.mayikt.pay.output.dto.PayMentTransacOutDTO;
import com.mayikt.pay.output.dto.PaymentChannelOutDTO;
import com.mayikt.web.base.BaseWebController;

/**
 * @description:
 * @author: liuwq
 * @date: 2019年  上午12:44:19
 * @version: V1.0
 * @Copyright:该项目“基于SpringCloud2.x构建微服务电商项目”由每特教育|蚂蚁课堂版权所有，未经过允许的情况下，
 *            私自分享视频和源码属于违法行为。
 */
@Controller
public class PayController extends BaseWebController {
	
	@Autowired
	private PayMentTransacInfoFeign payMentTransacInfoFeign;
	
	@Autowired
	private PaymentChannelFeign paymentChannelFeign;
	
	@RequestMapping("/")
	public String index() {
		return "index";
	}

	@RequestMapping("/pay")
	public String pay(String payToken, Model model) {
		// 1.验证payToken参数
		if (StringUtils.isEmpty(payToken)) {
			setErrorMsg(model, "支付令牌不能为空!");
			return ERROR_500;
		}
		
		// 2.使用payToken查询支付信息
		BaseResponse<PayMentTransacOutDTO> tokenByPayMentTransac = payMentTransacInfoFeign.tokenByPayMentTransac(payToken);
		if (!isSuccess(tokenByPayMentTransac)) {
			setErrorMsg(model, tokenByPayMentTransac.getMsg());
			return ERROR_500;
		}
		
		// 3.查询支付信息
		PayMentTransacOutDTO data = tokenByPayMentTransac.getData();
		model.addAttribute("data", data);
		
		// 4.查询渠道信息
		List<PaymentChannelOutDTO> paymentChanneList = paymentChannelFeign.selectAll();
		model.addAttribute("paymentChanneList", paymentChanneList);
		return "index";
	}
	
}
