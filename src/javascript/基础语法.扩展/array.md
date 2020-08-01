--------------------------------------------------
### array

```javascript

//---------push,pop,shift,unshift---------
var list = []
var arr = ['a', 'b', 'c'];
arr.push(1,true,new Date()); //可以在后面添加一个或多个
arr.pop(); //从后面出队一个

arr.unshift("在前面添加一个")
arr.shift(); //从前面出队一个

[1,2,3].join();      //"1,2,3" 默认按,连接
[1,2,3].join("|");   //"1|2|3"


//--------------concat------------------
['hello'].concat(['world'])
// ["hello", "world"]

['hello'].concat(['world'], ['!'])
// ["hello", "world", "!"]

[].concat({a: 1}, {b: 2})
// [{ a: 1 }, { b: 2 }]

[2].concat({a: 1})
// [2, {a: 1}]

[1, 2, 3].concat(4, 5, 6)
// [1, 2, 3, 4, 5, 6]

//-----slice切片，不改变原先的，与string一样，直接搜索下slice-----
//slice()方法的一个重要应用，是将类似数组的对象转为真正的数组。
Array.prototype.slice.call({ 0: 'a', 1: 'b', length: 2 }) // ['a', 'b']
Array.prototype.slice.call(document.querySelectorAll("div"));
Array.prototype.slice.call(arguments);

//-----sort()是转成字符串后排序的-----
['d', 'c', 'b', 'a'].sort()
// ['a', 'b', 'c', 'd']

[4, 3, 2, 1].sort()
// [1, 2, 3, 4]

[11, 101].sort()
// [101, 11]  特别注意

//自定义的排序函数应该返回数值，否则不同的浏览器可能有不同的实现，不能保证结果都一致。
[
  { name: "张三", age: 30 },
  { name: "李四", age: 24 },
  { name: "王五", age: 28  }
].sort(function (o1, o2) {
  return o1.age - o2.age;
})
// [
//   { name: "李四", age: 24 },
//   { name: "王五", age: 28  },
//   { name: "张三", age: 30 }
// ]

//-----map()-----
//map方法将数组的所有成员依次传入参数函数，然后把每一次的执行结果组成一个新数组返回。
[1, 2, 3].map(p=>p*2) //[2, 4, 6]
[1, 2, 3].map((item,index,arr)=>item*index) //[0,2,6]

//不能直接{a:p},必须{return {a:p};}
[1, 2, 3].map(p=>{return {a:p, a2:p * 2};}) //[{a:1,a2:2},{a:2,a2:4},{a:3,a2:6}]
[{a:1},{a:4,b:2}].flatMap(p => {p.a *= 2; return p;}) //[{a:2},{a:8,b:2}]

//---forEach只是处理而不返回---
[1,2,3].forEach(p => console.log(p))

//特别要关注的是，for里可以写break;但forEach里不能，可以用return,但也只是跳过当前元素，后面的照样遍历
[1,2,3].forEach(p => {if(p===2) return;console.log(p);}) //会输出1,3

//----filter---
[1, 2, 3, 4, 5].filter(p => p > 2);
[1, 2, 3, 4, 5].filter((item, index) => index % 2 == 0)

//---- reduce 是"拆解"的意思，而不是"减少"----
//arr.reduce(callback( accumulator, currentValue[, index[, array]] )[, initialValue])
//accumulator默认为initialValue或者arr[0],然后currentValue就是下一个值了
[1, 2, 3].reduce((a, b) => a + b); //1+2+3=6
[1, 2, 3].reduce((a, b) => a - b); //1-2-3=-4
[1, 2, 3].reduce((a, b) => a + b, 100); //100+1+2+3=106
[1, 2, 3].reduce((a, b) => a - b, 100); //100-1-2-3=94


//-------------链式使用,有点C#的样子了+-----------
var users = [
    {name: 'tom', email: 'tom@example.com'},
    {name: 'peter', email: 'peter@example.com'}
  ];
  
users
  .map(p => p.email)
  .filter(p => /^t/.test(p))
  .map(p => {return {mail:p}}) //特殊
  .forEach(p => console.log(p));
// {mail: "tom@example.com"}
//可以再加个reduce的例子，全整上

[...new Array(10).keys()]
  .filter(p=>p%2==0)
  .map(p=>{return {a:p,b:p*p}})
  .sort(p=>p.b>20?1:-1)

//--------------------
//它的键名是按次序排列的一组整数,看起来像下标索引，也可以像所以一样使用arr[0],但其实他会被转成arr['0']
Object.keys(arr)  // ["0", "1", "2"] 





var a=[]
a[10000]=1
a.length //10001，length并不代表其长度
for(i in a){console.log(a[i])} //实际只会出来一个


for ...in无法保证顺序，for(var i=1;i<a.length;i++)可以保证

```

### 扩展运算符 & rest参数 ...

```js
function push(array, ...items) { //rest参数
  array.push(...items); //扩展运算符
}
let arr = [];
push(arr,1,2,3,4,...[5,6,7,8])
arr // [1, 2, 3, 4, 5, 6, 7, 8]

console.log(...[1, 2]) // 1 2
Math.max(...[14, 3, 77])

//复制数组
const a1 = [1, 2];
// 写法一
const a2 = [...a1];
// 写法二
const [...a2] = a1;

//合并数组
const arr1 = ['a', 'b'];
const arr2 = ['c'];
const arr3 = ['d', 'e'];
[...arr1, ...arr2, ...arr3]
```


### 遍历数组

```js
let strs = ['a', 'b', 'c'];

//比较老的写法
for (let i in strs) {
  console.log(strs[i]);
}


// keys(),values(),entries()
for (let [index, item] of strs.entries()) {
  console.log(index, item);
}
// 0 "a"
// 1 "b"



```


### 拉平
```js
//flat只处理一层，要处理多次要指定数字，或Infinity
[1, 2, [3, 4]].flat()
// [1, 2, 3, 4]

[1, 2, [3, [4, 5]]].flat()
// [1, 2, 3, [4, 5]]

[1, 2, [3, [4, 5]]].flat(2)
// [1, 2, 3, 4, 5]

[1, [2, [3]]].flat(Infinity)
// [1, 2, 3]


[2, 3, 4].flatMap(p => [p, p * 2])
// [2, 4, 3, 6, 4, 8]


["apple", "peach", "spork", "straw"].sort((a,b)=>a[3]>b[3]?1:-1)
```



