### async函数：是一个能返回Promise对象的函数
async函数返回一个 Promise 对象。
当函数执行的时候，一旦遇到await就会先返回其后的Promise，
等到异步操作完成，再接着执行await后面的语句。

```js
// 只是拿到特定的结果，then下去就行
fetch('https://httpstat.us/200?sleep=3000')
  .then(rs => rs.text())
  .then(body => console.log(body))
  .catch(console.error);
  

// 如果要做更多的事情，得这么写了
async function get(url) {
  //fetch会生成个Promise,因为在await后面，所以会立即返回，等到resolve后才会执行后面的语句
  let rs = await fetch(url);
  
  //rs.text()也是个promise,所以需要接着await
  let body = await rs.text();
  
  //所谓的 更多的事
  return `url:${url} ==> body:${body}`;
}

get('https://httpstat.us/200?sleep=3000')
  .then(result => console.log(result))
  .catch(console.error);

```




### await 命令
正常情况下，await命令后面是一个 Promise 对象，会当做函数的返回值。 如果不是 Promise 对象，则会自动包装成Promise：Promise.resolve(123)。
```js
async function f() {
  return await 123; // 等同于return 123;
}

f() // Promise {<resolved>: 123}
```


### 并发读取远程URL

```js
//看着是异步的，async,await都有，但其实还是一个一个来的，for后没有结果不能进入下一个
async function getUrls(urls) {
  for (let url of urls) {
    let rs = await fetch(url);
    let body = await rs.text();
    console.log(`url:${url} ==> body:${body}`);
  }
}
```


```js
//这才是真正的并发，因为map后每个都返回的是Promise，就能直接进入下一个了。
async function getUrls(urls) {
  // 并发读取远程URL
  let promises = urls.map(async (url) => {
    let rs = await fetch(url);
    let body = await rs.text();
    return `url:${url} ==> body:${body}`;
  });

  // 按次序输出
  for (let promise of promises) {
    console.log(await promise);
  }
}

let urls = [
  'https://httpstat.us/200?sleep=3000',
  'https://httpstat.us/400?sleep=1000',
  'https://httpstat.us/502?sleep=4000',
  'https://httpstat.us/403?sleep=2000',
];

getUrls(urls);
```




### 

```js
/*jshint esversion: 6 */

// async await 是怎么实现等待的？
// 不是说他是但线程的吗？
// 他内部怎么实现的？既然可以实现为什么不能在语言层面上直接实现等待？
// http请求就是等待的，他又是如何实现的？

```




### sleep的例子

```js
function sleep(ms) {
  return new Promise(resolve => {
    setTimeout(resolve, ms);
  })
}

// 用法
async function demo() {
  for(let i = 1; i <= 5; i++) {
    console.log(i);
    await sleep(1000);
  }
}

demo();
```






