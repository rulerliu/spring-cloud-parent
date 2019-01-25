package com.mayikt.member.service.impl;

import com.mayikt.base.BaseApiService;
import com.mayikt.base.BaseResponse;
import com.mayikt.constants.Constants;
import com.mayikt.core.bean.utils.MeiteBeanUtils;
import com.mayikt.core.token.GenerateToken;
import com.mayikt.core.type.TypeCastHelper;
import com.mayikt.member.feign.WeiXinAppServiceFeign;
import com.mayikt.member.mapper.UserMapper;
import com.mayikt.member.mapper.entity.UserDO;
import com.mayikt.member.output.dto.UserOutDTO;
import com.mayikt.member.service.MemberService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: liuwq
 * @date: 2019年 下午11:40:13
 * @version: V1.0
 * @Copyright:该项目“基于SpringCloud2.x构建微服务电商项目”由每特教育|蚂蚁课堂版权所有，未经过允许的情况下， 私自分享视频和源码属于违法行为。
 */
@RestController
public class MemberServiceImpl extends BaseApiService<UserOutDTO> implements MemberService {

	@Autowired
	private WeiXinAppServiceFeign weiXinAppServiceFeign;
	
	@Autowired
	private UserMapper userMapper;

	@Autowired
	private GenerateToken generateToken;

	@Override
	public BaseResponse<UserOutDTO> existMobile(String mobile) {
		if (StringUtils.isBlank(mobile)) {
			return setResultError("手机号码不能为空");
		}

		UserDO userDO = userMapper.existMobile(mobile);
		if (userDO == null) {
			return setResultError(Constants.HTTP_RES_CODE_EXISTMOBILE_203, "用户信息不存在");
		}
		UserOutDTO userOutDTO = MeiteBeanUtils.doToDto(userDO, UserOutDTO.class);
		return setResultSuccess(userOutDTO);
	}

	@Override
	public BaseResponse<UserOutDTO> getUserInfo(String token) {
		// 1.验证参数
		if (StringUtils.isBlank(token)) {
			return setResultError("token不能为空");
		}
		// 2.根据token获取redis中的userId
		String redisUserId = generateToken.getToken(token);
		if (StringUtils.isBlank(redisUserId)) {
			return setResultError("token无效或者已过期");
		}
		
		long userId = TypeCastHelper.toLong(redisUserId);
		UserDO userDO = userMapper.findByUserId(userId);
		if (userDO == null) {
			return setResultError("用户id:" + userId + "不存在");
		}
		return setResultSuccess(MeiteBeanUtils.doToDto(userDO, UserOutDTO.class));
	}

}
