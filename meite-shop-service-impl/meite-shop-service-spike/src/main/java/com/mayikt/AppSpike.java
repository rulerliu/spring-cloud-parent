package com.mayikt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@MapperScan(basePackages = "com.mayikt.spike.mapper")
public class AppSpike {

	/**
	 * http://127.0.0.1:9800/spike?phone=18529103439&seckillId=1
	 *
	 * http://127.0.0.1:9800/addSpikeToken?seckillId=1&tokenQuantity=100
	 *
	 *
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(AppSpike.class, args);
	}

}
