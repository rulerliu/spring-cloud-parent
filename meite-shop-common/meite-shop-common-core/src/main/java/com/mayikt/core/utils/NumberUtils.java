/**
 * 
 */
package com.mayikt.core.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**  
* <p>Title: NumberUtils</p>  
* <p>Description: 数字工具类 </p>  
* @author liuwq  
* @date 2019年4月2日  下午4:40:07
*/
public class NumberUtils {
	
	public static Integer toInteger(Object srcStr, Integer defaultValue) {
		try {
			if (srcStr != null && isInt(srcStr)) {
				String s = srcStr.toString().replaceAll("(\\s)", "");
				return s.length() > 0 ? Integer.valueOf(s) : defaultValue;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return defaultValue;
	}

	public static Double toDouble(Object srcStr, Double defaultValue) {
		try {
			if (srcStr != null) {
				return Double.valueOf(srcStr.toString().replaceAll(",", ""));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return defaultValue;
	}

	public static long toLong(Object srcStr, long defaultValue) {
		try {
			if (srcStr != null) {
				return Long.parseLong(srcStr.toString());
			}
		} catch (Exception e) {
			;
		}
		return defaultValue;
	}

	public static boolean toBoolean(Object srcStr, boolean defaultValue) {
		try {
			if (srcStr != null) {
				return Boolean.parseBoolean(trim(srcStr.toString()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return defaultValue;
	}

	public static float toFloat(Object srcStr, float defaultValue) {
		try {
			if (srcStr != null) {
				return Float.parseFloat(srcStr.toString());
			}
		} catch (Exception e) {
			;
		}
		return defaultValue;
	}
	
	public static String trim(Object srcStr) {
		if (srcStr != null) {
			return srcStr.toString().trim();
		}
		return null;
	}
	
	public static boolean isInt(Object srcStr) {
		if (srcStr == null) {
			return false;
		}
		String s = srcStr.toString().replaceAll("(\\s)", "");
		Pattern p = Pattern.compile("([-]?[\\d]+)");
		Matcher m = p.matcher(s);
		return m.matches();
	}

	public static boolean isDouble(Object srcStr) {
		if (srcStr == null) {
			return false;
		}
		String s = srcStr.toString().replaceAll("(\\s)", "");
		Pattern p = Pattern.compile("([-]?[\\d]+[\\.][\\d]+)");
		Matcher m = p.matcher(s);
		return m.matches();
	}

	public static void main(String[] args) {
		System.out.println(NumberUtils.toDouble("122.22", 13.33));
		System.out.println(NumberUtils.toDouble(null, 13.33));
		System.out.println(NumberUtils.toDouble(null, null));

		System.out.println(NumberUtils.toInteger("20", 12));
		System.out.println(NumberUtils.toInteger(null, 12));
		System.out.println(NumberUtils.toInteger(null, null));

	}
	
}
