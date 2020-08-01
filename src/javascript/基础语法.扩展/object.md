### 遍历对象的几种方法
```js
var obj = {a: 1, b: 2, c: 3};

for (var i in obj) {
  console.log(`key:${i} value:${obj[i]}`);
}

for (let [key, value] of Object.entries(obj)) {
  console.log(`key:${key} value:${value}`);
}

Object.entries(obj).forEach(([key, value]) => {
  console.log(`key:${key} value:${value}`); 
});

Object.keys({a:1, b:2})
//["a", "b"]

Object.entries({a:1, b:2})
//[["a", 1],["b", 2]]

//将url参数转为object对象
Object.fromEntries(new URLSearchParams('?v&foo=bar&baz=qux'))
//{v: "", foo: "bar", baz: "qux"}

```








### 链判断运算符"?" 及 null判断运算符"??"
```js
var message = undefined
console.log(message?.body?.user?.firstName ?? 'default') //"default"
var message = {body:{user:{firstName:"LIU"}}};
console.log(message.body?.user?.firstName ?? 'default') //"LIU"

var code = response?.head?.code ?? 500;
var message = response?.head?.message ?? 'OK!';
```








### 函数返回多个值，几种写法
```js
//比较好的写法
(function getPoint() {
  const x = 1;
  const y = 10;
  return {x, y};
})() //{x: 1, y: 10} 对象   es6自动将变量转对象


//其他写法：
(function getPoint() {
  const x = 1;
  const y = 10;
  return {a:x, b:y};
})() // {a: 1, b: 10}       自己构造个对象


(function getPoint() {
  const x = 1;
  const y = 10;
  return [x, y];
})() //[1, 10]              数组
```





### object 创建对象|(添加|读取|删除)属性
```javascript
var obj = {x:1};

obj.x //1
obj["x"] //1

obj.y = 1 //添加个属性

delete obj.x //true 不存在也是 true，不能删除才是false
'y' in obj //true
'z' in obj //false
'toString' in obj // true


var obj = {};
obj.toString = () => "hello"; //重写toString()
obj.toString() //"hello"


//设置属性的各种参数，还可以设置set,get
var obj = Object.defineProperty({}, 'p', {
  value: 123,
  writable: false,
  enumerable: true,
  configurable: false
});

Object.getOwnPropertyDescriptor(obj, 'p')

```





