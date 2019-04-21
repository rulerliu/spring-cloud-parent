package com.mayikt.member.feign;

import com.mayikt.member.service.QQAuthoriService;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("app-mayikt-member")
public interface QQAuthoriServiceFeign extends QQAuthoriService {
}