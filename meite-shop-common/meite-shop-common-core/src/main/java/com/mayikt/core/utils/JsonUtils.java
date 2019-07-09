package com.mayikt.core.utils;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mayikt.test.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonUtils {

	public static ObjectMapper  objectMapper = new ObjectMapper();

	private static final Logger log          = LoggerFactory.getLogger(JsonUtils.class);

	private static Pattern      humpPattern  = Pattern.compile("[A-Z]");

	/** 使用泛型方法，把JavaBean对象转成Object对象。 转换为普通JavaBean：readValue为Test.class
	 * 
	 * @param object
	 *        实体类
	 * @param valueType
	 *        类型
	 * @return */
	public static <T> T javaBeanToObject(Object object, Class<T> valueType) {

		if (valueType == null) {
			return null;
		}

		if (object == null) {
			return null;
		}

		try {
			String content = beanToJson(object);
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); 
			objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);
//			objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);
//			objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
//			objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
			return objectMapper.readValue(content, valueType);
		}
		catch (Exception e) {
			log.error("转换类型错误，请检查转换类型！", e);
			throw new RuntimeException("请检查转换类型", e);
		}

	}

	/** 使用泛型方法，把JavaBean对象转成Object，同时将key转成大写并且驼峰格式转下划线格式，支付中心转换用到。 转换为普通JavaBean：readValue为Test.class
	 * 
	 * @param object
	 *        实体类
	 * @param valueType
	 *        类型
	 * @return */
	public static <T> T javaBeanToObjectAndKeyChange(Object object, Class<T> valueType) {

		if (valueType == null) {
			return null;
		}

		if (object == null) {
			return null;
		}

		try {
			String content = beanToJson(object);
			Map<String, Object> map = objectMapper.readValue(content, Map.class);
			map = map2Uppercase(map);
			content = JSONObject.toJSONString(map);
			return objectMapper.readValue(content, valueType);
		}
		catch (Exception e) {
			log.error("转换类型错误，请检查转换类型！", e);
			throw new RuntimeException("请检查转换类型", e);
		}

	}

	/** 使用泛型方法，把json字符串转换为相应的JavaBean对象。 转换为普通JavaBean：readValue为Test.class
	 * 
	 * @param content
	 *        实体类
	 * @param valueType
	 *        类型
	 * @return */
	public static <T> T jsonStringToObject(String content, Class<T> valueType) {

		if (valueType == null) {
			return null;
		}

		try {
			// return objectMapper.convertValue(content, valueType);
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); 
			//objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
			objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);
			objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
			objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
			return objectMapper.readValue(content, valueType);
		}
		catch (Exception e) {
			log.error("转换类型错误，请检查转换类型！", e);
			throw new RuntimeException("请检查转换类型", e);
		}

	}

	/** 把JavaBean转换为需要的json字符串
	 * 
	 * @param object
	 * @return */
	public static String beanToJson(Object object) {
		String content = null;
		try {

			if (object == null) {
				return content;
			}
			else {
				content = objectMapper.writeValueAsString(object);
			}

		}
		catch (JsonProcessingException e) {
			log.error("将对象生成json异常，请检查该对象！", e);
		}

		return content;
	}

	/** 将map中的key转下划线和大写
	 * @param map
	 */
	@SuppressWarnings("rawtypes")
	public static Map map2Uppercase(Map<String, Object> map) {
		Map m = new LinkedHashMap();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			String key = entry.getKey();
			Object obj = entry.getValue();

			if (obj instanceof List) {
				List list = new ArrayList();
				for (int i = 0; i < ((List) obj).size(); i++) {
					list.add(map2Uppercase((Map) ((List) obj).get(i)));
				}
				m.put(underscoreName(key), list);
			}
			else {
				m.put(underscoreName(key), obj);
			}
		}
		return m;
	}

	/** 转换为下划线(大写)
	 * 
	 * @param camelCaseName
	 * @return */
	public static String underscoreName(String camelCaseName) {
		StringBuilder result = new StringBuilder();
		if (camelCaseName != null && camelCaseName.length() > 0) {
			result.append(camelCaseName.substring(0, 1).toUpperCase());
			for (int i = 1; i < camelCaseName.length(); i++) {
				char ch = camelCaseName.charAt(i);
				if (Character.isUpperCase(ch)) {
					result.append("_");
					result.append(ch);
				} else {
					result.append(Character.toUpperCase(ch));
				}
			}
		}
		return result.toString();
	}

	/** 将map转成json对象
	 * 
	 * @param map */
	public static JSONObject changeMapToJson(Map map) {
		JSONObject jsonObject = null;
		try {
			if (map == null) {
				return jsonObject;
			}
			else {
				String content = JSONObject.toJSONString(map);
				jsonObject = JSONObject.parseObject(content);
			}
		}
		catch (RuntimeException e) {
			log.error("map转json异常，请检查改map", e);
		}
		return jsonObject;
	}

	public static void main(String[] args) {
		User user = new User();
		user.setId(123);
		user.setName("123456");

		String beanToJson = beanToJson(user);
		System.out.println("beanToJson:" + beanToJson);

		User jsonStringToObject = jsonStringToObject(beanToJson, User.class);
		System.out.println("jsonStringToObject:" + jsonStringToObject);

		JSONObject javaBeanToObjectAndKeyChange = javaBeanToObjectAndKeyChange(user, JSONObject.class);
		 System.out.println("javaBeanToObjectAndKeyChange:" + javaBeanToObjectAndKeyChange.toString());
//		jsonStringToObject("{\"aa\":\"123\"}", JSONObject.class);
		// System.out.println(a);
		/*
		 * Map<String,Object> map = new HashMap<String,Object>(); map.put("nameId", "p"); map.put("ageOk", 1); // map = map2Uppercase(map);
		 * System.out.println(map2Uppercase(map));;
		 */

	}

	/** 将驼峰格式转换成下划线格式
	 * 
	 * @param str */
	public static String humpToLine(String str) {
		Matcher matcher = humpPattern.matcher(str);
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

	/** <br>
	 * 适用场景: 将对象转换成json, 包含所有属性, 包括关联对象<br>
	 * 调用方式: <br>
	 * 业务逻辑说明<br>
	 * 
	 * @param src
	 * @return
	 * @autho QIJIANFEI
	 * @time 2017年6月6日 下午9:27:50 */
	public static <T> String toJson(T src) {
		try {
			return src instanceof String ? (String) src : objectMapper.writeValueAsString(src);
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}