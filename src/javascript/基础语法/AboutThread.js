/*jshint esversion: 6 */

//js是单线程的，但可以实现一些异步的功能

//#region 模拟度假系统，同时请求机票与酒店，这个能看懂的话，基本就差不多了
{
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

}
//#endregion









//#region async函数：是一个能返回Promise对象的函数。当函数执行的时候，一旦遇到await就会先返回其后的Promise， 等到异步操作完成，再接着执行await后面的语句。
{
    // 如果只是拿到特定的结果，then下去就行
    fetch('https://httpstat.us/200?sleep=3000')
        .then(rs => rs.text())
        .then(body => console.log(body))
        .catch(console.error);


    // 如果要做更多的事情，得这么写了
    async function get(url) {
        //fetch会生成个Promise，同时因为在await后面，所以会立即返回，等到resolve后才会执行后面的语句
        let rs = await fetch(url);

        //rs.text()也是个promise,所以需要接着await
        let body = await rs.text();

        //所谓的 更多的事
        return `url:${url} ==> body:${body}`;
    }

    get('https://httpstat.us/200?sleep=3000')
        .then(result => console.log(result))
        .catch(console.error);

}
//#endregion












//#region 并发读取远程URL
{
    //看着是异步的，async,await都有，但其实还是一个一个来的，for后没有结果不能进入下一个
    async function getUrls(urls) {
        for (let url of urls) {
            let rs = await fetch(url);
            let body = await rs.text();
            console.log(`url:${url} ==> body:${body}`);
        }
    }
    
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
}
//#endregion







//#region Promise
{
    //一个正常的Promise要做的事情
    var commonPromise = new Promise((resolve, reject) => {
        let isSuccess = true; //mock
        if (isSuccess) {
            resolve(要返回的结果);
        } else {
            reject(); //或者不写这个，让他抛出异常就等同于调用了reject();
        }
    });

    //链式调用，每个then的结果都是个新的Promise
    Promise.resolve()
        .then(step1)
        .then(step2)
        .then(step3)
        .catch(console.error) //不管上面哪个step出错都会执行这个
        .finally(() => console.log("是死是活，已有定数"));


    function sleep(ms) {
        return new Promise((resolve, reject) => {
            //虽然setTimeout是异步的，但只要回调了relolve就行
            setTimeout(resolve, ms, 'done');
        });
    }

    sleep(100).then(result => {
        console.log(result);
    });
}
//#endregion





//#region Promise.all() Promise.resolve() Promise.reject()
{
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

}
//#endregion

