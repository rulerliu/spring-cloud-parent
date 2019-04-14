package com.mayikt.pay.mayikt.member.feign;

import com.mayikt.pay.mayikt.member.service.MemberService;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("app-mayikt-member")
public interface MemberServiceFeign extends MemberService {
}
