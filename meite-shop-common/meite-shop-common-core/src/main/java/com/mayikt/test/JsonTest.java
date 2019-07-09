package com.mayikt.test;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: liuwq
 * @date: 2019/7/9 0009 下午 4:35
 * @version: V1.0
 */
public class JsonTest {

    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<>();
		map.put("aaaa", 1111);
		map.put("bbbb", 2222);
		map.put("cccc", 3333);
		System.out.println(map.toString());

		// map转换成JSONObject
		JSONObject jo = (JSONObject) JSONObject.toJSON(map);
		System.out.println(jo.getString("aaaa"));

		// map转换成jsonString
		String jsonString = JSONObject.toJSONString(map);
		System.out.println(jsonString);

		// java bean 转换成jsonString
		User user = new User(1, "liuwq");
		String s = JSONObject.toJSONString(user);
		System.out.println("s:" + s);

		// java bean 转换成JSONObject
		JSONObject o = (JSONObject) JSONObject.toJSON(user);
		System.out.println("o:" + o);

		String str = "{\"name\": \"adsfasfsd\"}";
		// jsonString转换成JSONObject
		JSONObject parse = JSONObject.parseObject(str);
		System.out.println(parse.getString("name"));

		// jsonString转换成javaBean
		User parseObject = JSONObject.parseObject(str, User.class);
		Map<String, Object> map2 = JSONObject.parseObject(str, Map.class);
		System.out.println(parseObject);
		System.out.println(map2);
    }

}
