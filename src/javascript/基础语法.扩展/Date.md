### Date()
```javascript
//初始化的语法
new Date();
new Date(value);
new Date(dateString); //不推荐用字符串解析日期，有需要自行解析
new Date(year, monthIndex[, day[, hours[, minutes[, seconds[, milliseconds]]]]]); //注意monthIndex是index，如5月要传4

//将js日期时间转C#的方法：new DateTime(1970, 01, 01).AddMilliseconds(1595134697251);
Date.now(); //1595134697251 从UTC 1970-01-01 0点起的秒数
Date.parse("2020-12-23"); //1608681600000   从UTC 1970-01-01 0点起的秒数

new Date(); //Sun Jul 19 2020 12:58:10 GMT+0800 (China Standard Time)
new Date(1592913569202); //Tue Jun 23 2020 19:59:29 GMT+0800 (中国标准时间)
new Date("2020-12-23 13:56:12.123"); //Wed Dec 23 2020 13:56:12 GMT+0800 (China Standard Time)
new Date(2020, 0);
new Date(2020, 0, 2);
new Date(2018, 11, 21, 16, 28, 30);


new Date("2020-12-23");                    //Wed Dec 23 2020 08:00:00 GMT+0800 (China Standard Time) 注意：多了8小时
new Date("2020-12-23T00:56");              //Wed Dec 23 2020 00:56:00 GMT+0800 (China Standard Time) 正常
new Date("2020-12-23T00:56:58");           //Wed Dec 23 2020 00:56:58 GMT+0800 (China Standard Time) 正常
new Date("2020-12-23T00:56:58.365+08:00"); //Wed Dec 23 2020 00:56:58 GMT+0800 (China Standard Time) 正常



new Date(2020, 07, 19) < new Date(2020, 12, 12); //true

new Date(2020, 07, 19) == new Date(2020, 07, 19); //false, 不能这么比较
new Date(2020, 07, 19).getTime() == new Date(2020, 07, 19).getTime(); //true
new Date(2020, 07, 19) - new Date(2020, 07, 19) === 0; //true 相减的结果是：总毫秒的差值。

```


### js的Date默认格式化不行，找个简单好用的
```js
Date.prototype.format = function (fmt) {
    var dict = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours() % 12 == 0 ? 12 : this.getHours() % 12, //小时
        "H+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) {
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    for (var [k, v] of Object.entries(dict)) {
        if (new RegExp(`(${k})`).test(fmt)) {
            v += "";
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? v : ("00" + v).substr(v.length));
        }
    }
    return fmt;
};
new Date().format("yyyy-MM-ddTHH:mm:ss.S"); //"2020-07-19T18:58:31.692"




//改掉默认输出为：2020-7-19T19:25.868Z+08:00
Date.prototype.toString = function () {
    return `${this.getFullYear()}-${this.getMonth()+1}-${this.getDate()}T${this.getHours()}:${this.getMinutes()}.${this.getMilliseconds()}Z+08:00`;
};

```
