package com.mayikt.spike.api.service;

import com.alibaba.fastjson.JSONObject;
import com.mayikt.base.BaseResponse;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @description:
 * @author: liuwq
 * @date: 2019/6/2 0002 17:26
 * @version: V1.0
 * @Copyright:该项目“基于SpringCloud2.x构建微服务电商项目”由每特教育|蚂蚁课堂版权所有，未经过允许的情况下， 私自分享视频和源码属于违法行为。
 */
public interface SpikeCommodityService {

    /**
     * 用户秒杀接口 phone和userid都可以的
     *
     * @phone 手机号码<br>
     * @seckillId 库存id
     * @return
     */
    @RequestMapping("/spike")
    public BaseResponse<JSONObject> spike(String phone, Long seckillId);

}
