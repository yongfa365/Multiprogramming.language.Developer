package yongfa365.AboutEasyExcel;


import com.alibaba.excel.metadata.Sheet;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class ConsoleTest {

    public static void main(String[] args) throws IOException {
        {
            var fileName = "D:\\单表测试.xlsx";
            var lstData = ExcelHelper.genPersons(60000);

            var start = LocalDateTime.now();

            ExcelHelper.Export(lstData, fileName);

            System.out.println("导出成功: " + fileName + " " + ChronoUnit.SECONDS.between(start, LocalDateTime.now()) + "s");
        }

        {
            var fileName = "D:\\多表测试.xlsx";
            var sheet1 = ExcelHelper.SheetItem.builder()
                    .sheet(new Sheet(1, 0, Person.class, "我是表一", null))
                    .data(ExcelHelper.genPersons(10))
                    .build();

            var sheet2 = ExcelHelper.SheetItem.builder()
                    .sheet(new Sheet(2, 0, Person.class, "我是表二", null))
                    .data(ExcelHelper.genPersons(5))
                    .build();

            ExcelHelper.Export(fileName, List.of(sheet1, sheet2));
            System.out.println("导出成功: " + fileName);
        }
    }
}
