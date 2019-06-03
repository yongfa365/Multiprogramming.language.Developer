## 官方网站
id|desc|url
---|----|----
1|easyexcel|https://github.com/alibaba/easyexcel
2|quickstart|https://github.com/alibaba/easyexcel/blob/master/quickstart.md
3|7 行代码优雅地实现 Excel 文件导出功能？|https://mp.weixin.qq.com/s/TZYxyzt_FpXcWuJpxz_IZQ

easyexcel是使用java1.7写的，有些类型支持不好，如：LocalDateTime指定了format是识别不了的。针对这种情况处理方案是：自行转成String，或者在实体里再写个字段来处理最终输出，如：
```java
public class Person  extends BaseRowModel {
    //直接给他加标注不合适
    GenderType genderType;
    
    //再扩展个字段加标注，最终用这个输出
    @ExcelProperty(value = "性别", index = 2)
    String gender;
    
    public String getGender() {
        return genderType.getDesc();
    }
}
```

```java
核心代码：
        var writer = new ExcelWriter(outputStream, ExcelTypeEnum.XLSX);

        var sheet1 = new Sheet(1, 0, Person.class,"Sheet1",null);
        sheet1.setColumnWidthMap(Map.of(0, 2000, 1, 5000, 3, 20000)); //改变列宽
       
        writer.write(lstData, sheet1);
        writer.finish();

        outputStream.flush();

```

别人已经写的很好了，我就不写介绍了

测试写6万行时占满1核CPU跑6s