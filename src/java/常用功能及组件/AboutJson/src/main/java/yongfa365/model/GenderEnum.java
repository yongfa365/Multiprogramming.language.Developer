package yongfa365.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum GenderEnum implements BaseEnum {
    male(1, "男"), female(2, "女");

    @JsonValue
    private int value;
    private String text;

    @Override
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    private GenderEnum(int value, String text) {
        this.value = value;
        this.text = text;
    }

    @JsonCreator
    public static GenderEnum getItem(int code) {
        for (GenderEnum item : values()) {
            if (item.getValue() == code) {
                return item;
            }
        }
        return null;
    }
}
