## 官方网站
id|desc|url
---|----|----
1|easyexcel|https://github.com/alibaba/easyexcel
2|quickstart|https://github.com/alibaba/easyexcel/blob/master/quickstart.md
3|7 行代码优雅地实现 Excel 文件导出功能？|https://mp.weixin.qq.com/s/TZYxyzt_FpXcWuJpxz_IZQ

easyexcel是使用java1.7写的，有些类型支持不好，如：LocalDateTime指定了format是识别不了的。针对这种情况处理方案是：自行转成String，或者在实体里再写个字段来处理最终输出，如：
```java
//直接给他家标注不合适
GenderType genderType;

//再扩展个字段加标注，最终用这个输出
@ExcelProperty(value = "性别", index = 2)
String gender;

public String getGender() {
    return genderType.getDesc();
}

```

别人已经写的很好了，我就不写介绍了

测试写6万行时占满1核CPU