package com.mayikt.authorization.api.service;

import com.alibaba.fastjson.JSONObject;
import com.mayikt.base.BaseResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @description:
 * @author: liuwq
 * @date: 2019/6/1 0001 19:23
 * @version: V1.0
 * @Copyright:该项目“基于SpringCloud2.x构建微服务电商项目”由每特教育|蚂蚁课堂版权所有，未经过允许的情况下， 私自分享视频和源码属于违法行为。
 */
public interface AuthorizationService {

    /**
     * 机构申请 获取appid 和appsecret
     *
     * @return
     */
    @GetMapping("/applyAppInfo")
    public BaseResponse<JSONObject> applyAppInfo(@RequestParam("appName") String appName);

    /*
     * 使用appid 和appsecret密钥获取AccessToken
     */
    @GetMapping("/getAccessToken")
    public BaseResponse<JSONObject> getAccessToken(@RequestParam("appId") String appId,
                                                   @RequestParam("appSecret") String appSecret);

    /*
     * 验证Token是否失效
     */
    @GetMapping("/getAppInfo")
    public BaseResponse<JSONObject> getAppInfo(@RequestParam("accessToken") String accessToken);

}
