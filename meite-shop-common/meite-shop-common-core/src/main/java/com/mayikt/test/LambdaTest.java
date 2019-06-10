package com.mayikt.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: liuwq
 * @date: 2019/6/10 0010 上午 9:46
 * @version: V1.0
 */
public class LambdaTest {

    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();
        list.add("c");
        list.add("a");
        list.add("b");

        Collections.sort(list, (a1, a2) -> {
            return a1.compareTo(a2);
        });

        new Thread(() -> {
            System.out.println("aaa");
        }).start();


        Set<String> collect = list.stream().map(String::toUpperCase).collect(Collectors.toSet());

        collect.forEach(System.out::println);
        System.out.println(">>>");
        list.forEach(System.out::println);
    }

}
