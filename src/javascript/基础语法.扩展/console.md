### console.log|error|warn|debug|clear
```js

console.clear()

//可以用占位符，也可以连着用
console.log('%s + %s', 1, '1', "= 3"); //1 + 1  = 3
console.warn({a:1}); //有警告标示
console.error({a:1}); //有错误标示
console.debug({a:1}); //默认不显示，要打开设置


//使用%c占位符时，对应的参数必须是 CSS 代码，用来对输出内容进行 CSS 渲染。
console.log(
  '%cThis text is styled!',
  'color: red; background: yellow; font-size: 24px;'
)


// 开F12后，debugger才工作，否则会被忽略
for(var i = 0; i < 5; i++){
  console.log(i);
  if (i === 2) debugger;
}

console.assert(false, '判断条件不成立')

//想要copy某个变量的值放到粘贴板
copy(xxx)
```



### 分组
```js
console.group('一级分组');
console.log('一级分组的内容');

console.group('二级分组');
console.log('二级分组的内容');

console.groupEnd(); // 二级分组结束
console.groupEnd(); // 一级分组结束

console.groupCollapsed('Fetching Data');
console.log('Request Sent');
console.error('Error: Server not responding (500)');
console.groupEnd();
```



### 计时功能
```js
console.time();
await new Promise(p => setTimeout(p, 2000));
console.timeEnd();

```



### table
```js
var languages = [
  { name: "JavaScript", fileExtension: ".js" },
  { name: "TypeScript", fileExtension: ".ts" },
  { name: "CoffeeScript", fileExtension: ".coffee" }
];

console.table(languages);

var languages = {
  csharp: { name: "C#", paradigm: "object-oriented" },
  fsharp: { name: "F#", paradigm: "functional" }
};

console.table(languages);
```




### console的输出加上时间
```js
// 方法一：
// 所有console的输出上都加上时间前缀,但这个是有bug的，不会解析%s了
['log', 'info', 'warn', 'error', 'debug'].forEach(function(method) {
  console[method] = console[method].bind(
    console,
    new Date().toISOString()
  );
});


// 方法二：
// F12-->ctl+shift+p-->输入time会看到Show timestamps

```



### 用条件断点来打印日志
与其在源码的不同地方去添加 console.log / console.table / console.time 等等，不如直接使用条件判断来将它们 “连接” 到 Source 面板中
![image](https://user-gold-cdn.xitu.io/2018/12/17/167b955a1f0311fc?imageslim)