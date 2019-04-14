package com.mayikt.pay.mayikt.constants;

/**
 * @description: 用户登录常量
 * @author: liuwq
 * @date: 2019/1/25 0025 下午 4:01
 * @version: V1.0
 * @Copyright:该项目“基于SpringCloud2.x构建微服务电商项目”由每特教育|蚂蚁课堂版权所有，未经过允许的情况下， 私自分享视频和源码属于违法行为。
 */
public interface MemberLoginConstants {
    // token
    String MEMBER_TOKEN_KEYPREFIX = "mayikt.member.login";

    // 安卓的登陆类型
    String MEMBER_LOGIN_TYPE_ANDROID = "Android";
    // IOS的登陆类型
    String MEMBER_LOGIN_TYPE_IOS = "IOS";

    // PC的登陆类型
    String MEMBER_LOGIN_TYPE_PC = "PC";

    // 登陆超时时间 有效期 90天
    Long MEMBRE_LOGIN_TOKEN_TIME = 77776000L;

}
