package com.mayikt.test;

import java.text.SimpleDateFormat;
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
//        test1();
        findCityByName1();
//        System.out.println(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
//        System.out.println(new SimpleDateFormat("yyyyMMddHHmmssSSSS").format(new Date()));
    }

    public static void findCityByName1() {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        for (int i = 0; i < 500; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(">>>" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date()));
                }
            });
        }
        executorService.shutdown();
    }

    public static void test1() {
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            final int index = i;
            fixedThreadPool.execute(new Runnable() {

                @Override
                public void run() {
//                    try {
                        System.out.println(new Date().toLocaleString() + ">>>" + index);
//                        Thread.sleep(2000);
//                    } catch (InterruptedException e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    }
                }
            });
        }
        fixedThreadPool.shutdown();
    }

}
