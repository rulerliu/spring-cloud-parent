package com.mayikt.core.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	/**
	 * 开启redis事务
	 */
	public void begin() {
		// 开始redis 事务权限
		stringRedisTemplate.setEnableTransactionSupport(true);
		// 开启事务
		stringRedisTemplate.multi();
	}

    /**
     * 提交redis事务
     */
	public void exec() {
        stringRedisTemplate.exec();
    }

    /**
     * 回滚redis 事务
     */
    public void discard() {
        stringRedisTemplate.discard();
    }

    /**
	 * 存放string类型
	 * 
	 * @param key
	 *            key
	 * @param data
	 *            数据
	 * @param timeout
	 *            超时间
	 */
	public void setString(String key, String data, Long timeout) {
		stringRedisTemplate.opsForValue().set(key, data);
		if (timeout != null) {
			stringRedisTemplate.expire(key, timeout, TimeUnit.SECONDS);
		}
	}

	public StringRedisTemplate getStringRedisTemplate() {
		return stringRedisTemplate;
	}

	/**
	 * 如果key不存在的话返回true，存在的话返回fasle
	 * @param key
	 * @param value
	 * @param timeout 单位：秒
	 * @return
	 */
	public Boolean setNx(String key, String value, Long timeout) {
		Boolean setIfAbsent = stringRedisTemplate.opsForValue().setIfAbsent(key, value);
		if (timeout != null) {
			stringRedisTemplate.expire(key, timeout, TimeUnit.SECONDS);
		}
		return setIfAbsent;
	}

	public void setList(String key, List<String> listToken) {
		stringRedisTemplate.opsForList().leftPushAll(key, listToken);
	}

	/**
	 * 存放string类型
	 * 
	 * @param key
	 *            key
	 * @param data
	 *            数据
	 */
	public void setString(String key, String data) {
		setString(key, data, null);
	}

	/**
	 * 根据key查询string类型
	 * 
	 * @param key
	 * @return
	 */
	public String getString(String key) {
		String value = stringRedisTemplate.opsForValue().get(key);
		return value;
	}

	/**
	 * 根据对应的key删除key
	 * 
	 * @param key
	 */
	public Boolean delKey(String key) {
		return stringRedisTemplate.delete(key);
	}
}
