package com.mayikt.pay.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.mayikt.pay.mq.producer.IntegralProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: liuwq
 * @date: 2019/4/21 0021 18:12
 * @version: V1.0
 * @Copyright:该项目“基于SpringCloud2.x构建微服务电商项目”由每特教育|蚂蚁课堂版权所有，未经过允许的情况下， 私自分享视频和源码属于违法行为。
 */
@RestController
public class MQTestServiceImpl {

    @Autowired
    private IntegralProducer integralProducer;

    /**
     * http://127.0.0.1:8600/send
     * @return
     */
    @GetMapping("/send")
    public String send() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("paymentId", "1234567890");
        jsonObject.put("userId", "123456");
        jsonObject.put("integral", 100);
        integralProducer.send(jsonObject);
        return "success";
    }

}
