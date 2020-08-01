### 

```js
// 例一
const set = new Set([1, 2, 3, 4, 4]);
[...set]
// [1, 2, 3, 4]

// 去除数组的重复成员
[...new Set(array)]

//去除字符串里面的重复字符
[...new Set('ababbc')].join('')
// "abc"

```

### 判断是否包括一个键上面，Object结构和Set结构的写法不同

```js
// 对象的写法
let obj = {
  'width': 1,
  'height': 1
};

if (obj['aaaaa']) {
  console.log("has name")
}

// Set的写法
let set = new Set();

set.add('width');
set.add('height');

if (set.has('aaaaa')) {
  console.log("has name")
}
```


### 遍历

```js
let set = new Set(['red', 'green', 'blue']);

//---------keys()---------
for (let item of set.keys()) {
  console.log(item);
}
// red
// green
// blue

//---------values()---------
for (let item of set.values()) {
  console.log(item);
}
// red
// green
// blue

//---------entries()---------
for (let item of set.entries()) {
  console.log(item);
}
// ["red", "red"]
// ["green", "green"]
// ["blue", "blue"]

//---------set直接可遍历---------
for (let x of set) {
  console.log(x);
}
// red
// green
// blue

//---------forEach()---------
set.forEach(p => console.log(p))
// red
// green
// blue

```

