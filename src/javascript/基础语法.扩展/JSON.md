### JSON
```javascript
JSON.stringify({ name: "张三" }) // "{"name":"张三"}"

//对于原始类型的字符串，转换结果会带双引号。
JSON.stringify('foo') === "foo" // false
JSON.stringify('foo') === "\"foo\"" // true

//如果对象的属性是undefined、函数或XML对象，该属性会被JSON.stringify过滤。
var obj = {
  a: undefined,
  b: function () {}
};

JSON.stringify(obj) // "{}"
JSON.stringify(obj,null, 2) // 缩进2个空格
JSON.stringify(obj,null, '\t') // 按tab缩进

//字符串必须使用双引号表示，不能使用单引号。
//对象的键名必须放在双引号里面。
//数组或对象最后一个成员的后面，不能加逗号。
JSON.parse('{"a":1,"b":"2020-02-03"}')


```