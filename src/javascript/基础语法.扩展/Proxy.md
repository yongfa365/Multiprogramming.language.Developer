
### Proxy基础语法
```js
var proxy = new Proxy(target, handler);
```


### 重定义属性的读取（get）和设置（set）行为
```js
/*jshint esversion: 6 */

var obj = new Proxy({}, {
  get(target, prop, receiver) {
    console.log(`getting ${prop}!`);
    return Reflect.get(target, prop, receiver);
  },
  set(target, prop, value, receiver) {
    console.log(`setting ${prop}!`);
    return Reflect.set(target, prop, value, receiver);
  },
  deleteProperty(target, prop) {
    console.log(`deleting ${prop}!`);
    return Reflect.deleteProperty(target, prop);
  },
});

obj.a = 1;
// setting a!

obj.a;
// getting a!
// 1
```



### 柳永法模拟java的动态代理写的，注意看注释
```js
/*jshint esversion: 6 */

let obj = {
  firstName: "永法",
  lastName: "柳",
  getFullName: function () {
    return this.lastName + this.firstName;
  }
};

// 原函数调用
obj.getFullName();

// var proxy = new Proxy(target, handler);
var proxy = new Proxy(obj, {
  // 代理读的操作
  get: function (target, prop) {
    if (prop === "getFullName") {
      // 代理原函数
      return () => target.firstName + " " + target.lastName;
    } else if (prop === "getChineseName") {
      // 代理不存在的函数
      return () => target.lastName + " " + target.firstName;
    } else if (prop in target) {
      // 代理原属性
      return target[prop];
    } else {
      // 代理不存在的属性
      return "我不存在";
    }
  },
  // 代理写的操作
  set: function (target, prop, value) {
    target[prop] = value;
  }
});

proxy.age = 37;

console.log(`
${proxy.getFullName()} | ${proxy.getChineseName()}
${proxy.firstName} | ${proxy.mediateName} | ${proxy.age}
`);

```



### 用于验证
```js
let validator = {
  set: function (target, prop, value) {
    if (prop === 'age') {
      if (!Number.isInteger(value)) {
        throw new TypeError('The age is not an integer');
      }
      if (value > 200) {
        throw new RangeError('The age seems invalid');
      }
    }

    // The default behavior to store the value
    target[prop] = value;

    // 表示成功
    return true;
  }
};

let person = new Proxy({}, validator);

person.age = 100;

console.log(person.age);
// 100

person.age = 'young';
// 抛出异常: Uncaught TypeError: The age is not an integer

person.age = 300;
// 抛出异常: Uncaught RangeError: The age seems invalid
```
