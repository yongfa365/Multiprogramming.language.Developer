package com.wingontravel.jsonutils.model;


import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.parser.Feature;

import java.time.LocalDateTime;

public class Father {
    private Children myChildren;
    private String name;
    private int age;
    private String kongziduan;

    public String getKongziduan() {
        return kongziduan;
    }

    public void setKongziduan(String kongziduan) {
        this.kongziduan = kongziduan;
    }

    @JSONField(format = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime birthday;

    public LocalDateTime getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDateTime birthday) {
        this.birthday = birthday;
    }

    public Children getMyChildren() {
        return myChildren;
    }

    public void setMyChildren(Children myChildren) {
        this.myChildren = myChildren;
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
