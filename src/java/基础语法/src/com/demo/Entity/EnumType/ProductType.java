package com.demo.Entity.EnumType;

public enum ProductType {
    DEFAULT(0, "默认"),
    HOTEL(1 << 0, "酒店"), //1
    FLIGHT(1 << 1, "机票"), //2
    BUS(1 << 2, "车票"), //4
    FLIGHT_HOTEL(FLIGHT.value | HOTEL.value, "机酒"), //3
    BUS_HOTEL(BUS.value | HOTEL.value, "船酒");//5

    int value;
    String text;

    ProductType(int value, String text) {
        this.value = value;
        this.text = text;
    }

    public int getValue() {
        return value;
    }
}
