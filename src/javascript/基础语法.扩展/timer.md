
### 常用以下两种写法
```js
setTimeout(() => {
    //your code
}, 1000);

setInterval(() => {
    //your code
}, 1000);

```




### 定时器 setTimeout | setInterval

```js

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

```




### sleep 用async..await实现

```js
// 在最外层可以简单的这么用
await new Promise(r => setTimeout(r, 2000));
```



```js
//在函数内部可以结合async来用

function sleep(ms) {
  return new Promise(resolve => setTimeout(resolve, ms));
}

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
```





### Promise的写法

```js
//可以一直then下去来延时
sleep(2000).then(p => {
    console.log("2秒后...")
}).then(p => sleep(3000)).then(p => {
    console.log("再3秒后...")
}).then(p => sleep(2000)).then(p => {
    console.log("再2秒后...")
});
```




### 同步xhr的写法

```js
function sleepByXHR(ms) {
    var xhr = new XMLHttpRequest();
    // `false` makes the request synchronous
    xhr.open('GET', 'http://httpstat.us/200?sleep=' + ms, false); 
    xhr.send();
}
var time = new Date();
sleepByXHR(2000)
console.log(`耗时：${new Date()-time} ms`);
```
