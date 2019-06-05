package com.mayikt.core.utils;///**
// * 
// */
//package com.mayikt.util;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
///**  
//* <p>Title: RedisLockUtils</p>  
//* <p>Description: redis锁工具类 </p>  
//* @author liuwq  
//* @date 2019年4月2日  下午5:07:06
//*/
//public class RedisLockUtils {
//	private final static Logger logger = LoggerFactory.getLogger(RedisLockUtils.class);
//	
//	public static final int REDIS_LOCK_EXPIRE = 2;//redis 锁超时时间
//	public static final int REDIS_LOCK_RETRY_COUNT = 20;//redis 锁重试次数
//	public static final long REDIS_LOCK_RETRY_INTERVAL_TIME = 100L;//redis 锁每次重试时间间隔
//	
//	// 注入redis
//	private CacheManager cacheManager;
//
//	public RedisLockUtils(){
//
//	}
//	public RedisLockUtils(CacheManager cacheManager){
//		this.cacheManager=cacheManager;
//	}
//	/**
//	 * 加锁
//	 */
//	public boolean lock(final String key, final String value, final int seconds) {
//		boolean result = true;
//		if (cacheManager.setnx(key.getBytes(), value.getBytes()) == 1) {
//			cacheManager.setValueExpireTime(key.getBytes(), seconds);
//		} else {
//			result = false; // 锁失败
//		}
//		return result;
//	}
//	
//	/**
//	 * 释放锁
//	 */
//	public void unlock(final String key) {
//		 cacheManager.del(key.getBytes());
//	}
//
//
//	public static String getLockKey(String busiCode, String actId, String phoneNo) {
//		return busiCode + "_" + actId + "_" + phoneNo;
//	}
//	public  Boolean lockService(String lockKey){
//		long count = 0;
//		while (true) {
//			try {
//				if (lock(lockKey, lockKey, REDIS_LOCK_EXPIRE)) {
//					return true;
//				}
//				Thread.sleep(REDIS_LOCK_RETRY_INTERVAL_TIME);
//				count++;
//				if (count >= REDIS_LOCK_RETRY_COUNT) {
//					logger.error("redisKey:{},获取锁次数累计超过{}次", lockKey, REDIS_LOCK_RETRY_COUNT);
//					return false;
//				}
//
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//				return false;
//			}
//		}
//	}
//	
//	public static void main(String[] args) {
//		RedisLockUtils lockUtils = new RedisLockUtils(cacheManager);
//		String lockKey = RedisLockUtils.getLockKey("RECHARGE_LOCK", "", "");
//		if (!lockUtils.lockService(lockKey)) {
//			logger.error("[3000127服务],获取redis锁失败，累计重试次数已经超过20次,手机号码{},活动id{}", "", "");
//		}
//		
//		try {
//			// todo
//			System.out.println(Thread.currentThread().getName() + "获取锁成功");
//		} catch (Exception e) {
//			logger.error("[3000127服务]出现异常:{}", e);
//		} finally {
//			lockUtils.unlock(lockKey);
//		}
//	}
//	
//}
