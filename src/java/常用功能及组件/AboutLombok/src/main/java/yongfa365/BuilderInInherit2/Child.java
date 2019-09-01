package yongfa365.BuilderInInherit2;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;


/**
 * 我是child
 */
@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public  class  Child extends Parent
{
    /**
     * 我是childname
     */
    String childName;
}