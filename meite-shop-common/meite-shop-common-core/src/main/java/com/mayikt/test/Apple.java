package com.mayikt.test;

/**
 * @description:
 * @author: liuwq
 * @date: 2019/6/14 0014 下午 12:27
 * @version: V1.0
 */
public class Apple extends Fruit {
    public String name = "apple";
    public Apple() {
        tellName();
        printName();
    }
    public void tellName() {
        System.out.println("Apple tellName:" + name);
    }
    public void printName() {
        System.out.println("Apple printName:" + name);
    }

    public static void main(String[] args) {
        new Apple();
    }
}

class Fruit {
    public String name = "fruit";
    public Fruit() {
        tellName();
        printName();
    }
    public void tellName() {
        System.out.println("Fruit tellName:" + name);
    }
    public void printName() {
        System.out.println("Fruit printName:" + name);
    }
}