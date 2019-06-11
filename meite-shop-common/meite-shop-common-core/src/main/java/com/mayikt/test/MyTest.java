package com.mayikt.test;

/**
 * @description:
 * @author: liuwq
 * @date: 2019/6/11 0011 下午 12:41
 * @version: V1.0
 */
public class MyTest {

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println(i);
            if (i == 3) {
                return;
            }
        }
        System.out.println(">>>>>>");
    }

}
