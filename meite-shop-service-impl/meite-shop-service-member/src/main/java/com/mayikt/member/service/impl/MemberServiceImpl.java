package com.mayikt.member.service.impl;

import com.mayikt.base.BaseApiService;
import com.mayikt.base.BaseResponse;
import com.mayikt.bean.utils.MeiteBeanUtils;
import com.mayikt.constants.Constants;
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

}
