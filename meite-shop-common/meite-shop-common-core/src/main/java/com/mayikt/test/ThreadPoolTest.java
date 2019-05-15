package com.mayikt.test;

import com.mayikt.core.utils.DateUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @description:
 * @author: liuwq
 * @date: 2019/5/9 0009 下午 2:29
 * @version: V1.0
 * @Copyright:该项目“基于SpringCloud2.x构建微服务电商项目”由每特教育|蚂蚁课堂版权所有，未经过允许的情况下， 私自分享视频和源码属于违法行为。
 */
public class ThreadPoolTest {

    public static void main(String[] args) {
        test1();
//        findCityByName1();
//        System.out.println(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
//        System.out.println(new SimpleDateFormat("yyyyMMddHHmmssSSSS").format(new Date()));
    }

    public static void findCityByName1() {
        Long start = System.currentTimeMillis();
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 1000; i++) {
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    Long start = System.currentTimeMillis();
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Long end = System.currentTimeMillis();
                    System.out.println(">>>" + (end - start));
                }
            });
        }

        threadPool.shutdown();
        while (true) {
            if (threadPool.isTerminated()) {
                Long end = System.currentTimeMillis();
                System.out.println(end - start);
                break;
            }
        }
    }

    public static void test1() {
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        for (int i = 0; i < 100; i++) {
            final int index = i;
            fixedThreadPool.execute(new Runnable() {

                @Override
                public void run() {
                    try {
                        DateFormat dateFormat = DateUtils.getDateFormat();
                        Date parse = dateFormat.parse("2019-01-01 12:12:12");
                        System.out.println(parse.toLocaleString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        fixedThreadPool.shutdown();
    }

}
