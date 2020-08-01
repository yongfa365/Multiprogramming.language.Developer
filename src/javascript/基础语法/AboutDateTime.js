/*jshint esversion: 6 */

//#region date 的各种输出
{
    var now = new Date();

    //js没有原生的自定义格式化方法，有几个固定的不符合国情的（想要好用的看最后面的format）：
    now.toISOString(); //"2020-07-19T05:11:33.175Z"    少8小时的
    now.toLocaleDateString(); //"7/19/2020"  不好用

    var now = DateTime.Now;

    console.log("now.getFullYear()", now.getFullYear());
    console.log("now.getMonth()", now.getMonth()); //是索引，比实际月少1
    console.log("now.getDate()", now.getDate());

    console.log("now.getHours()", now.getHours()); //是24小时制的
    console.log("now.getMinutes()", now.getMinutes());
    console.log("now.getSeconds()", now.getSeconds());
    console.log("now.getMilliseconds()", now.getMilliseconds());

    console.log("now.getTime()", now.getTime());
    console.log("now.getDay()", now.getDay()); //DayOfWeek
    console.log("now.getTimezoneOffset()", now.getTimezoneOffset()); //当前时区到UTC的秒数，+08:00这个值是-480，奇葩

}
//#endregion




//#region 定义DateTime 及 字符串转DateTime
{
    //初始化的语法
    new Date();
    new Date(value);
    new Date(dateString); //不推荐用字符串解析日期，有需要自行解析
    new Date(year, monthIndex[, day[, hours[, minutes[, seconds[, milliseconds]]]]]); //注意monthIndex是index，如5月要传4

    //将js日期时间转C#的方法：new DateTime(1970, 01, 01).AddMilliseconds(1595134697251);
    Date.now(); //1595134697251 从UTC 1970-01-01 0点起的“毫秒数”
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

}
//#endregion





//#region DateTime增减、TimeSpan、比较大小
{
    //没有原生的加减年、月、日等的方法

    var tomorrow = new Date(Date.now() + 1*24*60*60*1000); //这就是加一天了

    new Date(2020, 07, 19) < new Date(2020, 12, 12); //true

    new Date(2020, 07, 19) == new Date(2020, 07, 19); //false, 不能这么比较
    new Date(2020, 07, 19).getTime() == new Date(2020, 07, 19).getTime(); //true
    new Date(2020, 07, 19) - new Date(2020, 07, 19) === 0; //true 相减的结果是：总毫秒的差值。

}
//#endregion





//#region 计时器，Stopwatch
{
    //看一段代码执行花了多久
    //方法1（也可以用Date.now()）：
    var time = new Date();
    await new Promise(r => setTimeout(r, 2000));
    console.log(`耗时：${new Date()-time} ms`);

    //方法2：没有StopWatch

}
//#endregion





//#region timer 定时器 setTimeout | setInterva
{
    //常用以下两种写法：
    setTimeout(() => {
        //your code
    }, 1000);

    setInterval(() => {
        //your code
    }, 1000);
    

    //契约
    var id = scope.setTimeout(function [, delay, arg1, arg2, ...]);
    var id = scope.setTimeout(function [, delay]);
    var id = scope.setTimeout(code[, delay]);

    
    //setTimeout和setInterval指定的回调函数，必须等到本轮事件循环的所有同步任务都执行完，才会开始执行。即便delay为0
    //一个例子，先输出1和3，然后才其他
    console.log(1);
    //1s后执行，
    var id1 = setTimeout(() => console.log("我是setTimeout"), 1000);
    var id1 = setTimeout((a, b) => console.log("我是setTimeout ", a + b), 1000, 100, 3);

    //每1s执行一次
    var id2 = setInterval(() => console.log("我是setInterval", new Date().toISOString()), 1000);
    var id2 = setInterval((a, b) => console.log("我是setInterval", a + b), 1000, 100, 3);
    console.log(3)
}
//#endregion





//#region sleep功能，语言功能不全，就使用者花时间来补了
{
    // 第一种： 在最外层可以简单的这么用
    await new Promise(r => setTimeout(r, 2000));



    // 第二种： Promise的写法
    function sleep(ms) {
        return new Promise(resolve => setTimeout(resolve, ms));
    }

    //可以一直then下去来延时
    sleep(2000).then(p => {
        console.log("2秒后...")
    }).then(p => sleep(3000)).then(p => {
        console.log("再3秒后...")
    }).then(p => sleep(2000)).then(p => {
        console.log("再2秒后...")
    });


    //在函数内部可以结合async来用
    async function demo() {
        console.log('准备sleep 2s');
        await sleep(2000);
        console.log('完成sleep');

        // Sleep in loop
        for (let i = 0; i < 5; i++) {
            await sleep(2000);
            console.log(i);
        }
    }

    demo();



    // 第三种：同步xhr
    function sleepByXHR(ms) {
        var xhr = new XMLHttpRequest();
        xhr.open('GET', 'http://httpstat.us/200?sleep=' + ms, false); // `false` makes the request synchronous
        xhr.send();
    }

    var time = new Date();
    sleepByXHR(2000)
    console.log(`耗时：${new Date()-time} ms`);

}
//#endregion







//#region js的Date默认格式化不行，找个简单好用的
{
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
}
//#endregion

