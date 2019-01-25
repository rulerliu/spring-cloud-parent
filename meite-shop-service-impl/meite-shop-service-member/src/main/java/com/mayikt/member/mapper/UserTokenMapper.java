package com.mayikt.member.mapper;

import com.mayikt.member.mapper.entity.UserTokenDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface UserTokenMapper {

	/**
	 * 根据userId + loginType 查询
	 * @param userId
	 * @param loginType
	 * @return
	 */
	@Select("SELECT id as id ,token as token ,login_type as LoginType, device_infor as deviceInfor ,is_availability as isAvailability,user_id as userId"
			+ " , CREATE_TIME as createTime,update_time as updateTime   " +
			"FROM meite_user_token WHERE user_id=#{userId} AND login_type=#{loginType} and is_availability ='0'; ")
	UserTokenDO selectByUserIdAndLoginType(@Param("userId") Long userId, @Param("loginType") String loginType);

	/**
	 * 根据token更新可用状态
	 * @param userId
	 * @param loginType
	 * @return
	 */
	@Update("    update meite_user_token set is_availability ='1',update_time=now()   where token =#{token} ")
	int updateTokenAvailability(@Param("token") String token);

	// INSERT INTO `meite_user_token` VALUES ('2', '1', 'PC', '苹果7p', '1', '1');

	/**
	 * 插入数据
	 * @param userTokenDo
	 * @return
	 */
	@Insert("    INSERT INTO `meite_user_token`(user_id, token, login_type, device_infor, is_availability) VALUES (#{userId}, #{token},#{loginType}, #{deviceInfor}, 0); ")
	int insertUserToken(UserTokenDO userTokenDo);
}
