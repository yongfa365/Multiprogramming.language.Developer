package com.json.test.jsonutils.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum HobbyEnum implements BaseEnum {
    eat(1, "吃"), drink(2, "喝"), play(2, "玩");

    @JsonValue
    private int value;
    private String desc;

    public void setValue(int value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public int getValue() {
        return value;
    }

    private HobbyEnum(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @JsonCreator
    public static HobbyEnum getItem(int code) {
        for (HobbyEnum item : values()) {
            if (item.getValue() == code) {
                return item;
            }
        }
        return null;
    }
}
