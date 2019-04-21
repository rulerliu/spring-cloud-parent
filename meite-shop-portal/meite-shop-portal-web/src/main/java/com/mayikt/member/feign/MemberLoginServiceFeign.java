package com.mayikt.member.feign;

import com.mayikt.member.service.MemberLoginService;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("app-mayikt-member")
public interface MemberLoginServiceFeign extends MemberLoginService {
}
