package com.mayikt.member.mapper;

import com.mayikt.member.mapper.entity.UserDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.repository.query.Param;

public interface UserMapper {

	/**
	 * 插入数据
	 * @param userDO
	 * @return
	 */
	@Insert("INSERT INTO `meite_user` VALUES (null,#{mobile}, #{email}, #{password}, #{userName}, null, null, null, '1', null, null, null);")
	int register(UserDO userDO);

	/**
	 * 根据手机号码查询
	 * @param mobile
	 * @return
	 */
	@Select("SELECT * FROM meite_user WHERE MOBILE=#{mobile};")
	UserDO existMobile(@Param("mobile") String mobile);

	/**
	 * 根据手机号码 + 密码查询
	 * @param mobile
	 * @param password
	 * @return
	 */
	@Select("SELECT USER_ID AS USERID ,MOBILE AS MOBILE,EMAIL AS EMAIL,PASSWORD AS PASSWORD, USER_NAME AS USER_NAME ,SEX AS SEX ,AGE AS AGE ,CREATE_TIME AS CREATETIME,IS_AVALIBLE AS ISAVALIBLE,PIC_IMG AS PICIMG,QQ_OPENID AS QQOPENID,WX_OPENID AS WXOPENID "
			+ "  FROM meite_user  WHERE MOBILE = #{0} and password = #{1};")
	UserDO login(@Param("mobile") String mobile, @Param("password") String password);

	/**
	 * 根据userId查询
	 * @param userId
	 * @return
	 */
	@Select("SELECT USER_ID AS USERID ,MOBILE AS MOBILE,EMAIL AS EMAIL,PASSWORD AS PASSWORD, USER_NAME AS USER_NAME ,SEX AS SEX ,AGE AS AGE ,CREATE_TIME AS CREATETIME,IS_AVALIBLE AS ISAVALIBLE,PIC_IMG AS PICIMG,QQ_OPENID AS QQOPENID,WX_OPENID AS WXOPENID"
			+ " FROM meite_user WHERE user_Id = #{userId}")
	UserDO findByUserId(@Param("userId") Long userId);


}
