package com.mayikt.pay.mayikt.member.service.impl;

import com.mayikt.pay.mayikt.base.BaseApiService;
import com.mayikt.pay.mayikt.base.BaseResponse;
import com.mayikt.pay.mayikt.constants.Constants;
import com.mayikt.pay.mayikt.constants.MemberLoginConstants;
import com.mayikt.pay.mayikt.core.bean.utils.MeiteBeanUtils;
import com.mayikt.pay.mayikt.core.token.GenerateToken;
import com.mayikt.pay.mayikt.core.type.TypeCastHelper;
import com.mayikt.pay.mayikt.core.utils.MD5Util;
import com.mayikt.pay.mayikt.member.input.dto.UserLoginInpDTO;
import com.mayikt.pay.mayikt.member.output.dto.UserOutDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mayikt.pay.mayikt.member.feign.WeiXinAppServiceFeign;
import com.mayikt.pay.mayikt.member.mapper.UserMapper;
import com.mayikt.pay.mayikt.member.mapper.entity.UserDO;
import com.mayikt.pay.mayikt.member.service.MemberService;

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

	@Override
	public BaseResponse<UserOutDTO> ssoLogin(@RequestBody UserLoginInpDTO userLoginInpDTO) {
		// 1.验证参数
		String mobile = userLoginInpDTO.getMobile();
		if (StringUtils.isEmpty(mobile)) {
			return setResultError("手机号码不能为空!");
		}
		String password = userLoginInpDTO.getPassword();
		if (StringUtils.isEmpty(password)) {
			return setResultError("密码不能为空!");
		}
		// 判断登陆类型
		String loginType = userLoginInpDTO.getLoginType();
		if (StringUtils.isEmpty(loginType)) {
			return setResultError("登陆类型不能为空!");
		}
		// 目的是限制范围
		if (!(loginType.equals(MemberLoginConstants.MEMBER_LOGIN_TYPE_ANDROID) ||
                loginType.equals(MemberLoginConstants.MEMBER_LOGIN_TYPE_IOS) ||
                loginType.equals(MemberLoginConstants.MEMBER_LOGIN_TYPE_PC))) {
            return setResultError("登陆类型出现错误!");
        }

		// 设备信息
		String deviceInfor = userLoginInpDTO.getDeviceInfor();
		if (StringUtils.isEmpty(deviceInfor)) {
			return setResultError("设备信息不能为空!");
		}
		// 2.对登陆密码实现加密
		String newPassWord = MD5Util.MD5(password);
		// 3.使用手机号码+密码查询数据库 ，判断用户是否存在
		UserDO userDO = userMapper.login(mobile, newPassWord);
		if (userDO == null) {
			return setResultError("用户名称或者密码错误!");
		}
		return setResultSuccess(MeiteBeanUtils.doToDto(userDO, UserOutDTO.class));
	}

}
