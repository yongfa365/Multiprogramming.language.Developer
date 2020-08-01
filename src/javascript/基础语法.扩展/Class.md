### 

```js
class Point {
  constructor(x, y) {
    this.x = x;
    this.y = y;
  }

  toString() {
    return '(' + this.x + ', ' + this.y + ')';
  }
}

//new Point(123,456).toString()
```




### 实例对象class,new
```javascript
//构造函数
var Vehicle = function (p) {
  'use strict'; //防止不带new调用而导致price变成全局变量
  this.price = p;
};

var bus = new Vehicle(500);
bus.price


//当实例对象本身没有某个属性或方法的时候，它会到原型对象去寻找该属性或方法。这就是原型对象的特殊之处。
function Animal(name) {
  this.name = name;
}
Animal.prototype.color = 'white';

var cat1 = new Animal('大毛');
var cat2 = new Animal('二毛');

cat1.color // "white"
cat2.color // "white"

cat1.color = 'black';
cat1.color // 'black'
cat2.color // 'white'
Animal.prototype.color // 'white';

//总结一下，原型对象的作用，就是定义所有实例对象共享的属性和方法。这也是它被称为原型对象的原因，而实例对象可以视作从原型对象衍生出来的子对象。
//原型链，JavaScript 规定，所有对象都有自己的原型对象（prototype）。一方面，任何一个对象，都可以充当其他对象的原型；另一方面，由于原型对象也是对象，所以它也有自己的原型。因此，就会形成一个“原型链”（prototype chain）：对象到原型，再到原型的原型……最上层是Object

//instanceof检查整个原型链，因此同一个实例对象，可能会对多个构造函数都返回true。
var d = new Date();
d instanceof Date // true
d instanceof Object // true

//模块需要使用 jQuery 库和 YUI 库，就把这两个库当作参数输入module1
var module1 = (function ($, YAHOO) {
　//...
})(jQuery, YAHOO);

```





### 

```js

```




### 

```js

```




### 

```js

```

