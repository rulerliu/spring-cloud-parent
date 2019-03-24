package com.mayikt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import com.spring4all.swagger.EnableSwagger2Doc;

/**
 * @description:
 * @author: liuwq
 * @date: 2019年  下午11:06:05
 * @version: V1.0
 * @Copyright:该项目“基于SpringCloud2.x构建微服务电商项目”由每特教育|蚂蚁课堂版权所有，未经过允许的情况下，
 *            私自分享视频和源码属于违法行为。
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableSwagger2Doc
//@EnableApolloConfig
@MapperScan(basePackages = "com.mayikt.product.mapper")
@EnableElasticsearchRepositories(basePackages = { "com.mayikt.product.es" })
public class ProductApp {

	/**
	 * 查看es：http://192.168.128.130:9200/
	 * 查看es集群节点：http://192.168.128.130:9201/_cat/nodes?pretty
	 * 查看kibana：http://192.168.128.130:5601/app/kibana
	 * 
	 * 查看es分词：http://192.168.128.130:9200/_analyze
	 * {
		  "analyzer": "standard",
		  "text": "苹果"
		}
	 * 
	 * http://localhost:8500/search?name=pad
	 */
	public static void main(String[] args) {
		SpringApplication.run(ProductApp.class, args);
	}
	
}
