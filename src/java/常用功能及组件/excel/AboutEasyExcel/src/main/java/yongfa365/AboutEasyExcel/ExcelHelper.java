package yongfa365.AboutEasyExcel;


import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import lombok.Builder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExcelHelper {

    public static void Export(List<? extends BaseRowModel> lstData, String fileName) throws IOException {
        try (var out = new FileOutputStream(fileName)) {
            Export(lstData, out);
        }
    }

    public static void Export(List<? extends BaseRowModel> lstData, OutputStream outputStream) throws IOException {
        var writer = new ExcelWriter(outputStream, ExcelTypeEnum.XLSX);

        var sheet1 = new Sheet(1, 0, Person.class);
        //sheet1.setColumnWidthMap(Map.of(0, 2000, 1, 5000, 3, 20000)); //改变列宽
        //sheet1.setAutoWidth(true); //设置没什么用
        sheet1.setSheetName("Sheet1");
        writer.write(lstData, sheet1);
        writer.finish();

        outputStream.flush();
    }

    public static void Export(String fileName, List<SheetItem> lstData) throws IOException {
        try (var outputStream = new FileOutputStream(fileName)) {
            var writer = new ExcelWriter(outputStream, ExcelTypeEnum.XLSX);
            lstData.forEach(p -> {
                writer.write(p.data, p.sheet);
            });
            writer.finish();
            outputStream.flush();
        }
    }

    public static List<Person> genPersons(int size) {
        var lst = new ArrayList<Person>();
        for (int i = 0; i < size; i++) {
            var item = Person.builder()
                    .no(100000000000000L + i)
                    .name("柳永法" + i)
                    .age(i % 100)
                    .genderType(i % 2 == 0 ? Person.GenderType.MAN : Person.GenderType.WOMAN)
                    .birthday(LocalDateTime.now().plusHours(i))
                    .fromDate(Date.from(ZonedDateTime.now().plusHours(i).toInstant()))
                    .height(1F * i / 3)
                    .idCard(String.valueOf(142702198309145843L + i))
                    .totalAmout(BigDecimal.valueOf(i * 123456789))
                    .resume(i + "这个是简历内容".repeat(10))
                    .build();
            lst.add(item);
        }
        return lst;
    }

    public static String genFileName() {
        return DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(LocalDateTime.now()) + ".xlsx";
    }


    @Builder
    static class SheetItem {
        Sheet sheet;
        List<? extends BaseRowModel> data;
    }
}
