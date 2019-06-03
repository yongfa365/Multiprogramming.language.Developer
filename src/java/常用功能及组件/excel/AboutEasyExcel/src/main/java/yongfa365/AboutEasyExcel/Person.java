package yongfa365.AboutEasyExcel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
public class Person  extends BaseRowModel {
    //直接log显示成1E+14这样的结构了，实际用时可能要转为String
    @ExcelProperty(value = "NO.", index = 0)
    Long no;

    @ExcelProperty(value = "姓名", index = 1)
    String name;

    @ExcelProperty(value = "年龄", index = 3)
    Integer age;

    //对java8的这个支持不好
    @ExcelProperty(value = "出生日期", index = 4)
    LocalDateTime birthday;

    @ExcelProperty(value = "入职日期", index = 4,format = "yyyy-MM-dd")
    Date fromDate;

    @ExcelProperty(value = "身高", index = 5)
    Float height;

    @ExcelProperty(value = "身份证号", index = 6)
    String idCard;

    @ExcelProperty(value = "总资产", index = 7)
    BigDecimal totalAmout;

    @ExcelProperty(value = "简历", index = 8)
    String resume;

    @ExcelProperty(value = "性别", index = 2)
    String gender;

    GenderType genderType;

    public String getGender() {
        return genderType.getDesc();
    }


    static enum GenderType {
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
}
