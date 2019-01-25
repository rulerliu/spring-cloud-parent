package com.mayikt.member.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.mayikt.base.BaseApiService;
import com.mayikt.base.BaseResponse;
import com.mayikt.constants.MemberLoginConstants;
import com.mayikt.core.token.GenerateToken;
import com.mayikt.core.utils.MD5Util;
import com.mayikt.member.input.dto.UserLoginInpDTO;
import com.mayikt.member.mapper.UserMapper;
import com.mayikt.member.mapper.entity.UserDO;
import com.mayikt.member.service.MemberLoginService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: liuwq
 * @date: 2019/1/25 0025 下午 3:28
 * @version: V1.0
 * @Copyright:该项目“基于SpringCloud2.x构建微服务电商项目”由每特教育|蚂蚁课堂版权所有，未经过允许的情况下， 私自分享视频和源码属于违法行为。
 */
@RestController
public class MemberLoginServiceImpl extends BaseApiService<JSONObject> implements MemberLoginService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private GenerateToken generateToken;

    @Override
    public BaseResponse<JSONObject> login(@RequestBody UserLoginInpDTO userLoginInpDTO) {
        // 1.验证参数
        String mobile = userLoginInpDTO.getMobile();
        if (StringUtils.isBlank(mobile)) {
            return setResultError("手机号码不能为空！");
        }
        String password = userLoginInpDTO.getPassword();
        if (StringUtils.isBlank(password)) {
            return setResultError("密码不能为空！");
        }
        String loginType = userLoginInpDTO.getLoginType();
        if (StringUtils.isEmpty(loginType)) {
            return setResultError("登陆类型不能为空!");
        }
        // 限制登录类型
        if (!(loginType.equals(MemberLoginConstants.MEMBER_LOGIN_TYPE_ANDROID) ||
                loginType.equals(MemberLoginConstants.MEMBER_LOGIN_TYPE_IOS) ||
                loginType.equals(MemberLoginConstants.MEMBER_LOGIN_TYPE_PC))) {
            return setResultError("登陆类型出现错误!");
        }

        // 2.对密码加密
        final String newPassword = MD5Util.MD5(password);

        // 3.使用手机号码 + 密码进行登录
        UserDO userDO = userMapper.login(mobile, newPassword);
        if (userDO == null) {
            return setResultError("用户名或者密码错误！");
        }

        // 4.用户登录一次，生成一个token，存放在redis中，key：token，value：userId
        Long userId = userDO.getUserId();
        String token = generateToken.createToken(MemberLoginConstants.MEMBER_TOKEN_KEYPREFIX, userId + "",
                MemberLoginConstants.MEMBRE_LOGIN_TOKEN_TIME);

        JSONObject data = new JSONObject();
        data.put("token", token);
        return setResultSuccess(data);
    }
}
