package yongfa365.BuilderInInherit2;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;


/**
 * 我是parent
 */
@SuperBuilder
@Data
public class Parent
{
    /**
     * 我是parentName
     */
    String parentName;
}
