package com.mayikt.core.utils;

import java.util.List;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;



/**
 * 
 *  
 * @项目名称:广西红包中心
 * @工程名称:lyhzq-commons
 * @类名称:JacksonUtils
 * @类描述:JSON字符串POJO转换工具
 * @作者:dww
 * @创建时间:2018年4月3日 下午3:14:37
 * @当前版本:1.0
 *
 */
public class JacksonUtils {

    //定义jackson对象
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 
     * @objectToJson(将对象转换成json字符串)
     * @作者:dww
     * @创建时间:2018年4月3日 下午3:15:14
     * @param data
     * @return
     */
    public static String objectToJson(Object data) {
    	try {
			String string = MAPPER.writeValueAsString(data);
			return string;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
    	return null;
    }
    
    /**
     * 
     * @jsonToPojo(将json结果集转化为对象)
     * @作者:dww
     * @创建时间:2018年4月3日 下午3:15:28
     * @param jsonData json数据
     * @param beanType 对象中的object类型
     * @return
     */
    public static <T> T jsonToPojo(String jsonData, Class<T> beanType) {
        try {
            T t = MAPPER.readValue(jsonData, beanType);
            return t;
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return null;
    }
    
    
    /**
     * 
     * @jsonToList(将json数据转换成pojo对象list)
     * @作者:dww
     * @创建时间:2018年4月3日 下午3:20:27
     * @param jsonData
     * @param beanType
     * @return
     */
    public static <T>List<T> jsonToList(String jsonData, Class<T> beanType) {
    	JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, beanType);
    	try {
    		List<T> list = MAPPER.readValue(jsonData, javaType);
    		return list;
		} catch (Exception e) {
			//e.printStackTrace();
		}
    	
    	return null;
    }
    
    /**
     * 
     * @isList(判断JSON是否数组)
     * @作者:dww
     * @创建时间:2018年4月3日 下午3:20:42
     * @param jsonData
     * @param beanType
     * @return
     */
    public static <T>boolean isList(String jsonData, Class<T> beanType){
    	JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, beanType);
    	try {
    		MAPPER.readValue(jsonData, javaType);
    		return true;
		} catch (Exception e) {
			return false;
		}

    }
}
