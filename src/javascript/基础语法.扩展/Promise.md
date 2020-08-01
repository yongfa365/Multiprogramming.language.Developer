### 这个能看懂的话，后面基本不用看了
```js
console.time("查询酒店+机票总耗时");

//实际中要自行处理httpstatus != 200的场景
var hoteltask = fetch('https://httpstat.us/200?sleep=10000')
.then(rs => rs.text())
.then(body =>  [{hotelId:1,name:"酒店1"},{hotelId:100,name:"酒店100" + body}]);

var flighttask = fetch('https://httpstat.us/200?sleep=8000')
.then(rs => rs.text())
.then(body => [{flightId:1,airline:"深航"},{flightId:2,airline:"东航" + body}]);

//等所有结果回来
Promise.all([hoteltask, flighttask])
    .then(([hotels, flights]) => {
        console.log("获取到 酒店:", hotels.map(p => p.hotelId).join(), "机票：", flights.map(p => p.flightId).join());
        console.log("最终时间应该小于酒店+机票的时间，https连接有时慢，可以多刷新几次看下。")
        console.timeEnd("查询酒店+机票总耗时");
    })
    .catch(ex => console.log(ex))
    .finally(() => console.log("是死是活，已有定数"));
```


### Promise


```javascript
//一个正常的Promise要做的事情
var commonPromise = new Promise((resolve,reject) =>{
    let isSuccess = true; //mock
    if(isSuccess){
        resolve(要返回的结果);
    }else{
        reject(); //或者不写这个，让他抛出异常就等同于调用了reject();
    }
})

//链式调用，每个then的结果都是个新的Promise
Promise.resolve()
  .then(step1)
  .then(step2)
  .then(step3)
  .catch(console.error) //不管上面哪个step出错都会执行这个
  .finally(() => console.log("是死是活，已有定数，手工"));


function sleep(ms) {
  return new Promise((resolve, reject) => {
    //虽然setTimeout是异步的，但只要回调了relolve就行
    setTimeout(resolve, ms, 'done');
  });
}

sleep(100).then(result => {
  console.log(result);
});


```



### Promise.all() Promise.resolve()  Promise.reject()
虽然setTimeout是异步的，但只要回调了relolve就行
```javascript
let p1 = Promise.resolve(123);
let p2 = Promise.resolve('hello');
let p3 = Promise.resolve(true);
let p4 = Promise.reject('error');

// [123, "hello", true]
Promise.all([p1, p2, p3]).then(result => {
  console.log(result);
});

// error
Promise.all([p1, p2, p4]).then(result => {
  console.log(result);
}).catch(result => {
  console.log(result);
});


//会自动包装，但前提是他已经有最终结果了，如果传的是个函数，则需要函数执行完的结果他才包装
// this will be counted as if the iterable passed is empty, so it gets fulfilled
var p = Promise.all([1,2,3]);
// this will be counted as if the iterable passed contains only the resolved promise with value "444", so it gets fulfilled
var p2 = Promise.all([1,2,3, Promise.resolve(444)]);
// this will be counted as if the iterable passed contains only the rejected promise with value "555", so it gets rejected
var p3 = Promise.all([1,2,3, Promise.reject(555)]);
```



### Promise.race
虽然setTimeout是异步的，但只要回调了relolve就行
```javascript


var p1 = new Promise(function(resolve, reject) { 
    setTimeout(resolve, 500, "one"); 
});

var p2 = new Promise(function(resolve, reject) { 
    setTimeout(resolve, 100, "two"); 
});

Promise.race([p1, p2]).then(function(value) {
  console.log(value); // "two"
  // 两个都完成，但 p2 更快
});

```







### 等待多个结果，研究了6个小时，脑袋都蒙了
一开始用“死循环”或“xhr同步调用” 实现sleep，但都无法做到同时发起两个请求，因为他们都是顺序的。

**Promise本没有实现异步功能，他只是各种封装**

```javascript
function ajax(url) {
  console.log("正在请求：", url);
  return new Promise(function(resolve, reject) {
    var xhr = new XMLHttpRequest();
    //没onload之前这个Promise已经返回了，只是是pending状态，只有调用resolve后才算完成
    xhr.onload = () => {
        console.log("请求完成，Response:", xhr.responseText);
        resolve(xhr.responseText);
    };
    xhr.onerror = reject;
    xhr.open('GET', url);
    xhr.send();
  });
}

await ajax("https://httpstat.us/200?预热下https");

console.time("查询酒店+机票总耗时");

//实际中要自行处理httpstatus != 200的场景

var hoteltask = ajax('https://httpstat.us/200?sleep=4000&获取酒店列表')
.then(rs =>  [{hotelId:1,name:"酒店1"},{hotelId:100,name:"酒店100"+rs}]);

var flighttask = ajax('https://httpstat.us/200?sleep=8000&获取机票列表')
.then(rs => [{flightId:1,airline:"深航"},{flightId:2,airline:"东航"+rs}]);

var doSomeThing_AfterGetAllResource = function (hotels,flights){
  console.log("获取到 酒店:", hotels.map(p => p.hotelId).join(), "机票：", flights.map(p => p.flightId).join());
  console.log("最终时间应该小于酒店+机票的时间，https连接有时慢，可以多刷新几次看下。")
  console.timeEnd("查询酒店+机票总耗时");
};

//等所有结果回来
Promise.all([hoteltask, flighttask])
  .then(([hotels, flights]) => doSomeThing_AfterGetAllResource(hotels, flights))
  .catch(ex => console.log(ex))
  .finally(() => console.log("是死是活，已有定数，收工"));
```


