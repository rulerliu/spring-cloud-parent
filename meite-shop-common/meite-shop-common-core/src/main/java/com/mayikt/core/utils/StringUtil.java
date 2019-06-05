package com.mayikt.core.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** <br>
 * 标题: 字符串工具类<br>
 * 描述: <br>
 * 公司: www.tydic.com<br>
 * 
 * @author davis
 * @time 2017年10月27日 上午10:17:17 */
public class StringUtil {

	

	/**
	 * 
	 * 方法功能描述: 根据号段串的长度，将号段头串补全
	 * @param numSection
	 * @param length
	 * @param replaceChar
	 * @return 
	 * @代码检查人： <br>@检查时间
	 */
	public static String toNumSectionAll(String numSection,int length,String replaceChar) {
		String numSectionStart = null;
		if(numSection != null){
			if(numSection.length()<length){
				int lost = length - numSection.length();
				numSectionStart = numSection;
				for(int i=0; i< lost; i++){
					numSectionStart = numSectionStart +replaceChar;
				}
			}else{
				numSectionStart = numSection.substring(0, length-1);
			}
		}
		return numSectionStart;
	}

	public static Integer toInteger(Object srcStr, Integer defaultValue) {
		try {
			if (srcStr != null && isInt(srcStr)) {
				String s = srcStr.toString().replaceAll("(\\s)", "");
				return s.length() > 0 ? Integer.valueOf(s) : defaultValue;
			}
		} catch (Exception e) {
			/*
			 * if (null == defaultValue) { defaultValue = ZERO; } return defaultValue;
			 */
			;
		}
		return defaultValue;
	}

	public static double toDouble(Object srcStr, double defaultValue) {
		try {
			if (srcStr != null) {
				return Double.valueOf(srcStr.toString().replaceAll(",", ""));
			}
		} catch (Exception e) {
			;
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
			;
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
		String str1="13625834";
		System.out.println(StringUtil.toNumSectionAll(str1, 11, "0"));
		
		System.out.println(StringUtil.toDouble(null, 13.33));
		System.out.println(StringUtil.toDouble("122.22", 13.33));
	}

}
