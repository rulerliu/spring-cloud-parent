package com.mayikt.pay.mayikt.pay.input.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.ToString;

/**
 * @description:
 * @author: liuwq
 * @date: 2019年 下午10:58:02
 * @version: V1.0
 * @Copyright:该项目“基于SpringCloud2.x构建微服务电商项目”由每特教育|蚂蚁课堂版权所有，未经过允许的情况下， 私自分享视频和源码属于违法行为。
 */
@Data
@ToString
public class CreatePayTokenInpDTO {
	/**
	 * 支付金额
	 */
	@NotNull(message = "支付金额不能为空")
	private Long payAmount;
	/**
	 * 订单号码
	 */
	@NotNull(message = "订单号码不能为空")
	private String orderId;

	/**
	 * userId
	 */
	@NotNull(message = "userId不能空")
	private Long userId;
}
