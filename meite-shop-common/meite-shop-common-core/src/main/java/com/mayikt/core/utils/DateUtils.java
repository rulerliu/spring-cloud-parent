package com.mayikt.core.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * @description:
 * @author: liuwq
 * @date: 2019/5/15 0015 下午 4:53
 * @version: V1.0
 * @Copyright:该项目“基于SpringCloud2.x构建微服务电商项目”由每特教育|蚂蚁课堂版权所有，未经过允许的情况下， 私自分享视频和源码属于违法行为。
 */
public class DateUtils {

    private static final ThreadLocal<DateFormat> messageFormat = new ThreadLocal<DateFormat>();
    private static final String MESSAGE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final DateFormat getDateFormat() {
        DateFormat format = messageFormat.get();
        if (format == null) {
            format = new SimpleDateFormat(MESSAGE_FORMAT, Locale.getDefault());
            messageFormat.set(format);
        }

        return format;
    }
}
