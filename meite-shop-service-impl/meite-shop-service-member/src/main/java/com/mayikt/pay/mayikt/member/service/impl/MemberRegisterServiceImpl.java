package com.mayikt.pay.mayikt.member.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.mayikt.pay.mayikt.base.BaseApiService;
import com.mayikt.pay.mayikt.base.BaseResponse;
import com.mayikt.pay.mayikt.core.utils.MD5Util;
import com.mayikt.pay.mayikt.member.input.dto.UserInpDTO;
import com.mayikt.pay.mayikt.member.feign.VerificaCodeServiceFeign;
import com.mayikt.pay.mayikt.member.mapper.UserMapper;
import com.mayikt.pay.mayikt.member.mapper.entity.UserDO;
import com.mayikt.pay.mayikt.member.service.MemberRegisterService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: liuwq
 * @date: 2019年  下午10:40:36
 * @version: V1.0
 * @Copyright:该项目“基于SpringCloud2.x构建微服务电商项目”由每特教育|蚂蚁课堂版权所有，未经过允许的情况下，
 *            私自分享视频和源码属于违法行为。
 */
@RestController
public class MemberRegisterServiceImpl extends BaseApiService<JSONObject> implements MemberRegisterService {
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private VerificaCodeServiceFeign verificaCodeServiceFeign;

	@Transactional
	@Override
	public BaseResponse<JSONObject> register(@RequestBody UserInpDTO userInpDTO, String registCode) {
		String userName = userInpDTO.getUserName();
		// 1.参数验证
		if (StringUtils.isBlank(userName)) {
			return setResultError("用户名为空");
		}
		String password = userInpDTO.getPassword();
		if (StringUtils.isBlank(password)) {
			return setResultError("密码为空");
		}
		String mobile = userInpDTO.getMobile();
		if (StringUtils.isBlank(mobile)) {
			return setResultError("手机号码为空");
		}
		
		// 2.验证注册码是否正确
//		BaseResponse<JSONObject> verificaWeixinCode = verificaCodeServiceFeign.verificaWeixinCode(mobile, registCode);
//		if (!Constants.HTTP_RES_CODE_200.equals(verificaWeixinCode.getCode())) {
//			return setResultError(verificaWeixinCode.getMsg());
//		}
		
		// 3.调用数据库插入数据
		UserDO userDO = new UserDO();
		BeanUtils.copyProperties(userInpDTO, userDO);

		// 4.对用户密码MD5加密
		String newPassword = MD5Util.MD5(password);

		int num = userMapper.register(userDO);
		return num > 0 ? setResultSuccess("注册成功") : setResultError("注册失败");
	}

}
