package com.mayikt.core.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;


/**
 * 
 *  
 * @项目名称:广西红包中心
 * @工程名称:lyhzq-commons
 * @类名称:URLEncodingUtil
 * @类描述:URLencodeing处理工具类
 * @作者:dww
 * @创建时间:2018年4月3日 下午3:28:28
 * @当前版本:1.0
 *
 */
public class URLEncodingUtil {
    /**
     * Decodes the passed UTF-8 String using an algorithm that's compatible with
     * JavaScript's <code>decodeURIComponent</code> function. Returns
     * <code>null</code> if the String is <code>null</code>.
     *
     * @param s
     *            The UTF-8 encoded String to be decoded
     * @return the decoded String
     */
    public static String decodeURIComponent(String s) {
        if (s == null) {
            return null;
        }

        String result = null;

        try {
            result = URLDecoder.decode(s, "UTF-8");
        }

        // This exception should never occur.
        catch (UnsupportedEncodingException e) {
            result = s;
        }

        return result;
    }

    /**
     * Encodes the passed String as UTF-8 using an algorithm that's compatible
     * with JavaScript's <code>encodeURIComponent</code> function. Returns
     * <code>null</code> if the String is <code>null</code>.
     * 
     * @param s
     *            The String to be encoded
     * @return the encoded String
     */
    public static String encodeURIComponent(String s) {
        if (s == null) {
            return null;
        }
        String result = null;

        try {
            result = URLEncoder.encode(s, "UTF-8").replaceAll("\\+", "%20").replaceAll("\\%21", "!")
                    .replaceAll("\\%27", "'").replaceAll("\\%28", "(").replaceAll("\\%29", ")")
                    .replaceAll("\\%7E", "~").replaceAll("\\%3D", "=");
        }

        // This exception should never occur.
        catch (UnsupportedEncodingException e) {
            result = s;
        }

        return result;
    }

    /**
     * Private constructor to prevent this class from being instantiated.
     */
    private URLEncodingUtil() {
        super();
    }
}