package com.mayikt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import com.spring4all.swagger.EnableSwagger2Doc;

/**
 * @description:
 * @author: liuwq
 * @date: 2019年3月26日 下午10:45:48
 * @version: V1.0
 * @Copyright:该项目“基于SpringCloud2.x构建微服务电商项目”由每特教育|蚂蚁课堂版权所有，未经过允许的情况下，
 *            私自分享视频和源码属于违法行为。
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableSwagger2Doc
//@EnableApolloConfig
@MapperScan(basePackages = "com.mayikt.pay.mapper")
public class PayApp {

	/**
	 * http://127.0.0.1:8600/cratePayToken?payAmount=20000&orderId=1234567890&userId=1234
	 *
	 * http://127.0.0.1:8600/tokenByPayMentTransac?token=
	 *
	 * http://127.0.0.1:8600/toPayHtml?channelId=yinlian_pay&payToken=
	 *
	 * http://127.0.0.1:8050/pay?payToken=
	 */
	public static void main(String[] args) {
		SpringApplication.run(PayApp.class, args);
	}
	
}
