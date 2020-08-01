### 字符串函数
```js

"abc"[2]; \\"c"
String(true) // "true"
String(5) // "5"
'abc'.length // 3
'我'.length // 1
'a'.concat('b','c',1,2,3) //abc123
'\r\n\t  hello world  \r\n　\t'.trim()　//中文空格也会trim调
'hello world'.indexOf('o') // 4
'hello world'.includes('world') // true
'hello world'.lastIndexOf('o') // 7
'Hello World'.toLowerCase() // "hello world"
'Hello World'.toUpperCase() // "HELLO WORLD"


//---String.prototype.substring()不推荐用-------
//---String.prototype.slice(开始位置，结束位置(不含))-------
'JavaScript'.slice(0, 4) // "Java"
'JavaScript'.slice(4) // "Script" 不写第二个就是到结尾
'JavaScript'.slice(-6) // "Script"
'JavaScript'.slice(0, -6) // "Java"
'JavaScript'.slice(-2, -1) // "p"
'JavaScript'.slice(2, 1) // "" 区间不存在则返回空

//---String.prototype.substr(开始位置，截取多长)---
'JavaScript'.substr(4, 6) // "Script"
'JavaScript'.substr(-6) // "Script"


let s = 'Hello world!';

s.startsWith('Hello') // true
s.endsWith('!') // true
s.includes('o') // true

'hello'.repeat(2) // "hellohello"
'x'.padEnd(10) //"x         "
'12'.padStart(10, '0') // "0000000012"
'12'.padStart(10, 'YYYY-MM-DD') // "YYYY-MM-12"
'09-12'.padStart(10, 'YYYY-MM-DD') // "YYYY-09-12"

const s = '  abc  ';

s.trim() // "abc"
s.trimStart() // "abc  "
s.trimEnd() // "  abc"

```








### 模板替换 多行文本 都是要用`的，单引号及双引号不行
```js
var html = `
  <div>
    <span>Some HTML here</span>
  </div>
`;



let hotel={hotelID:12345,hotelName:"如家"};


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

```






### 编码转化

```js
//名称或值得用双引号，而不能用单引号
JSON.parse('{"a":1,"b":2}') 


//encodeURI不破坏url结构
encodeURI("http://我是123.com/呀")
"http://%E6%88%91%E6%98%AF123.com/%E5%91%80"

//encodeURIComponent会编码所有，一般用在url后面当参数用
encodeURIComponent("http://我是123")
"http%3A%2F%2F%E6%88%91%E6%98%AF123"

//atob及btoa只针对ASCII码，所以像中文之类的要先转码才能用
btoa(encodeURIComponent("http://我是123"))
"aHR0cCUzQSUyRiUyRiVFNiU4OCU5MSVFNiU5OCVBRjEyMw=="

atob("aHR0cCUzQSUyRiUyRiVFNiU4OCU5MSVFNiU5OCVBRjEyMw==")
"http%3A%2F%2F%E6%88%91%E6%98%AF123"

decodeURIComponent(atob("aHR0cCUzQSUyRiUyRiVFNiU4OCU5MSVFNiU5OCVBRjEyMw=="))
"http://我是123"

```

