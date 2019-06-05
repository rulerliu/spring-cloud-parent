package com.mayikt.core.exception;

import java.util.HashMap;
import java.util.Map;


/**
 * 
 *  
 * @项目名称:广西红包中心平台
 * @工程名称:lyhzq-commons
 * @类名称:ServerErrorCode
 * @类描述:错误码常量
 * @作者:dww
 * @创建时间:2017年8月2日 下午3:24:56
 * @当前版本:1.0
 *
 */
public class ServerErrorCode {
	/**
	 * 客户端错误码
	 */
	public static final String OPERATION_SUCCEEDS = "0000";//成功
    public static final String SERVER_INTERNAL_ERROR = "9999";//失败
    public static final String SOME_FIELD_NOT_ALLOWED_TO_BE_EMPTY = "102001";//接口入参必填参数不能为空或格式错误
	public static final String REQUEST_DATA_FORMAT_FAILED = "103001";//请求数据格式检验失败
	
	public static final Map<String, String> ERRORCODE_TO_DESC;

	static {
		ERRORCODE_TO_DESC = new HashMap<String, String>();
		ERRORCODE_TO_DESC.put(OPERATION_SUCCEEDS, "成功");
		ERRORCODE_TO_DESC.put(REQUEST_DATA_FORMAT_FAILED, "请求数据格式检验失败");
		ERRORCODE_TO_DESC.put(SOME_FIELD_NOT_ALLOWED_TO_BE_EMPTY, "接口入参必填参数不能为空或格式错误");
		ERRORCODE_TO_DESC.put(SERVER_INTERNAL_ERROR, "失败");
	}

	public static String getErrorDesc(String resultCode) {
		return ERRORCODE_TO_DESC.get(resultCode);
	}
}
