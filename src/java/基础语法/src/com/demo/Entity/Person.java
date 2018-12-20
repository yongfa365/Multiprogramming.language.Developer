package com.demo.Entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Person {
    private Integer id;
    private String name;
    private LocalDateTime birthday;
    private Boolean isMan;
    private BigDecimal height = new BigDecimal("1.78"); //设置默认值
    private Boolean isHuman = true; //要想变只读，不给他设置set就行了,有了默认值，也就总是返回true了。


    //以下内容都是点点点，自动生成的，各IDE都有这功能，但用Lombok更方便，就不用写下面的内容了，他会在编译时生成的二进制文件里。
    public Person() {
    }

    public Person(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Person(Integer id, String name, LocalDateTime birthday, Boolean isMan, BigDecimal height, Boolean isHuman) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.isMan = isMan;
        this.height = height;
        this.isHuman = isHuman;
    }

    public <T extends Comparable<BigDecimal>> Boolean IsHighter(T comparator) {
        comparator.compareTo(height);
        return true;
    }

    public Integer getId() {
        return id;
    }

    /**
     * <h1>这个是方法的注释,<span style="color:red">竟然可以写html</span></h1>
     * 更多参见：<a href="http://wiki.jikexueyuan.com/project/java/documentation.html">极客学院.Java 文件注释</a>
     *
     * @param id 是个id吧
     * @return void
     * @author 柳永法
     * @version 1.0
     * @since 2018-12-05
     */
    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDateTime birthday) {
        this.birthday = birthday;
    }

    public Boolean getMan() {
        return isMan;
    }

    public void setMan(Boolean man) {
        isMan = man;
    }

    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    //自动生成的时候把is去掉了
    public Boolean getHuman() {
        return isHuman;
    }

/*    public void setHuman(Boolean human) {
        isHuman = human;
    }*/

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthday=" + birthday +
                ", isMan=" + isMan +
                ", height=" + height +
                ", isHuman=" + isHuman +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (id != null ? !id.equals(person.id) : person.id != null) return false;
        if (name != null ? !name.equals(person.name) : person.name != null) return false;
        if (birthday != null ? !birthday.equals(person.birthday) : person.birthday != null) return false;
        if (isMan != null ? !isMan.equals(person.isMan) : person.isMan != null) return false;
        if (height != null ? !height.equals(person.height) : person.height != null) return false;
        return isHuman != null ? isHuman.equals(person.isHuman) : person.isHuman == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        result = 31 * result + (isMan != null ? isMan.hashCode() : 0);
        result = 31 * result + (height != null ? height.hashCode() : 0);
        result = 31 * result + (isHuman != null ? isHuman.hashCode() : 0);
        return result;
    }

}
