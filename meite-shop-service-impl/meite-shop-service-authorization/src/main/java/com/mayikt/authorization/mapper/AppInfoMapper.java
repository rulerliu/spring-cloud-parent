package com.mayikt.authorization.mapper;

import com.mayikt.authorization.mapper.entity.MeiteAppInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface AppInfoMapper {

	@Insert("INSERT INTO `meite_app_info` VALUES (null,#{appName}, #{appId}, #{appSecret}, '0', #{revision}, #{createdBy}, #{createdTime}, #{updatedBy}, #{updatedTime});")
	public int insertAppInfo(MeiteAppInfo meiteAppInfo);

	@Select("SELECT ID AS ID ,app_id as appId, app_name AS appName ,app_secret as appSecret  FROM meite_app_info where app_id=#{appId} and app_secret=#{appSecret}; ")
	public MeiteAppInfo selectByAppInfo(@Param("appId") String appId, @Param("appSecret") String appSecret);

	@Select("SELECT ID AS ID ,app_id as appId, app_name AS appName ,app_secret as appSecret, " +
			"availability as availability, revision as revision, created_by as createdBy, created_time as createdTime, updated_by as updatedBy, updated_time as updatedTime " +
			" FROM meite_app_info where app_id=#{appId}  ")
	public MeiteAppInfo findByAppInfo(@Param("appId") String appId);
}
