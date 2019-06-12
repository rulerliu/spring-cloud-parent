package com.mayikt.core.utils;
/**
 *
 * @description: 使用雪花算法生成全局id
 * @author: liuwq
 * @date: 2019年1月3日 下午3:03:17
 * @version V1.0
 * @Copyright 该项目“基于SpringCloud2.x构建微服务电商项目”由每特教育|蚂蚁课堂版权所有，未经过允许的情况下，
 *            私自分享视频和源码属于违法行为。
 */
public class SnowflakeIdUtils {
	
	private static SnowflakeIdWorker idWorker;
	
	static {
		// 静态代码块初始化SnowflakeIdWorker
		idWorker = new SnowflakeIdWorker(1, 1);
	}

	public static String nextId() {
		return idWorker.nextId() + "";
	}

}