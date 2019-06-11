package com.mayikt.core.utils;

import org.springframework.cglib.beans.BeanCopier;

import java.util.ArrayList;
import java.util.List;

public class BeanCopyUtil {

    public static <E, T> List<T> copyList(List<E> elements, Class<T> cls) {
        List<T> resultList = new ArrayList<>();
        if (elements == null || elements.size() == 0) {
            return resultList;
        }
        BeanCopier copier = BeanCopier.create(elements.get(0).getClass(), cls, false);
        for (E element : elements) {
            T obj = null;
            try {
                obj = cls.newInstance();
                copier.copy(element, obj, null);
            } catch (Exception e) {
                e.printStackTrace();
            }

            resultList.add(obj);
        }
        return resultList;
    }

    public static <E, T> T copyObject(E element, Class<T> cls) {
        if (element == null) {
            return null;
        }
        BeanCopier copier = BeanCopier.create(element.getClass(), cls, false);
        try {
            T obj = cls.newInstance();
            copier.copy(element, obj, null);
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
