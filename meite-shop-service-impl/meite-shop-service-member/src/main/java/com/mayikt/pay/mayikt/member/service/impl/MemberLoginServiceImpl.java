package com.mayikt.pay.mayikt.member.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.mayikt.pay.mayikt.base.BaseApiService;
import com.mayikt.pay.mayikt.base.BaseResponse;
import com.mayikt.pay.mayikt.constants.MemberLoginConstants;
import com.mayikt.pay.mayikt.core.token.GenerateToken;
import com.mayikt.pay.mayikt.core.transactional.RedisDataSoureceTransaction;
import com.mayikt.pay.mayikt.core.utils.MD5Util;
import com.mayikt.pay.mayikt.member.input.dto.UserLoginInpDTO;
import com.mayikt.pay.mayikt.member.mapper.UserMapper;
import com.mayikt.pay.mayikt.member.mapper.UserTokenMapper;
import com.mayikt.pay.mayikt.member.mapper.entity.UserDO;
import com.mayikt.pay.mayikt.member.mapper.entity.UserTokenDO;
import com.mayikt.pay.mayikt.member.service.MemberLoginService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
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

    @Autowired
    private UserTokenMapper userTokenMapper;

    @Autowired
    private RedisDataSoureceTransaction redisDataSoureceTransaction;

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

        TransactionStatus transactionStatus = null;
        try {
            // 唯一登录
            // 判断当前手机号之前是否登录过，如果登录过，移除redis中的token，修改之前的状态为不可用，并且生成一个新的可用token
            Long userId = userDO.getUserId();
            UserTokenDO userTokenDO = userTokenMapper.selectByUserIdAndLoginType(userId, loginType);
            transactionStatus = redisDataSoureceTransaction.begin();
            if (userTokenDO != null) {
                String token = userTokenDO.getToken();
                Boolean removeToken = generateToken.removeToken(token);
                int updateTokenAvailability = userTokenMapper.updateTokenAvailability(token);
                if (!toDaoResult(updateTokenAvailability)) {
                    redisDataSoureceTransaction.rollback(transactionStatus);
                    return setResultError("系统错误");
                }
            }
            
            String qqOpenId = userLoginInpDTO.getQqOpenId();
            if (!StringUtils.isBlank(qqOpenId)) {
            	userMapper.updateUserOpenId(qqOpenId, userId);
            }

            // 4.用户登录生成token，存放在redis中，key：token，value：userId
            String kePrefix = MemberLoginConstants.MEMBER_TOKEN_KEYPREFIX + loginType;
            String token = generateToken.createToken(kePrefix, userId + "", MemberLoginConstants.MEMBRE_LOGIN_TOKEN_TIME);

            UserTokenDO newUserTokenDO = new UserTokenDO();
            newUserTokenDO.setUserId(userId);
            newUserTokenDO.setLoginType(loginType);
            newUserTokenDO.setToken(token);
            newUserTokenDO.setDeviceInfor(userLoginInpDTO.getDeviceInfor());
            int insertUserToken = userTokenMapper.insertUserToken(newUserTokenDO);
            if (!toDaoResult(insertUserToken)) {
                // 会抛出异常
                redisDataSoureceTransaction.rollback(transactionStatus);
//                return setResultError("系统错误");
            }
            redisDataSoureceTransaction.commit(transactionStatus);

            JSONObject data = new JSONObject();
            data.put("token", token);
            return setResultSuccess(data);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                redisDataSoureceTransaction.rollback(transactionStatus);
            } catch (Exception e2) {

            }
        }
        return setResultError("系统错误");
    }
}
