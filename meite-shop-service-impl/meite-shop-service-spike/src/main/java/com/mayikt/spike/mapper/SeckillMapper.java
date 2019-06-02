package com.mayikt.spike.mapper;

import com.mayikt.spike.mapper.entity.SeckillEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface SeckillMapper {

	@Update("update meite_seckill set inventory=inventory-1 where  seckill_id=#{seckillId};")
	int inventoryDeduction(@Param("seckillId") Long seckillId);

	/**
	 * 使用乐观锁修改库存信息 and inventory>0方式
	 *
	 * @param seckillId
	 * @return
	 */
	@Update("update meite_seckill set inventory=inventory-1 where  seckill_id=#{seckillId} and inventory>0")
	int optimisticLockSeckill(@Param("seckillId") Long seckillId);

	/**
	 * 基于版本号形式实现乐观锁
	 *
	 * @param seckillId
	 * @return
	 */
	@Update("update meite_seckill set inventory=inventory-1 ,version=version+1 where  seckill_id=#{seckillId} and version=#{version} and inventory>0;")
	int optimisticVersionSeckill(@Param("seckillId") Long seckillId, @Param("version") Long version);

	@Select("SELECT seckill_id AS seckillId,name as name,inventory as inventory,start_time as startTime,end_time as endTime,create_time as createTime,version as version from meite_seckill where seckill_id=#{seckillId}")
	SeckillEntity findBySeckillId(@Param("seckillId") Long seckillId);

}