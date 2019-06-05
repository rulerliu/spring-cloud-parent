package com.mayikt.core.exception;

/**
 * 
 * 
 * @项目名称:广西红包中心平台
 * @工程名称:lyhzq-commons
 * @类名称:ResultObj
 * @类描述:统一错误码对象
 * @作者:dww
 * @创建时间:2017年8月2日 下午3:24:37
 * @当前版本:1.0
 *
 */
public class ResultObj implements java.io.Serializable {

    private static final long serialVersionUID = -5553392454697677452L;
    
    /**
     * 请求返回编码
     */
    private String respCode;
    /**
     * 请求返回描述
     */
    private String respDesc;
    
    public ResultObj() {
    }
    
    public ResultObj(String respCode) {
        this.respCode = respCode;
        this.respDesc = ServerErrorCode.getErrorDesc(respCode);
    }
    
    public ResultObj(String respCode, String respDesc) {
        this.respCode = respCode;
        this.respDesc = respDesc;
    }

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getRespDesc() {
        return respDesc;
    }

    public void setRespDesc(String respDesc) {
        this.respDesc = respDesc;
    }

   
}