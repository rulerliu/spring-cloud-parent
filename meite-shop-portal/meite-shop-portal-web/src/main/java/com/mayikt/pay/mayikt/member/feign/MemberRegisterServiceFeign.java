package com.mayikt.pay.mayikt.member.feign;

import com.mayikt.pay.mayikt.member.service.MemberRegisterService;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("app-mayikt-member")
public interface MemberRegisterServiceFeign extends MemberRegisterService {
}
