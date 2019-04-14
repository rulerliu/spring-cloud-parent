package com.mayikt.pay.mayikt.product.es.reposiory;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.mayikt.pay.mayikt.product.es.entity.ProductEntity;

public interface ProductReposiory extends ElasticsearchRepository<ProductEntity, Long> {

}