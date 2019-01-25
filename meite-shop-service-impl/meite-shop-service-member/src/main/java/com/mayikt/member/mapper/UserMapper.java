package com.mayikt.member.mapper;

import com.mayikt.member.mapper.entity.UserDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.repository.query.Param;

public interface UserMapper {

	@Insert("INSERT INTO `meite_user` VALUES (null,#{mobile}, #{email}, #{password}, #{userName}, null, null, null, '1', null, null, null);")
	int register(UserDO userDO);

	@Select("SELECT * FROM meite_user WHERE MOBILE=#{mobile};")
	UserDO existMobile(@Param("mobile") String mobile);
}
