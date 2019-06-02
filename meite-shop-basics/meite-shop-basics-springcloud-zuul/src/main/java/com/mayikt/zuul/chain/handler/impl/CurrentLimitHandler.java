package com.mayikt.zuul.chain.handler.impl;

import com.google.common.util.concurrent.RateLimiter;
import com.mayikt.core.token.GenerateToken;
import com.mayikt.zuul.chain.handler.GatewayHandler;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class CurrentLimitHandler extends GatewayHandler {
	private RateLimiter rateLimiter = RateLimiter.create(1);

	@Autowired
	private GenerateToken generateToken;

	@Override
	public void service(RequestContext ctx, HttpServletRequest req, HttpServletResponse response) {
		log.info(">>>流程1：接口限流");
		// 1.用户限流频率设置 每秒中限制1个请求
		boolean tryAcquire = rateLimiter.tryAcquire(0, TimeUnit.SECONDS);
		if (!tryAcquire) {
			resultError(500, ctx, "现在抢购的人数过多，请稍等一下下哦！");
			return;
		}

		// 2.使用redis限制用户访问频率
		String seckillId = req.getParameter("seckillId");
		String seckillToken = generateToken.getListKeyToken("seckill_" + seckillId);
		if (StringUtils.isEmpty(seckillToken)) {
			log.info(">>>seckillId:{}, 亲，该秒杀已经售空，请下次再来!", seckillId);
			resultError(500, ctx, "亲，该秒杀已经售空，请下次再来!");
			return;
		}
		// 3.执行修改库存操作
		nextGatewayHandler.service(ctx, req, response);
	}

}
