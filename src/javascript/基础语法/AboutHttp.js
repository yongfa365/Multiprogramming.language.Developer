//https://developer.mozilla.org/en-US/docs/Web/API/Fetch_API/Using_Fetch

//response.json();response.text()这些都是Promise,而不是最终的结果

fetch('https://jsonplaceholder.typicode.com/todos/1')
    .then(response => response.json())
    .then(json => console.log(json))

fetch('https://jsonplaceholder.typicode.com/todos/1', { mode: 'no-cors' })
    .then(response => response.json())
    .then(json => console.log(json))


//no-cors获取不到rs.headers，可以在网站或其console里请求自己的页面 来看此功能
fetch("https://www.baidu.com/")
    .then(rs => rs.headers.forEach(console.log));

//据说新版fetch自动集成gzip，所以一般不用关注，下面这种写法也无效，具体用到再说 https://stackoverflow.com/a/14739453
fetch("https://www.baidu.com/", { headers: { "Accept-Encoding": "gzip" } })
    .then(rs => rs.text())
    .then(body => console.log(body));





var body = { a: 1, b: 2 };

// Example POST method implementation:
async function postData(url = '', data = {}) {
    // Default options are marked with *
    const response = await fetch(url, {
        method: 'POST', // *GET, POST, PUT, DELETE, etc.
        mode: 'no-cors', // no-cors, *cors, same-origin
        cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
        credentials: 'same-origin', // include, *same-origin, omit
        headers: {
            'Content-Type': 'application/json'
            // 'Content-Type': 'application/x-www-form-urlencoded',
        },
        redirect: 'follow', // manual, *follow, error
        referrerPolicy: 'no-referrer', // no-referrer, *no-referrer-when-downgrade, origin, origin-when-cross-origin, same-origin, strict-origin, strict-origin-when-cross-origin, unsafe-url
        body: JSON.stringify(data) // body data type must match "Content-Type" header
    });
    return response.json(); // parses JSON response into native JavaScript objects
}

postData('https://jsonplaceholder.typicode.com/posts', { answer: 42 })
    .then(data => {
        console.log(data); // JSON data parsed by `data.json()` call
    });





// 比较老的写法，xhr
function ajax(url) {
    console.log("正在请求：", url);
    return new Promise(function (resolve, reject) {
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
      