package com.mayikt.core.utils;

import com.alibaba.fastjson.JSONObject;
import com.mayikt.core.exception.ResultObj;
import com.mayikt.core.exception.ServerErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class MapUtil {
    
    private static Logger logger = LoggerFactory.getLogger(MapUtil.class);

    public static Long getLong(Map<String, String> map, String key, boolean canNull){
        String val = null;
        try {
            val = map.get(key);
            val = val.split("[.]")[0];
            return Long.parseLong(val);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return null;
    }

    public static String getString(Map<String, String> map, String key, boolean canNull){
        String val = null;
        val = map.get(key);
        if (val == null && !canNull) {
            return val;
        }
        return val;

    }
    
    /**
     * 
     * @transBean2Map(对象转MAP)
     * @作者:dww
     * @创建时间:2017年8月7日 上午10:00:29
     * @param obj
     * @return
     */
    public static Map<String, Object> transBean2Map(Object obj) {
        if (obj == null) {
            return null;
        }
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                // 过滤class属性
                if (!key.equals("class")) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);
                    map.put(key, value);
                }
            }
        } catch (Exception e) {
            logger.info("transBean2Map Error:"+e.getMessage());
            e.printStackTrace();
        }
        return map;

    }
    /**
     * 
     * @transBean2Map(对象转MAP)
     * @作者:dww
     * @创建时间:2017年8月7日 上午10:00:29
     * @param obj
     * @return
     */
    public static Map<String, String> transBean2MapStr(Object obj) {
        if (obj == null) {
            return null;
        }
        Map<String,String> map = new HashMap<String,String>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                // 过滤class属性
                if (!key.equals("class")) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    String value = (String) getter.invoke(obj);
                    map.put(key, value);
                }
            }
        } catch (Exception e) {
            logger.info("transBean2Map Error:"+e.getMessage());
            e.printStackTrace();
        }
        return map;
        
    }
    
    /**
     * 
     * @transBeanTOJsonObject(对象转JsonObject)
     * @作者:dww
     * @创建时间:2017年8月7日 上午10:00:29
     * @param obj
     * @return
     */
    public static  JSONObject transBeanTOJsonObject(Object obj) {
        if (obj == null) {
            return null;
        }
        JSONObject jsonMsg = new JSONObject();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                // 过滤class属性
                if (!key.equals("class")) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);
                    jsonMsg.put(key, value);
                }
            }
        } catch (Exception e) {
            logger.info("transBean2Map Error:"+e.getMessage());
            e.printStackTrace();
        }
        return jsonMsg;
        
    }
    
    public static void main(String[] args) {
        /*Map<String,Object> maps=transBean2Map(new ResultObj(ServerErrorCode.USER_IS_EXIST));
        System.out.println("--- transBean2Map Map Info: ");  
        for (Map.Entry<String, Object> entry : maps.entrySet()) {  
            System.out.println(entry.getKey() + ": " + entry.getValue());  
        }  */
        JSONObject  jSONObject=transBeanTOJsonObject(new ResultObj(ServerErrorCode.OPERATION_SUCCEEDS));
        System.out.println(jSONObject.get("resultCode"));
    }
}
