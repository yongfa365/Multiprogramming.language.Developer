### Number
```javascript
(10).toString() // "10"
(10).toString(2) // "1010"
10.5.toString()
(123456789).toLocaleString('zh-Hans-CN-u-nu-hanidec') //"一二三,四五六,七八九"
(123).toLocaleString('en-US', { style: 'currency', currency: 'HKD' }) //"HK$123.00"

Number.prototype.iterate = function () {
  var result = [];
  for (var i = 0; i <= this; i++) {
    result.push(i);
  }
  return result;
};

(8).iterate() // [0, 1, 2, 3, 4, 5, 6, 7, 8]

```