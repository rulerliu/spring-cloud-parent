package com.mayikt.pay.service;

import com.alibaba.fastjson.JSONObject;
import com.mayikt.base.BaseResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @description:
 * @author: liuwq
 * @date: 2019/3/29 0029 上午 10:25
 * @version: V1.0
 * @Copyright:该项目“基于SpringCloud2.x构建微服务电商项目”由每特教育|蚂蚁课堂版权所有，未经过允许的情况下， 私自分享视频和源码属于违法行为。
 */
public interface PayContextService {

    @GetMapping("/toPayHtml")
    BaseResponse<JSONObject> toPayHtml(@RequestParam("channelId") String channelId, @RequestParam("payToken") String payToken);

}
