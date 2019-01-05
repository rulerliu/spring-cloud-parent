package com.mayikt;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import com.spring4all.swagger.EnableSwagger2Doc;

import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

/**
 * @description:
 * @author: liuwq
 * @date: 2019年  上午12:02:02
 * @version: V1.0
 * @Copyright:该项目“基于SpringCloud2.x构建微服务电商项目”由每特教育|蚂蚁课堂版权所有，未经过允许的情况下，
 *            私自分享视频和源码属于违法行为。
 */
@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
@EnableSwagger2Doc
public class AppZuul {

	public static void main(String[] args) {
		SpringApplication.run(AppZuul.class, args);
	}
	
	/**
	 * http://127.0.0.1/swagger-ui.html
	 * 添加文档来源
	 */
	@Component
	@Primary
	class DocumentationConfig implements SwaggerResourcesProvider {
		@Override
		public List<SwaggerResource> get() {
			List<SwaggerResource> resources = new ArrayList<SwaggerResource>();
			resources.add(swaggerResource("app-mayikt-member", "/app-mayikt-member/v2/api-docs", "2.0"));
			resources.add(swaggerResource("app-mayikt-weixin", "/app-mayikt-weixin/v2/api-docs", "2.0"));
			return resources;
		}

		/**
		 * @param name 服务service-id
		 * @param location 访问数据json串的location
		 * @param version
		 * @return
		 */
		private SwaggerResource swaggerResource(String name, String location, String version) {
			SwaggerResource swaggerResource = new SwaggerResource();
			swaggerResource.setName(name);
			swaggerResource.setLocation(location);
			swaggerResource.setSwaggerVersion(version);
			return swaggerResource;
		}

	}

}
