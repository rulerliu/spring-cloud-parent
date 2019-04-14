package com.mayikt.pay.mayikt.product.api.service;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;

import com.mayikt.pay.mayikt.base.BaseResponse;
import com.mayikt.pay.mayikt.product.output.dto.ProductOutDTO;

/**
 * @description:
 * @author: liuwq
 * @date: 2019年  下午10:54:40
 * @version: V1.0
 * @Copyright:该项目“基于SpringCloud2.x构建微服务电商项目”由每特教育|蚂蚁课堂版权所有，未经过允许的情况下，
 *            私自分享视频和源码属于违法行为。
 */
public interface ProductSearchService {
	
	@GetMapping("/search")
	public BaseResponse<List<ProductOutDTO>> search(String name);

}
