package com.mayikt.test;

/**
 * @description:
 * @author: liuwq
 * @date: 2019/5/7 0007 下午 4:06
 * @version: V1.0
 * @Copyright:该项目“基于SpringCloud2.x构建微服务电商项目”由每特教育|蚂蚁课堂版权所有，未经过允许的情况下， 私自分享视频和源码属于违法行为。
 */
public enum EnumTest {
    RED(01, "红色"),
    YELLOW(02, "黄色"),
    BLUE(03, "蓝色"),
    ;

    private Integer code;
    private String color;

    public static void main(String[] args) {
        System.out.println(EnumTest.RED.getCode());
        System.out.println(EnumTest.RED.getColor());
    }

    EnumTest(Integer code, String color) {
        System.out.println(">>>有参构造方法");
        this.code = code;
        this.color = color;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

}
