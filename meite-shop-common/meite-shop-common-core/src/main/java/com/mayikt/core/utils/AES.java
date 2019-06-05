package com.mayikt.core.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/*******************************************************************************
 * AES加解密算法
 * 
 * 
 */

public class AES {
	
	public static String CKEY = "123456789ABCDEFG";
	public static String CHARSET = "utf-8";
	
	public static String encrypt(String sSrc) throws Exception{
		if(StringUtils.isBlank(sSrc)){
			return null;
		}
		return encrypt(sSrc, CKEY, CHARSET);
	}
	
	public static String decrypt(String sSrc) throws Exception {
		if(StringUtils.isBlank(sSrc)){
			return null;
		}
		return decrypt(sSrc,CKEY, CHARSET);
	}
	// 加密
	public static String encrypt(String sSrc, String sKey, String charset) throws Exception {
		if (sKey == null) {
			System.out.print("Key为空");
			return null;
		}
		// 判断Key是否为16位
		if (sKey.length() != 16) {
			System.out.print("Key长度不是16位");
			return null;
		}
		byte[] raw = sKey.getBytes("ASCII");
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");//"算法/模式/补码方式"
		IvParameterSpec iv = new IvParameterSpec("0102030405060709".getBytes());//使用CBC模式，需要一个向量iv，可增加加密算法的强度
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
		byte[] encrypted = cipher.doFinal(sSrc.getBytes(charset));
		return Base64.encodeBase64URLSafeString(encrypted);//此处使用BASE64做转码功能，同时能起到2次加密的作用
	}

	private static final String ADD = "/add/";

	/**
	 * 业务报表参数加密
	 * @param str 加密参数
	 * @param key 秘钥
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String str, String key) throws Exception {
		if (str == null || key == null)
			return null;
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE,
				new SecretKeySpec(key.getBytes("utf-8"), "AES"));
		byte[] bytes = cipher.doFinal(str.getBytes("utf-8"));
		String result = new BASE64Encoder().encode(bytes);
		result = result.toString().replaceAll("\r\n", "");
		result = result.replaceAll("\\+", ADD);//+加号替换成/and/，避免url传递后丢失或者转变成空格。
		return result;
	}

	/**
	 * 业务报表参数解密
	 * @param str 解密参数
	 * @param key 秘钥
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(String str, String key) throws Exception {
		if (str == null || key == null) return null;
		str = str.replaceAll(ADD,"+");//把/and/还原成+加号
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE,
				new SecretKeySpec(key.getBytes("utf-8"), "AES"));
		byte[] bytes = new BASE64Decoder().decodeBuffer(str);
		bytes = cipher.doFinal(bytes);
		return new String(bytes, "utf-8");
	}
	
	// 解密
	public static String decrypt(String sSrc, String sKey, String charset) throws Exception {
		try {
			// 判断Key是否正确
			if (sKey == null) {
				System.out.print("Key为空");
				return null;
			}
			// 判断Key是否为16位
			if (sKey.length() != 16) {
				System.out.print("Key长度不是16位");
				return null;
			}
			byte[] raw = sKey.getBytes("ASCII");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			IvParameterSpec iv = new IvParameterSpec("0102030405060709"	.getBytes());
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			byte[] encrypted = Base64.decodeBase64(sSrc);//先用base64解密
			try {
				byte[] original = cipher.doFinal(encrypted);
				String originalString = new String(original, charset);
				return originalString;
			} catch (Exception e) {
				System.out.println(e.toString());
				return null;
			}
		} catch (Exception ex) {
			System.out.println(ex.toString());
			return null;
		}
	}

	public static void main(String[] args) throws Exception {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("SourceSystem", "10000027");
		jsonObject.put("Mobile", "18529103439");
		jsonObject.put("Rpid", "D69DACF4256C1B328504D753080C3C6F");
		jsonObject.put("Token", "76f052b9a0dd4e8772d075c11709e9ce");
		String encrypt = encrypt(jsonObject.toJSONString());
		System.out.println("原始数据：" + jsonObject.toJSONString());
		System.out.println("加密后：" + encrypt);
		System.out.println("加密后长度：" + encrypt.length());
		System.out.println("解密后：" + decrypt(encrypt));
	}

}