类似于对象，也是键值对的集合，但是“键”的范围不限于字符串

目前感觉就是个鸡肋

```js
let map = new Map([
  ['name', '张三'],
  ['title', 'Author']
]);

map.size // 2
map.has('name') // true
map.get('name') // "张三"
map.has('title') // true
map.get('title') // "Author"
```
