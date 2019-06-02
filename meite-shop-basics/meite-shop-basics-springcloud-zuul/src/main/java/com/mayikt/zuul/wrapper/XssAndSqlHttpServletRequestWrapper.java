package com.mayikt.zuul.wrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 防止xss攻击
 * 
 * 
 * @description:
 * @author: 97后互联网架构师-余胜军
 * @contact: QQ644064779、微信yushengjun644 www.mayikt.com
 * @date: 2019年1月3日 下午3:03:17
 * @version V1.0
 * @Copyright 该项目“基于SpringCloud2.x构建微服务电商项目”由每特教育|蚂蚁课堂版权所有，未经过允许的情况下，
 *            私自分享视频和源码属于违法行为。
 */
public class XssAndSqlHttpServletRequestWrapper extends HttpServletRequestWrapper {

	public XssAndSqlHttpServletRequestWrapper(HttpServletRequest request) {
		super(request);

	}

	@Override
	public String getParameter(String name) {
		String value = super.getParameter(name);
		if (!StringUtils.isEmpty(value)) {
			value = StringEscapeUtils.escapeJava(value);
		}
		return value;
	}
}