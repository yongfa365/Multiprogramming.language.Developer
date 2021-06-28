package com.demo.Entity.EnumType;

import java.util.HashMap;
import java.util.Map;

public enum ColorType2 {
    RED(1, "红色"),
    BLACK(2, "黑色"),
    GREEN(3, "绿色");

    int value;
    String text;

    ColorType2(int value, String text) {
        this.value = value;
        this.text = text;
    }

    public static ColorType2 of(Integer value)
    {
        // return MEMBERS.get(value);
        for (var item : values()) {
            if (item.value == value) {
                return item;
            }
        }
        return null;
    }

    // 使用缓存的方案，或者LazyLoad在使用时处理（那时就要考虑多线程问题了）
    private static final Map<Integer, ColorType2> MEMBERS = new HashMap<>();

    static {
        for (var item : values()) {
            MEMBERS.put(item.value, item);
        }
    }
}
