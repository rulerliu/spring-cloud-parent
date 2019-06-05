package com.mayikt.core.utils;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 *  
 * @项目名称:广西红包中心
 * @工程名称:lyhzq-commons
 * @类名称:CookieUtils
 * @类描述:cookie通用处理类
 * @作者:dww
 * @创建时间:2018年4月3日 下午2:38:45
 * @当前版本:1.0
 *
 */
public class CookieUtils {

    /**
     * 
     * @setCookie(设置cookie值)
     * @作者:dww
     * @创建时间:2017年9月26日 下午5:00:17
     * @param response
     * @param map 需要加入cookie的参数
     * @return
     */
    public static Boolean setCookie(HttpServletResponse response, Map<String, String> map){
        if(map==null || map.isEmpty()){
            return false;  
        }
        for (Map.Entry<String,String> entry : map.entrySet()) {  
            Cookie ssidCookie = new Cookie(entry.getKey(),entry.getValue());
            ssidCookie.setMaxAge(Integer.MAX_VALUE);
            ssidCookie.setPath("/");// 设置所有路径都可以使用
            response.addCookie(ssidCookie);
        }  
        return true;
    }
    
    /**
     * 
     * @getCookie(获取cookie值)
     * @作者:dww
     * @创建时间:2017年9月26日 下午5:00:17
     * @param request
     * @return
     */
    public static Map<String,String> getCookie(HttpServletRequest request){
        Map<String,String> map=new HashMap<String, String>();
        Cookie[] cookies = request.getCookies(); 
        if(cookies==null || cookies.length<=0){
            return map;  
        }
        for (Cookie cookie : cookies) {  
            map.put(cookie.getName(),cookie.getValue());
        }  
        return map;
    }
    
    /**
     * 
     * @delCookie(设置cookie值)
     * @作者:dww
     * @创建时间:2017年9月26日 下午5:00:17
     * @param response
     * @param map 需要加入cookie的参数
     * @return
     */
    public static Boolean delCookie(HttpServletResponse response, Map<String, String> map){
        if(map==null || map.isEmpty()){
            return false;  
        }
        for (Map.Entry<String,String> entry : map.entrySet()) {  
            Cookie ssidCookie = new Cookie(entry.getKey(),entry.getValue());
            ssidCookie.setMaxAge(0); //删除Cookie,设置maxage=0即可删除  
            ssidCookie.setPath("/");// 设置所有路径都可以使用
            response.addCookie(ssidCookie);
        }  
        return true;
    }
}
