package com.mayikt.spike.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.mayikt.base.BaseApiService;
import com.mayikt.base.BaseResponse;
import com.mayikt.core.token.GenerateToken;
import com.mayikt.spike.api.service.SpikeCommodityService;
import com.mayikt.spike.mapper.OrderMapper;
import com.mayikt.spike.mapper.SeckillMapper;
import com.mayikt.spike.mapper.entity.SeckillEntity;
import com.mayikt.spike.producer.SpikeCommodityProducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
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

//    @Autowired
//    private RedisUtil redisUtil;

    @Autowired
    private GenerateToken generateToken;

    @Autowired
    private SpikeCommodityProducer spikeCommodityProducer;

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

        /*
        // 2.用户频率验证 限流(应该在网关中实现)
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

        // 4.添加秒杀成功订单
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setUserPhone(phone);
        orderEntity.setSeckillId(seckillId);
        int insertOrder = orderMapper.insertOrder(orderEntity);
        if (!toDaoResult(insertOrder)) {
            return setResultError("亲，请稍后重试!");
        }
        log.info(">>>添加订单成功>>>>insertOrder返回为{} 秒杀成功", inventoryDeduction);
        return setResultSuccess("恭喜您，秒杀成功!");
        */


        //基于MQ异步实现方案
        // 2.从redis获取对应的秒杀token
        String seckillToken = generateToken.getListKeyToken("seckill_" + seckillId);
        if (StringUtils.isEmpty(seckillToken)) {
            log.info(">>>seckillId:{}, 亲，该秒杀已经售空，请下次再来!", seckillId);
            return setResultError("亲，该秒杀已经售空，请下次再来!");
        }

        // 3.获取到秒杀token之后，异步放入到MQ中
        sendSeckillMsg(seckillId, phone);
        return setResultSuccess("正在排队中.......");
    }

    /**
     * 获取到秒杀token之后，异步放入mq中实现修改商品的库存
     */
    @Async
    public void sendSeckillMsg(Long seckillId, String phone) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("seckillId", seckillId);
        jsonObject.put("phone", phone);
        spikeCommodityProducer.send(jsonObject);
    }


    /**
     * 采用redis数据库类型为 list类型 key为 商品库存id list 多个秒杀token
     * @param seckillId
     * @param tokenQuantity
     * @return
     */
    @Override
    public BaseResponse<JSONObject> addSpikeToken(Long seckillId, Long tokenQuantity) {
        // 1.验证参数
        if (seckillId == null) {
            return setResultError("商品库存id不能为空!");
        }
        if (tokenQuantity == null) {
            return setResultError("token数量不能为空!");
        }
        SeckillEntity seckillEntity = seckillMapper.findBySeckillId(seckillId);
        if (seckillEntity == null) {
            return setResultError("商品信息不存在!");
        }
        // 2.使用多线程异步生产令牌
        createSeckillToken(seckillId, tokenQuantity);
        return setResultSuccess("令牌正在生成中.....");
    }

    @Async
    public void createSeckillToken(Long seckillId, Long tokenQuantity) {
        generateToken.createListToken("seckill_", seckillId + "", tokenQuantity);
    }

}
