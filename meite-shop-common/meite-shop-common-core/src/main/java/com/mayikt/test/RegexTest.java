package com.mayikt.test;

/**
 * @description:
 * @author: liuwq
 * @date: 2019/5/16 0016 下午 2:29
 * @version: V1.0
 * @Copyright:该项目“基于SpringCloud2.x构建微服务电商项目”由每特教育|蚂蚁课堂版权所有，未经过允许的情况下， 私自分享视频和源码属于违法行为。
 */
public class RegexTest {

    public static void main(String[] args) {
        String regx = "a";//用来匹配是否是字母"a"
        regx = "[a,b,c,d]";//允许出现里面的某一个字母
//        regx = "[a-z]";//允许出现所有小写字母里面的某一个字母
//        regx = "[0-9]";//允许出现0-9数字中的某一个
//        regx = "[A-Za-z]";
//        regx = "[a-zA-Z0-9]";
//        regx = "[^0-9]";//允许出现不在0-9中的任意一个
//        regx = ".";//匹配任意单个字符
//        regx = "\\.";//  \\为转义字符 \\.就是匹配我这个点本身
//        regx = "\\|";
//        regx = "\\d";//等价于[0-9]
//        regx = "\\w";//等价于[a-zA-Z0-9_]注意多了一下划线
//        regx = "[a-z]+";//允许出现列表中的一个或者多个
//        regx = "q?";//允许出现一次或者0次，空串就是没有
//        regx = "[a-z]*";//零次或者多次（>=1）次
//        regx = "[a-z]{6}";//正好六次不能多也不能少
//        regx = "[a-z]{6,}";//至少6次
//        regx = "[a-z]{4,27}";//大于等于4次或者小于等于27次
        boolean matches = "abcd".matches(regx);
        System.out.println(matches);
    }

}
