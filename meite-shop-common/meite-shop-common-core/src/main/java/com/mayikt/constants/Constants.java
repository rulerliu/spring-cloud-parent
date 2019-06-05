package com.mayikt.constants;

public interface Constants {

	/** 成功返回码 */
	String  RESPCODE_SUCCESS                    = "0000";
	/** 失败返回码 */
	String  RESPCODE_ERROR                      = "0001";

	/** 操作成功 */
	String  RESPCODE_SUCCESS_NAME               = "操作成功";
	/** 操作失败 */
	String  RESPCODE_ERROR_NAME                 = "操作失败";
	/** 未知错误 */
	String  RESPCODE_UNKNOWN_NAME               = "未知错误";

	/** 生失效标识：有效 */
	Integer IS_VALID                            = 1;
	/** 无效 **/
	Integer NOT_IS_VALID                            = 0;

	// 响应请求成功
	String HTTP_RES_CODE_200_VALUE = "success";
	// 系统错误
	String HTTP_RES_CODE_500_VALUE = "fial";
	// 响应请求成功code
	Integer HTTP_RES_CODE_200 = 200;
	// 系统错误
	Integer HTTP_RES_CODE_500 = 500;
	// 未关联QQ账号
	Integer HTTP_RES_CODE_201 = 201;
	// 发送邮件
	String MSG_EMAIL = "email";
	// 会员token
	String TOKEN_MEMBER = "TOKEN_MEMBER";
	// 用户有效期 90天
	Long TOKEN_MEMBER_TIME = (long) (60 * 60 * 24 * 90);
	int COOKIE_TOKEN_MEMBER_TIME = (60 * 60 * 24 * 90);
	// cookie 会员 totoken 名称
	String COOKIE_MEMBER_TOKEN = "cookie_member_token";
	// 微信code
	String WEIXINCODE_KEY = "weixin.code";
	// 微信注册码有效期30分钟
	Long WEIXINCODE_TIMEOUT = 1800l;
	
	// 用户手机号码未注册过
	Integer HTTP_RES_CODE_EXISTMOBILE_203 = 203;
	
	// 用户信息不存在
	Integer HTTP_RES_CODE_NOTUSER_203 = 203;

}
