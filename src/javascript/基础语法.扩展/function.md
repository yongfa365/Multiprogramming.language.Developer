--------------------------------------------------------
### function


```javascript
function print(s) {
  console.log(s);
}

var print = function(s) {
  console.log(s);
};

//lambda到es6才有：
var print = s => console.log(s);

var add = (a,b) => a + b;

//参数可以是函数
var add = function(x,y){return x+y;}
var sub = function(x,y){return x-y;}
var calc = function(fun,x,y){return fun(x,y);}
calc(add,1,2) //3
calc(sub,1,2) //-1

//立即调用的函数表达式,最后的分号是必须的，要用()括起来，让解析器知道他是表达式，而不是函数
(function(){ /* code */ }());
// 或者
(function(){ /* code */ })();

//eval不安全，一般也是用它来转json的，应该用JSON.parse替代eval
JSON.parse('{"a":1,"b":2}')

```


### 模块（Module）
我们可以使用函数和闭包来构造模块。模块是一个提供接口，却隐藏状态与实现的函数或对象。

举例我们想要给String增加一个deentityify方法。它的任务是寻找字符串中的HTML字符实体并把它们替换为相应的字符。这就需要在对象中保存字符实体的名字和它们相应的字符。那我们在哪里保存这个对象呢，不在全局变量，也不在函数的内部。理想的方式是把它放入一个闭包：
```javascript
// 将：&lt;a href=&quot;123&quot; /&gt; 替换为： <a href="123" />
String.prototype.deentityify = function () {
    //放在函数内部的，不会成为全局变量，注意：只是模块初始化的时候运行一次。
    var dict = {
        quot: '"',
        lt: '<',
        gt: '>'
    };

    //返回deentityify方法
    return function () {
        //这才是denntityify方法。它调用字符串的replace方法。
        return this.replace(/&([^&;]+);/g, function (match, p1) {
            var result = dict[p1];
            return typeof result === 'string' ? result : match;
        });
    };
}();

var result = "&lt;a href=&quot;123&quot; /&gt;".deentityify();
console.log(result);
```

最后一行我们用（）运算法立刻调用我们刚刚构造出来的函数。这个调用所创建并返回的方法才是我们需要的deentityify方法。

模块模式利用函数作用域和闭包来创建被绑定对象和私有成员的关联，在这个例子中，只有deentityify方法有权访问字符实体表这个数据对象。

模块模式的一般形式是：一个定义了私有变量和函数的函数；利用闭包创建可以访问私有变量和函数的特权函数；最后返回这个特权函数，或者把它们保存到一个可以访问的地方。

-----------------------
### es5时函数的默认值
```js
function log(x, y) {
  y = y || 'World';
  console.log(x, y);
}

log('Hello') // Hello World
log('Hello', 'China') // Hello China
log('Hello', '') // Hello World        参数y等于空字符，结果被改为默认值

//y = y || 'World';看似简单实则问题多多，如：y本身就是false或空字符，所以需要复杂点：
if (typeof y === 'undefined') {
  y = 'World';
}
```


### es6时函数的默认值
```js
//一般默认参数
function Point(x = 0, y = 0) {
  this.x = x;
  this.y = y;
}

const p = new Point();
p // { x: 0, y: 0 }


//默认参数可以使用变量,可以调用方法
let x = 99;
function foo(p = x + 1, y = p, z = throwIfMissing()) {
  console.log(p);
}
function throwIfMissing() {
  throw new Error('Missing parameter');
}
```


### es6时函数的默认值，考考你，两个默认值的区别
```js
// 写法一
function m1({x = 0, y = 0} = {}) {
  return [x, y];
}

// 写法二
function m2({x, y} = { x: 0, y: 0 }) {
  return [x, y];
}



// 函数没有参数的情况
m1() // [0, 0]
m2() // [0, 0]

// x 和 y 都有值的情况
m1({x: 3, y: 8}) // [3, 8]
m2({x: 3, y: 8}) // [3, 8]

// x 有值，y 无值的情况
m1({x: 3}) // [3, 0]
m2({x: 3}) // [3, undefined]

// x 和 y 都无值的情况
m1({}) // [0, 0];
m2({}) // [undefined, undefined]

m1({z: 3}) // [0, 0]
m2({z: 3}) // [undefined, undefined]
```


### rest参数
```js
function add(...values) {
  let sum = 0;

  for (var item of values) {
    sum += item;
  }

  return sum;
}

add(2, 5, 3) // 10


const headAndTail = (head, ...tail) => [head, tail];
headAndTail(1, 2, 3, 4, 5) // [1,[2,3,4,5]]

```



es6 object函数简化写法
```js
// ES5
var obj = {
    name: "Nicholas",
    sayName: function() {
        console.log(this.name);
    }
};

// ES6
var obj = {
    name: "Nicholas",
    sayName() {
        console.log(this.name);
    }
};
```






