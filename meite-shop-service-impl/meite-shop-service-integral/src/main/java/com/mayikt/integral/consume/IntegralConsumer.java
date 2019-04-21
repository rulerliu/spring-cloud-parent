package com.mayikt.integral.consume;

import com.alibaba.fastjson.JSONObject;
import com.mayikt.constants.PayConstants;
import com.mayikt.integral.mapper.IntegralMapper;
import com.mayikt.integral.mapper.entity.IntegralEntity;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * 
 * 
 * 
 * @description: 积分服务消费者
 * @author: 97后互联网架构师-余胜军
 * @contact: QQ644064779、微信yushengjun644 www.mayikt.com
 * @date: 2019年1月3日 下午3:03:17
 * @version V1.0
 * @Copyright 该项目“基于SpringCloud2.x构建微服务电商项目”由每特教育|蚂蚁课堂版权所有，未经过允许的情况下，
 *            私自分享视频和源码属于违法行为。
 */
@Component
@Slf4j
public class IntegralConsumer {

	@Autowired
	private IntegralMapper integralMapper;

	@RabbitListener(queues = PayConstants.INTEGRAL_DIC_QUEUE)
	public void process(Message message, @Headers Map<String, Object> headers, Channel channel) throws IOException {
		try {
			String messageId = message.getMessageProperties().getMessageId();
			String msg = new String(message.getBody(), "UTF-8");
			log.info(">>>messageId:{},msg:{}", messageId, msg);
			JSONObject jsonObject = JSONObject.parseObject(msg);
			String paymentId = jsonObject.getString("paymentId");
			if (StringUtils.isEmpty(paymentId)) {
				log.error(">>>>支付id不能为空 paymentId:{}", paymentId);
				basicNack(message, channel);
				return;
			}

			// 使用paymentId查询是否已经增加过积分 网络重试间隔，解决幂等性
			IntegralEntity resultIntegralEntity = integralMapper.findIntegral(paymentId);
			if (resultIntegralEntity != null) {
				log.error(">>>>paymentId:{}已经增加过积分", paymentId);
				// 已经增加过积分，通知MQ不要在继续重试。
				basicNack(message, channel);
				return;
			}

			Integer userId = jsonObject.getInteger("userId");
			if (userId == null) {
				log.error(">>>>paymentId:{},对应的用户userId参数为空", paymentId);
				basicNack(message, channel);
				return;
			}

			Long integral = jsonObject.getLong("integral");
			if (integral == null) {
				log.error(">>>>paymentId:{},对应的用户integral参数为空", integral);
				return;
			}

			IntegralEntity integralEntity = new IntegralEntity();
			integralEntity.setPaymentId(paymentId);
			integralEntity.setIntegral(integral);
			integralEntity.setUserId(userId);
			integralEntity.setAvailability(1);
			// 插入到数据库中
			int insertIntegral = integralMapper.insertIntegral(integralEntity);
			if (insertIntegral > 0) {
				// 手动签收消息,通知mq服务器端删除该消息
				basicNack(message, channel);
			}
			// 采用重试机制
		} catch (Exception e) {
			log.error(">>>>ERROR MSG:", e.getMessage());
			basicNack(message, channel);
		}

	}

	/**
	 * 消费者获取到消息之后 手动签收 通知MQ删除该消息
	 * @param message
	 * @param channel
	 * @throws IOException
	 */
	private void basicNack(Message message, Channel channel) throws IOException {
		channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
	}

	// 什么场景下 适合于重试 网络连接、空指针 参数错误
}
