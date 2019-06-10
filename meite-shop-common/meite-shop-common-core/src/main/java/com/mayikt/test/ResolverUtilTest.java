package com.mayikt.test;

import com.mayikt.core.utils.ResolverUtil;

import java.util.Set;

/**
 * @description:
 * @author: liuwq
 * @date: 2019/6/10 0010 上午 10:06
 * @version: V1.0
 */
public class ResolverUtilTest {

    public static void main(String[] args) {
        Class<?> superType = Object.class;
        String packageName = "com/mayikt/test";
        ResolverUtil<Class<?>> resolverUtil = new ResolverUtil<Class<?>>();
        resolverUtil.find(new ResolverUtil.IsA(superType), packageName);
        Set<Class<? extends Class<?>>> typeSet = resolverUtil.getClasses();
        for(Class<?> type : typeSet){
            // Ignore inner classes and interfaces (including package-info.java)
            // Skip also inner classes. See issue #6
            if (!type.isAnonymousClass() && !type.isInterface() && !type.isMemberClass()) {
                System.out.println(type.getName() + "-->" + type.getSimpleName());
            }
        }
    }

}
