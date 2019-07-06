package yongfa365.entity;


import lombok.Builder;
import lombok.Data;
import yongfa365.common.Helper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
public class Person {
    Long id;
    String name;
    Integer age;
    LocalDateTime birthday;
    Date fromDate;
    Float height;
    String idCard;
    BigDecimal totalAmout;
    String resume;
    String gender;
    GenderType genderType;

    enum GenderType {
        MAN(1, "男"), WOMAN(1, "女");
        int value;
        String desc;

        GenderType(int value, String desc) {
            this.value = value;
            this.desc = desc;
        }

        public String getDesc() {
            return desc;
        }
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    private static List<Person> allPersons;

    public static List<Person> getAllPersons() {
        if (allPersons == null) {
            allPersons = new ArrayList<>();
            for (Long i = 0L; i < 5; i++) {
                allPersons.add(Person.builder().id(i).name("姓名" + i).build());
            }
        }
        //模拟数据库操作，要等待5s
        Helper.sleep(5000);
        return allPersons;
    }
}