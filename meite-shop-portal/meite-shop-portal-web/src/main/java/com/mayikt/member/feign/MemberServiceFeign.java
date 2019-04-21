package com.mayikt.member.feign;

import com.mayikt.member.service.MemberService;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("app-mayikt-member")
public interface MemberServiceFeign extends MemberService {
}
