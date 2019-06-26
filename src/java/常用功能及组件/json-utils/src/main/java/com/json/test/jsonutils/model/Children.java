package com.json.test.jsonutils.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.json.test.jsonutils.EnumDeSerialize4FastJson;
import com.json.test.jsonutils.EnumSerialize4FastJson;

import java.util.HashMap;
import java.util.List;


public class Children {
    @JsonBackReference
    @JSONField(serialize = false)
    private Father myFather;
    private String name;
    private int age;

    @JSONField(serializeUsing = EnumSerialize4FastJson.class, deserializeUsing = EnumDeSerialize4FastJson.class)
    private GenderEnum gender;

//    @JSONField(serializeUsing = EnumSerialize4FastJson.class, deserializeUsing = EnumDeSerialize4FastJson.class)
    private List<HobbyEnum> hobbys;

    private HashMap<String,String> hashMap;

    public HashMap<String, String> getHashMap() {
        return hashMap;
    }

    public void setHashMap(HashMap<String, String> hashMap) {
        this.hashMap = hashMap;
    }

    public List<HobbyEnum> getHobbys() {
        return hobbys;
    }

    public void setHobbys(List<HobbyEnum> hobbys) {
        this.hobbys = hobbys;
    }

    public GenderEnum getGender() {
        return gender;
    }

    public void setGender(GenderEnum gender) {
        this.gender = gender;
    }

    public Father getMyFather() {
        return myFather;
    }

    public void setMyFather(Father myFather) {
        this.myFather = myFather;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
