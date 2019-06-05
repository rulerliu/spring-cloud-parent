package com.mayikt.core.utils;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.*;
import java.util.Calendar;
import java.util.Date;
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

    /** yyyy-MM-dd HH:mm:ss */
    public static final String YYYYMMDDHHMMSS    = "yyyy-MM-dd HH:mm:ss";
    /** yyyy-MM-dd HH:mm:ss SSS */
    public static final String YYYYMMDDHHMMSSSSS = "yyyy-MM-dd HH:mm:ss SSS";
    /** yyyyMMddHHmmssSSS */
    public static final String yyyyMMddHHmmssSSS = "yyyyMMddHHmmssSSS";
    /** yyyy-MM-dd */
    public static final String YYYYMMDD          = "yyyy-MM-dd";
    /** yyyyMMdd */
    public static final String yyyyMMdd          = "yyyyMMdd";
    /** yyyyMMddHH */
    public static final String yyyyMMddHH        = "yyyyMMddHH";
    /**
     * yyyyMMddHHmmss
     */
    public static final String yyyyMMddHHmmss = "yyyyMMddHHmmss";

    public static final DateFormat getDateFormat() {
        DateFormat format = messageFormat.get();
        if (format == null) {
            format = new SimpleDateFormat(MESSAGE_FORMAT, Locale.getDefault());
            messageFormat.set(format);
        }

        return format;
    }

    /** <br>
     * 方法名称: 格式化时间<br>
     * 适用场景: <br>
     * 业务逻辑说明: <br>
     *
     * @param date
     * @param formatStr
     * @return
     * @author linyujia
     * @time 2017年11月13日 下午3:02:37 */
    public static String formatDate(Date date, String formatStr) {
        SimpleDateFormat formatDate = new SimpleDateFormat(formatStr);
        String resultTime = formatDate.format(date);
        return resultTime;
    }

    /** <br>
     * 适用场景: 将英文缩写格式的字符串转换为日期，例如：08-JAN-18<br>
     * 调用方式: <br>
     * 业务逻辑说明<br>
     *
     * @param str
     * @return
     * @autho linyujia
     * @time 2018年5月8日 下午8:57:03 */
    public static Date enStrToDate(String str) {

        if (StringUtils.isBlank(str)) {
            return null;
        }
        DateTimeFormatter format = DateTimeFormat.forPattern("dd-MMM-yy");
        DateTime date2 = DateTime.parse(str, format.withLocale(Locale.US));

        return date2.toDate();
    }

    /** <br>
     * 方法名称: 将Date类型转换为datetime字符串<br>
     * 适用场景: <br>
     * 业务逻辑说明: <br>
     *
     * @param date
     * @return
     * @author zhashenglin
     * @time 2017年10月27日 上午10:17:37 */
    public static String dateToDatetimeStr(Date date) {
        if (null == date) {
            return null;
        }
        Format format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dataStr = format.format(date);
        return dataStr;
    }

    /** <br>
     * 方法名称: 将Date类型转换为date字符串<br>
     * 适用场景: <br>
     * 业务逻辑说明: <br>
     *
     * @param date
     * @return
     * @author zhashenglin
     * @time 2017年10月27日 上午10:18:56 */
    public static String dateToDateStr(Date date) {
        if (null == date) {
            return null;
        }
        Format format = new SimpleDateFormat("yyyy-MM-dd");
        String dataStr = format.format(date);
        return dataStr;
    }

    /** <br>
     * 方法名称: 将datetime字符串转为Date类型<br>
     * 适用场景: <br>
     * 业务逻辑说明: <br>
     *
     * @param strDate
     * @return
     * @author zhashenglin
     * @time 2017年10月27日 上午10:19:25 */
    public static Date datetimeStrToDate(String strDate) {
        if (StringUtils.isBlank(strDate)) {
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date date = formatter.parse(strDate, pos);
        return date;
    }

    /** <br>
     * 方法名称: 将date字符串转为Date类型<br>
     * 适用场景: <br>
     * 业务逻辑说明: <br>
     *
     * @param strDate
     * @return
     * @author zhashenglin
     * @time 2017年10月27日 上午10:27:51 */
    public static Date dateStrToDate(String strDate) {
        if (StringUtils.isBlank(strDate)) {
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date date = formatter.parse(strDate, pos);
        return date;
    }

    /** <p>
     * 方法名称：获取日期
     * </p>
     * <p>
     * 方法说明：在特定日期的基础上增加或者减少天数
     * </p> */
    public static Date getDate(Date date, Integer days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if (days != null) {
            calendar.add(Calendar.DAY_OF_MONTH, days);
        }
        return calendar.getTime();
    }

    /** <br>
     * 标题: <br>
     * 描述: 将年月日格式的字符串转换为时间并且时分秒为0<br>
     *
     * @autho daiyuanyuan
     * @time 2018年4月4日 下午5:05:52 */
    public static Date yymmddStrToDate(String strDate) {
        if (StringUtils.isBlank(strDate)) {
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        ParsePosition pos = new ParsePosition(0);
        Date date = formatter.parse(strDate, pos);
        return date;
    }

    /** <br>
     * 标题: <br>
     * 描述: 去掉日期的时分秒<br>
     *
     * @autho daiyuanyuan
     * @time 2018年4月4日 下午5:05:52 */
    public static Date dateToDate(Date date) {
        if (null == date) {
            return null;
        }
        String dateStr = dateToYYYYMMDDStr(date);
        Date newDate = yymmddStrToDate(dateStr);
        return newDate;
    }

    /** <br>
     * 标题: <br>
     * 描述: <br>
     *
     * @autho daiyuanyuan
     * @time 2018年4月4日 下午6:04:25 */
    public static Date StrToDate(String strDate) {
        if (StringUtils.isBlank(strDate)) {
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
        ParsePosition pos = new ParsePosition(0);
        Date date = formatter.parse(strDate, pos);
        return date;
    }

    public static Date formatStrToDate(String strDate, String format) {
        if (org.apache.commons.lang.StringUtils.isBlank(strDate)) {
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        ParsePosition pos = new ParsePosition(0);
        Date date = formatter.parse(strDate, pos);
        return date;
    }

    /** <br>
     * 标题: <br>
     * 描述: <br>
     *
     * @autho daiyuanyuan
     * @time 2018年4月4日 下午9:24:48 */
    public static String dateToYYYYMMDDStr(Date date) {
        if (null == date) {
            return null;
        }
        Format format = new SimpleDateFormat("yyyyMMdd");
        String dataStr = format.format(date);
        return dataStr;
    }

    /** <br>
     * 标题: <br>
     * 描述: 格式化时间<br>
     *
     * @autho yunan
     * @time 2018年4月4日 下午9:24:48 */
    public static Date formatDate(Date date) {
        Format format = new SimpleDateFormat("yyyy-MM-dd");
        String dataStr = format.format(date);
        Date resultDate = dateStrToDate(dataStr);
        return resultDate;
    }
    public static Date getFirstDayOfMonth(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH,1);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        return calendar.getTime();
    }
    public static Date getYestoday(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        return calendar.getTime();
    }
    public static String getNextDay(String currDateStr){
        SimpleDateFormat sdf=new SimpleDateFormat(yyyyMMdd);
        ParsePosition pos = new ParsePosition(0);
        Date currDate=sdf.parse(currDateStr,pos);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currDate);
        calendar.add(Calendar.DAY_OF_MONTH,1);
        Date nextDate=calendar.getTime();
        return sdf.format(nextDate);
    }
    public static Date getLastDayOfMonth(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH,1);
        calendar.set(Calendar.DAY_OF_MONTH,1);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.add(Calendar.SECOND,-1);
        return calendar.getTime();
    }

    public static void main(String[] args) {
        int daysOfStartDateFromNowDate = getDaysOfStartDateFromNowDate(DateUtils.yyyyMMddHHmmss, "20181122154306");
        System.out.println("相差天数:" + daysOfStartDateFromNowDate);
    }

    /**
     * 计算给定字符串日期到当前日期相差的天数
     * @param pattern yyyyMMddHHmmss
     * @param startDate 例如20180702154306
     * @return
     * @throws ParseException
     */
    public static int getDaysOfStartDateFromNowDate(String pattern,String startDate){
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date parse;
        int days = 0;
        try {
            parse = sdf.parse(startDate);
            long now_time = new Date().getTime();
            long start_time = parse.getTime();
            long nd = 1000 * 24 * 60 * 60;

            days = (int) ((now_time - start_time)/nd);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return days;
    }

    /**
     * 判断是否在以后30天之内
     * @param time yyyyMMddHHmmss
     */
    public static boolean isIn30Days(Date time) {
        Calendar calendar = Calendar.getInstance(); // 得到日历
        calendar.setTime(new Date());// 把当前时间赋给日历
        calendar.add(Calendar.DAY_OF_MONTH, 30); // 设置为30天后
        Date after30days = calendar.getTime(); // 得到30天后的时间
        return after30days.getTime() >= time.getTime();
    }

}
