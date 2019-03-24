package com.mayikt.product.service.impl;

import java.util.List;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.web.bind.annotation.RestController;

import com.mayikt.base.BaseApiService;
import com.mayikt.base.BaseResponse;
import com.mayikt.product.api.service.ProductSearchService;
import com.mayikt.product.es.entity.ProductEntity;
import com.mayikt.product.es.reposiory.ProductReposiory;
import com.mayikt.product.output.dto.ProductOutDTO;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

/**
 * @description:
 * @author: liuwq
 * @date: 2019年  下午11:07:54
 * @version: V1.0
 * @Copyright:该项目“基于SpringCloud2.x构建微服务电商项目”由每特教育|蚂蚁课堂版权所有，未经过允许的情况下，
 *            私自分享视频和源码属于违法行为。
 */
@RestController
public class ProductSearchServiceImpl extends BaseApiService<List<ProductOutDTO>> implements ProductSearchService {
	
	@Autowired
	private ProductReposiory productReposiory;

	@Override
	public BaseResponse<List<ProductOutDTO>> search(String name) {
		int i = 1 / 0;
		
		// 1. 拼接查询条件
		BoolQueryBuilder builder = QueryBuilders.boolQuery();
		// 2. 模拟查询name字段
		builder.must(QueryBuilders.fuzzyQuery("name", name));
		Pageable pageable = new QPageRequest(0, 5);
		// 3. 调用ES查询接口
		Page<ProductEntity> page = productReposiory.search(builder, pageable);
		// 4. 获取集合数据
		List<ProductEntity> content = page.getContent();
		// 5. 将entity转换成DTO
		MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
		List<ProductOutDTO> mapAsList = mapperFactory.getMapperFacade().mapAsList(content, ProductOutDTO.class);
		return setResultSuccess(mapAsList);
	}

}
