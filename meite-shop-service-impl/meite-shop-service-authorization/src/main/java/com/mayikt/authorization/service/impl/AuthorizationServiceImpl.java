package com.mayikt.authorization.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.mayikt.authorization.api.service.AuthorizationService;
import com.mayikt.authorization.mapper.AppInfoMapper;
import com.mayikt.authorization.mapper.entity.MeiteAppInfo;
import com.mayikt.base.BaseApiService;
import com.mayikt.base.BaseResponse;
import com.mayikt.core.token.GenerateToken;
import com.mayikt.core.utils.Guid;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @description:
 * @author: liuwq
 * @date: 2019/6/1 0001 19:25
 * @version: V1.0
 * @Copyright:该项目“基于SpringCloud2.x构建微服务电商项目”由每特教育|蚂蚁课堂版权所有，未经过允许的情况下， 私自分享视频和源码属于违法行为。
 */
@RestController
public class AuthorizationServiceImpl extends BaseApiService<JSONObject> implements AuthorizationService {

    @Autowired
    private AppInfoMapper appInfoMapper;

    @Autowired
    private GenerateToken generateToken;

    /**
     * http://127.0.0.1:9500/applyAppInfo?appName=liuwq2
     * @param appName
     * @return
     */
    @Override
    public BaseResponse<JSONObject> applyAppInfo(String appName) {
        // 1.验证参数
        if (StringUtils.isBlank(appName)) {
            return setResultError("机构名称不能为空!");
        }

        // 2.生成appid和appScrec
        Guid guid = new Guid();
        String appId = guid.getAppId();
        String appScrect = guid.getAppScrect();

        // 3.添加数据库中
        MeiteAppInfo meiteAppInfo = new MeiteAppInfo(appName, appId, appScrect);
        meiteAppInfo.setCreatedTime(new Date());
        meiteAppInfo.setUpdatedTime(new Date());
        int insertAppInfo = appInfoMapper.insertAppInfo(meiteAppInfo);
        if (!toDaoResult(insertAppInfo)) {
            return setResultError("申请失败!");
        }

        // 4.返回给客户端
        JSONObject data = new JSONObject();
        data.put("appId", appId);
        data.put("appScrect", appScrect);
        return setResultSuccess(data);
    }

    /**
     * http://127.0.0.1:9500/getAccessToken?appId=fd98f181-573e-4121-ac43-4ce4651a5dfa&appSecret=44A01DB46D23A4083E4B2D2F5667D635
     * @param appId
     * @param appSecret
     * @return
     */
    @Override
    public BaseResponse<JSONObject> getAccessToken(String appId, String appSecret) {
        // 使用appid+appSecret获取AccessToken
        // 1.参数验证
        if (StringUtils.isEmpty(appId)) {
            return setResultError("appId不能为空!");
        }
        if (StringUtils.isEmpty(appSecret)) {
            return setResultError("appSecret不能为空!");
        }

        // 2.使用appId+appSecret查询数据库
        MeiteAppInfo meiteAppInfo = appInfoMapper.selectByAppInfo(appId, appSecret);
        if (meiteAppInfo == null) {
            return setResultError("appId或者是appSecret错误");
        }

        // 3.获取应用机构信息 生成accessToken
        String dbAppId = meiteAppInfo.getAppId();
        String accessToken = generateToken.createToken("auth", dbAppId);
        JSONObject data = new JSONObject();
        data.put("accessToken", accessToken);
        return setResultSuccess(data);
    }

    /**
     * http://127.0.0.1:9500/getAppInfo?accessToken=b698cd5149594a3ea854b93279e1c294
     * @param accessToken
     * @return
     */
    @Override
    public BaseResponse<JSONObject> getAppInfo(String accessToken) {
        // 1.验证参数
        if (StringUtils.isEmpty(accessToken)) {
            return setResultError("AccessToken cannot be empty ");
        }

        // 2.从redis中获取accessToken
        String appId = generateToken.getToken("auth" + accessToken);
        if (StringUtils.isEmpty(appId)) {
            return setResultError("accessToken  invalid");
        }

        // 3.使用appid查询数据库
        MeiteAppInfo meiteAppInfo = appInfoMapper.findByAppInfo(appId);
        if (meiteAppInfo == null) {
            return setResultError("AccessToken  invalid");
        }

        // 4.返回应用机构信息
        JSONObject data = new JSONObject();
        data.put("appInfo", meiteAppInfo);
        return setResultSuccess(data);
    }
}
