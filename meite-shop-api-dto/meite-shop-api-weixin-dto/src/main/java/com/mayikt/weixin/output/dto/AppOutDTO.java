package com.mayikt.weixin.output.dto;

import lombok.Data;

/**
 * 
 * 
 * @description: App实体类层
 * @author: liuwq
 * @date: 2019年1月3日 下午3:03:17
 * @version V1.0
 * @Copyright 该项目“基于SpringCloud2.x构建微服务电商项目”由每特教育|蚂蚁课堂版权所有，未经过允许的情况下，
 *            私自分享视频和源码属于违法行为。
 */
@Data
public class AppOutDTO {

	/**
	 * appid
	 */
	private String appId;
	/**
	 * 应用名称
	 */
	private String appName;

	public AppOutDTO() {

	}

	public AppOutDTO(String appId, String appName) {
		super();
		this.appId = appId;
		this.appName = appName;
	}

}
