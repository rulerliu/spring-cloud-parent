package com.mayikt.spike.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.mayikt.base.BaseApiService;
import com.mayikt.base.BaseResponse;
import com.mayikt.core.utils.RedisUtil;
import com.mayikt.spike.api.service.SpikeCommodityService;
import com.mayikt.spike.mapper.OrderMapper;
import com.mayikt.spike.mapper.SeckillMapper;
import com.mayikt.spike.mapper.entity.OrderEntity;
import com.mayikt.spike.mapper.entity.SeckillEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: liuwq
 * @date: 2019/6/2 0002 17:28
 * @version: V1.0
 * @Copyright:该项目“基于SpringCloud2.x构建微服务电商项目”由每特教育|蚂蚁课堂版权所有，未经过允许的情况下， 私自分享视频和源码属于违法行为。
 */
@RestController
@Slf4j
public class SpikeCommodityServiceImpl extends BaseApiService<JSONObject> implements SpikeCommodityService {

    @Autowired
    private SeckillMapper seckillMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public BaseResponse<JSONObject> spike(String phone, Long seckillId) {
        // 1.参数验证
        if (StringUtils.isEmpty(phone)) {
            return setResultError("手机号码不能为空!");
        }
        if (seckillId == null) {
            return setResultError("商品库存id不能为空!");
        }
        SeckillEntity seckillEntity = seckillMapper.findBySeckillId(seckillId);
        if (seckillEntity == null) {
            return setResultError("商品信息不存在!");
        }

        // 2.用户频率验证
        Boolean reusltNx = redisUtil.setNx("seckill_" + phone, seckillId + "", 10L);
        if (!reusltNx) {
            log.error(">>>访问次数过快，请10秒后在重试!");
            return setResultError("访问次数过快，请10秒后在重试!");
        }

        // 3.修改库存
//        int inventoryDeduction = seckillMapper.optimisticLockSeckill(seckillId);
        Long version = seckillEntity.getVersion();
        int inventoryDeduction = seckillMapper.optimisticVersionSeckill(seckillId, version);
        if (!toDaoResult(inventoryDeduction)) {
            log.error(">>>修改库存失败>>>>inventoryDeduction返回为{} 秒杀失败！", inventoryDeduction);
            return setResultError("亲，请稍后重试!");
        }

        // 4.添加秒杀成功订单（基于MQ异步）
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setUserPhone(phone);
        orderEntity.setSeckillId(seckillId);
        int insertOrder = orderMapper.insertOrder(orderEntity);
        if (!toDaoResult(insertOrder)) {
            return setResultError("亲，请稍后重试!");
        }
        log.info(">>>添加订单成功>>>>insertOrder返回为{} 秒杀成功", inventoryDeduction);
        return setResultSuccess("恭喜您，秒杀成功!");
    }

}
