package com.demo.Entity.EnumType;

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
}
