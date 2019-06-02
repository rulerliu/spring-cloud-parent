package com.mayikt.spike.mq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class RabbitmqConfig {

	// 添加修改库存队列
	public static final String MODIFY_INVENTORY_QUEUE = "modify_inventory_queue";
	// 交换机名称
	public static final String MODIFY_INVENTORY__EXCHANGE = "modify_inventory_exchange";
	// 路由键
	public static final String MODIFY_INVENTORY_ROUTING_KEY = "modifyInventoryRoutingKey";

	// 1.添加交换机队列
	@Bean
	public Queue directModifyInventoryQueue() {
		return new Queue(MODIFY_INVENTORY_QUEUE);
	}

	// 2.定义交换机
	@Bean
	DirectExchange directModifyExchange() {
		return new DirectExchange(MODIFY_INVENTORY__EXCHANGE);
	}

	// 3.修改库存队列绑定交换机
	@Bean
	Binding bindingExchangeintegralDicQueue() {
		return BindingBuilder.bind(directModifyInventoryQueue()).to(directModifyExchange()).with(MODIFY_INVENTORY_ROUTING_KEY);
	}

}
