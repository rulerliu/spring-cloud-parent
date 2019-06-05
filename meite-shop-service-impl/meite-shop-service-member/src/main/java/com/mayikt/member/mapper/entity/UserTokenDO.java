package com.mayikt.member.mapper.entity;

import com.mayikt.base.BaseDO;
import lombok.Data;

@Data
public class UserTokenDO extends BaseDO {
	/**
	 * id
	 */
	private Long id;
	/**
	 * 用户token
	 */
	private String token;
	/**
	 * 登陆类型
	 */
	private String loginType;

	/**
	 * 设备信息
	 */
	private String deviceInfor;
	/**
	 * 用户userId
	 */
	private Long userId;
//	/**
//	 * 注册时间
//	 */
//	private Date createTime;
//	/**
//	 * 修改时间
//	 *
//	 */
//	private Date updateTime;
}
