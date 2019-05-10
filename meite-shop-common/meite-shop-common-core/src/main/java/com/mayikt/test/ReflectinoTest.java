package com.mayikt.test;

import java.lang.reflect.Method;

/**
 * @description:
 * @author: liuwq
 * @date: 2019/5/6 0006 上午 10:01
 * @version: V1.0
 * @Copyright:该项目“基于SpringCloud2.x构建微服务电商项目”由每特教育|蚂蚁课堂版权所有，未经过允许的情况下， 私自分享视频和源码属于违法行为。
 */
public class ReflectinoTest {

    public static void main(String[] args) {
        test2();
        System.out.println("-------------------");
        printClassInfo("s");
    }

    public static void test1() {
        User u = new User();
        // 方式1:
        Class c1 = User.class;

        // 方式2:
        Class c2 = u.getClass();
        // 方式3:

        try {
            Class c3 = Class.forName("com.forezp.User");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // 可以通过类的类型创建该类的实例对象
        try {
            User user = (User) c1.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void test2() {
        Class c1 = String.class;
        Class c2 = int.class;
        Class c3 = void.class;
        System.out.println(c1.getName());
        System.out.println(c2.getSimpleName());
        System.out.println(c3.getName());
    }

    public static void printClassInfo(Object object) {
        Class c = object.getClass();
        System.out.println("类的名称：" + c.getName());

        /**
         * 一个成员方法就是一个method对象
         * getMethod()所有的 public方法，包括父类继承的 public
         * getDeclaredMethods()获取该类所有的方法，包括private ,但不包括继承的方法。
         */
        Method[] methods = c.getMethods();//获取方法
        //获取所以的方法，包括private ,c.getDeclaredMethods();

        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];
            //得到方法的返回类型
            Class returnType = method.getReturnType();
            System.out.print(returnType.getName());
            //得到方法名：
            System.out.print(methods[i].getName() + "(");

            Class[] parameterTypes = method.getParameterTypes();
            for (Class class1 : parameterTypes) {
                System.out.print(class1.getName() + ",");
            }
            System.out.println(")");
        }
    }

}

class User {
}