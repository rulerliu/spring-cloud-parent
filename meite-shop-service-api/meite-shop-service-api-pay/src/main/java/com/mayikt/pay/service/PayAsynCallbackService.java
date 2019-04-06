package com.mayikt.pay.service;

import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description:
 * @author: liuwq
 * @date: 2019/4/6 0006 14:24
 * @version: V1.0
 * @Copyright:该项目“基于SpringCloud2.x构建微服务电商项目”由每特教育|蚂蚁课堂版权所有，未经过允许的情况下， 私自分享视频和源码属于违法行为。
 */
public interface PayAsynCallbackService {

    @RequestMapping("/unionPayAsynCallback")
    String unionPayAsynCallback(HttpServletRequest req, HttpServletResponse resp);

    @RequestMapping("/aliPayAsynCallback")
    String aliPayAsynCallback(HttpServletRequest req, HttpServletResponse resp);

}
