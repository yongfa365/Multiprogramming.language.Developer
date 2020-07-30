
//#region 构造个string
{
    var strNormal = "now";

    var strPlus = 1 + "2" + new Date() + true + "3"; //如果不是string的会使用object.toString()后连接在一起。

    var strConcat = "".concat("1", true, new Date(), 3.3333);

    let greetList = ['Hello', ' ', 'World', '!']
    var strConcat2 ="".concat(...greetList)  // "Hello World!"

    //没有string.format

    var strRepeat = "ABC".repeat(1000000); //复制1百万次5ms,相当快呀，比C#的50ms还快一个数量级

    //AA@BBB之所以只有2个A是因为Pad的数字是最终总的长度,一般测试性能时会用到
    var strPad = "@".padStart(3, 'A').padEnd(6, 'B');
    'x'.padEnd(10) //"x         "
    '12'.padStart(10, '0') // "0000000012"
    '12'.padStart(10, 'YYYY-MM-DD') // "YYYY-MM-12"
    '09-12'.padStart(10, 'YYYY-MM-DD') // "YYYY-09-12"


    //其他语言大量字符串操作时“+”性能很差，js好很多很多。
    //以下语句在C#要26s、java要8s，要用StringBuilder才能降低到4ms，而js的"+"直接只要10ms，
    console.time();
    var str="";for(let i=0;i<100000;i++){str+="xxxxxxxxxx"};console.log(str.length)
    console.timeEnd(); //12ms

    console.time();
    var arr=[];for(let i=0;i<100000;i++){arr.push("xxxxxxxxxx")};var str=arr.join("");console.log(str.length)
    console.timeEnd(); //8ms



    var strzy = "c:\\a\\b";  //c:\a\b   要转义，


    //#region 多行文本
    {
        let hotel = {hotelID:12345,hotelName:"如家"};
        `酒店信息:
        hotelID:${hotel.hotelID}
        hotelName:${hotel.hotelName}
        今天是：${new Date()}
        `

        //模板替换
        let tmpl = addrs => `
        <table>
        ${addrs.map(addr => `
            <tr><td>${addr.first}</td></tr>
            <tr><td>${addr.last}</td></tr>
        `).join('')}
        </table>
        `;

        let data = [
        { first: '<Jane>', last: 'Bond' },
        { first: 'Lars', last: '<Croft>' },
        ];

        console.log(tmpl(data));
        // <table>
        //
        //   <tr><td><Jane></td></tr>
        //   <tr><td>Bond</td></tr>
        //
        //   <tr><td>Lars</td></tr>
        //   <tr><td><Croft></td></tr>
        //
        // </table>
    }
    //#endregion
}
//#endregion



//#region string的各种操作，比较
{
    "x123"[0]; //x string也是字符数组，所以可以直接用下标取。

    "Usp_SearchCity".startsWith("Usp_"); //true 大小写敏感

    "SearchCityDTO".EndsWith("DTO"); //true 大小写敏感

    "abc123".includes("abc"); //包含不是contains而是includes,  大小写敏感

    //只是演示一下有哪些常用操作
    var strOpt = " 123|a|b|a|c456| ".trim() //虽然有trimLeft()及trimRight()但并不能去除指定字符
        .toUpperCase().toLowerCase()
        .replaceAll("a","ac") //chrome85才支持
        .substr(0, 10) //长度取多了不会报错
        .split('|');

    //“12 12”， 前后有中英文空格及tab回车换行
    var strTrim = "\r\n\t   　 　  12 12　 　  \r\n\t".trim();


    var sjoin = ["A", "BC", "DEF"].join(",");//A,BC,DEF


    //各种split
    var split1 = "a|b|c".split('|');
    var split2 = "aa@bb|cc#dd$ee,ff".split(/[@#$,|]/);
    //还可以移除那些为空的数据,空格不是空，以下结果：["a","b","c"," "]
    var split3 = " |||a|b,,c, ,, ".split(/[|, ]/).filter(Boolean);

    //替换
    "abc123_def456".replace(/(\d+).+?(\d+)/gi, (match, p1, p2) => {
        // match:123_def456; p1:123; p2:456
        console.log("match:%s; p1:%s; p2:%s", match, p1, p2); 
    })

    //==与=== 一般建议只用=== https://developer.mozilla.org/zh-CN/docs/Web/JavaScript/Equality_comparisons_and_sameness
    //在比较两件事情时，双等号将执行类型转换; 三等号将进行相同的比较，而不进行类型转换 (如果类型不同, 只是总会返回 false );  
}
//#endregion



//#region 正则表达式 用实例的还是字符串的，哪个方便用哪个
{
    //静态方法最好用了

    //split的写法上面有了
    var str1 = Regex.Split("aa@bb|cc#dd$ee,ff", "[@|#$,]");  //["aa","bb","cc","dd","ee","ff"]

    "a@b,c".replace(/[@,]/gi, "-"); //a-b-c

    "a111b222c".replace(/(\d+).(\d+)/gi, "$2-$1"); //a222-111c 后向引用啦。


    //匹配一个
    var match = [..."a111b222c".matchAll(/(\d+).(\d+)/gi)][0][2]; //222


    //匹配多组
    var matches = [..."a111b222c x333y444z  o555p666q".matchAll(/(\d+).(\d+)/gi)]; //匹配了3组
    for (const item of matches) {
        console.log(item[0],item[1],item[2])
    }


}
//#endregion




//#region 加解密,MD5,SHA,AES,RSA
{
    var src = "我是123の뭐라구";

    //encodeURI不破坏url结构
    encodeURI("http://我是123.com/呀")     //"http://%E6%88%91%E6%98%AF123.com/%E5%91%80"

    //encodeURIComponent会编码所有，一般用在url后面当参数用
    encodeURIComponent("http://我是123")   //"http%3A%2F%2F%E6%88%91%E6%98%AF123"

    //base64不好实现，可以来个假的：
    //atob及btoa只针对ASCII码，所以像中文之类的要先转码才能用
    btoa(encodeURIComponent("我是123の뭐라구")) //"JUU2JTg4JTkxJUU2JTk4JUFGMTIzJUUzJTgxJUFFJUVCJUFEJTkwJUVCJTlEJUJDJUVBJUI1JUFD"
    decodeURIComponent(atob("JUU2JTg4JTkxJUU2JTk4JUFGMTIzJUUzJTgxJUFFJUVCJUFEJTkwJUVCJTlEJUJDJUVBJUI1JUFD")) //"我是123の뭐라구"

    //其他加解密算法需要用第三方组件

}
//#endregion




//#region 序列化反序列化
{
    //fromJSON  
    //字符串必须使用双引号表示，不能使用单引号。
    //对象的键名必须放在双引号里面。
    //数组或对象最后一个成员的后面，不能加逗号。
    JSON.parse('{"a":1,"b":2}')

    //toJSON
    var obj = {a:1,b:new Date(),c:"3",e:true};
    JSON.stringify(obj) // "{}"
    JSON.stringify(obj,null, 2) // 缩进2个空格
    JSON.stringify(obj,null, '\t') // 按tab缩进

}
//#endregion
