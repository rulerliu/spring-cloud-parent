package com.mayikt;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import com.spring4all.swagger.EnableSwagger2Doc;

/**
 * @description:
 * @author: liuwq
 * @date: 2019年  下午11:38:12
 * @version: V1.0
 * @Copyright:该项目“基于SpringCloud2.x构建微服务电商项目”由每特教育|蚂蚁课堂版权所有，未经过允许的情况下，
 *            私自分享视频和源码属于违法行为。
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableSwagger2Doc
@EnableApolloConfig
@MapperScan(basePackages = "com.mayikt.member.mapper")
public class AppMember {

	/**
	 * http://127.0.0.1:8300/swagger-ui.html#/
	 * http://localhost:8300/memberInvokeWeixin
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(AppMember.class, args);
	}
	
}
