### const及let声明的不再是顶级对象

```javascript
var a = 1;
// 如果在 Node 的 REPL 环境，可以写成 global.a
// 或者采用通用方法，写成 this.a
window.a // 1

let b = 1;
window.b // undefined



```

### var存在变量提升let不会，let是块级更适合for
```javascript
for (let i = 0; i < 3; i++) {
  let i = 'abc';
  console.log(i);
}

console.log(i) //Uncaught ReferenceError: i is not defined



for (var i = 0; i < 3; i++) {
  console.log(i);
}

console.log(i) //3  因为i变成全局变量了

```

### const & Object.freeze

```javascript
//冻结对象
const foo = Object.freeze({});

// 常规模式时，下面一行不起作用；
// 严格模式时，该行会报错
foo.prop = 123;

//冻结对象的属性
var constantize = (obj) => {
  Object.freeze(obj);
  Object.keys(obj).forEach( (key, i) => {
    if ( typeof obj[key] === 'object' ) {
      constantize( obj[key] );
    }
  });
};
```
