package com.mayikt.member.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.mayikt.base.BaseApiService;
import com.mayikt.base.BaseResponse;
import com.mayikt.constants.Constants;
import com.mayikt.constants.MemberLoginConstants;
import com.mayikt.core.token.GenerateToken;
import com.mayikt.member.mapper.UserMapper;
import com.mayikt.member.mapper.entity.UserDO;
import com.mayikt.member.service.QQAuthoriService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: liuwq
 * @date: 2019年  下午10:24:44
 * @version: V1.0
 * @Copyright:该项目“基于SpringCloud2.x构建微服务电商项目”由每特教育|蚂蚁课堂版权所有，未经过允许的情况下，
 *            私自分享视频和源码属于违法行为。
 */
@RestController
public class QQAuthoriServiceImpl extends BaseApiService<JSONObject> implements QQAuthoriService {
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private GenerateToken generateToken;

	@Override
	public BaseResponse<JSONObject> findByOpenId(String qqOpenId) {
		if (StringUtils.isBlank(qqOpenId)) {
			return setResultError("qqOpenId不能为空!");
		}
		// 1. 根据qqOpenId查询user表
		UserDO userDO = userMapper.findByOpenId(qqOpenId);
		
		// 2. 如果没有查询到，返回203状态码
		if (userDO == null) {
			return setResultError(Constants.HTTP_RES_CODE_NOTUSER_203, "根据qqOpenId没查询到用户信息!");
		}
		
		// 3. 如果能查询到，返回对应用户信息token
		String keyPrefix = MemberLoginConstants.MEMBER_TOKEN_KEYPREFIX + "QQ_LOGIN";
		Long userId = userDO.getUserId();
		String token = generateToken.createToken(keyPrefix, userId + "", MemberLoginConstants.MEMBRE_LOGIN_TOKEN_TIME);
		JSONObject data = new JSONObject();
		data.put("token", token);
		
		return setResultSuccess(data);
	}

}
