package yongfa365.BuilderInInherit1;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Child extends Parent {
    private String childName;

    //需要把父类的所有字段都写到这个子类的构造函数里
    @Builder
    private Child(String parentName, String childName) {
        super(parentName);
        this.childName = childName;
    }
}