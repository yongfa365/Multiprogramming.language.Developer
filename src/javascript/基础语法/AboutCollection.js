//js没有list，但array跟list差不多，js的array是可变长的。

//#region array
{
    //演示：初始化的两种方法
    var lstInit01 = [];
    var lstInit02 = [ "1", "2", "3", "3" ];
    var lstInit03 = [
        {id:1, name:"A"},
        {id:2, name:"B"},
        {id:3, name:"B"},
    ];



    //Range 1..100
    [...Array(100).keys()]
    Array.from({length:100}, (e, i) => i+1);
    Array(100).fill(1).map((x, y) => x + y);

    //Range A..Z
    Array(26).fill().map((_, i) => String.fromCharCode('A'.charCodeAt(0) + i));

    //repeat object
    Array(10).fill(['a','b','c']).flat()
    Array(10).fill("ABC");
    Array(10).fill("ABCDE123456789".split("")).flat()
    Array(10).fill({id:1 ,name:"A"});


    //演示：添加元素的方法，单个的，array的
    lstInit01.push("A");
    lstInit01.push(1, true, new Date());
    lstInit01.push(...["4", "5", "6"])



    //演示：array的各种方法
    var lstData = [];

    for (var i = 0; i < 100; i++)
    {
        lstData.push({ Id: i, Name: `Name.${i}`, Birthday: new Date(Date.now() + i * 24 * 60 * 60 * 1000), Height: i / 3, IsHuman: i % 2 == 0 ? true : false  });
    }


    var lst01 = lstData.map(p => p.Id); //将Id组成新的List

    Math.max(...lst01);
    Math.min(...lst01);

    var lstTemp1 = lst01.max();
    var lstTemp2 = lstTemp.Min(); //IEnumerable可以多次使用，而Java的Stream只能用一次

    ////arr.reduce(callback( accumulator, currentValue[, index[, array]] )[, initialValue])
    var lst03 = lstData.reduce((a,b)=> a.Id > b.Id ? a : b); //max
    var lst03 = lstData.reduce((a,b)=> a.Id < b.Id ? a : b); //min
    var lst05 = lstData[0]; //first
    var lst06 = lstData.find(p => p.Id > 10); // first by 条件
    var lst07 = lstData[lstData.length - 1]; //最后一个
    var lst09 = lstData.some(p => p.IsHuman); //any
    var lst09 = lstData.every(p => p.IsHuman); //all


    var lst11 =  [...new Set([...[1,2,3], ...[2,3,4]])]; //union
    var lst12 = lstData.filter(p => p.Id > 80);
    var lst13 = lstData
        .filter(p => p.Birthday > new Date(Date.now() + 30 * 24 * 60 * 60 * 1000) && p.Id < 50) //筛选
        .sort((a,b)=>{a.Name.localeCompare(b.Name) || b.Id - a.Id}) //级联排序OrderBy..ThenByDescending https://stackoverflow.com/a/9175302/1879111
        .slice(1,1+10) //跳过,获取 skip..take
        .map(p =>{return {id: p.Id, userName: p.Name };}) //转为别的数组，用法比较特殊
        .reduce((map,obj) => (map[obj.id] = obj.userName,map),{})//转为字典 https://stackoverflow.com/a/26265095/1879111
        ;


    //演示：distinct https://stackoverflow.com/a/58429784/1879111
    var lst4Distinct = [
        { "name": "Joe", "age": 17 },
        { "name": "Bob", "age": 17 },
        { "name": "Carl", "age": 35 }
    ]

    var result = [...new Map(lst4Distinct.map(item => [item['age'], item])).values()];

    //演示：GroupBy https://stackoverflow.com/a/38327540/1879111
    function groupBy(list, keyGetter) {
        const map = new Map();
        list.forEach((item) => {
             const key = keyGetter(item);
             const collection = map.get(key);
             if (!collection) {
                 map.set(key, [item]);
             } else {
                 collection.push(item);
             }
        });
        return map;
    }
    
    // example usage
    
    const pets = [
        {type:"Dog", name:"Spot"},
        {type:"Cat", name:"Tiger"},
        {type:"Dog", name:"Rover"}, 
        {type:"Cat", name:"Leo"}
    ];
        
    groupBy(pets, pet => pet.type);

    groupBy(lstData.slice(0,10), p => {
        if (p.Height > 2)
        {
            return "Height>2";
        }
        else
        {
            return "Height<=2";
        }
    });


    //js没多线程，所以也不存在多线程的问题了
}
//#endregion








//#region Set
{
    var hs1 = new Set(["1", "2", "3", "3"]); //["1","2","3"]

    //没有原生的 自定义比较器

    var hs3 = new Set();
    hs3.add(1);
    hs3.add(2);
    hs3.add(3);

    var b1 = hs3.has(1); //true

    var hs4 = new Set([...hs3].filter(p => p > 1)); //set没有filter可以转成array

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
}
//#endregion


//#region Map
{
    let map = new Map([
        ['name', '张三'],
        ['title', 'Author']
    ]);

    map.set("1",2);
    map.get("1");
    map.has("1");

    for (let [key, value] of map) {
        console.log(key, value);
    }

    for (let [key, value] of map.entries()) {
        console.log(key, value);
    }
}
//#endregion


     


//#region Queue；Stack
{
    var stack = [];
    stack.push(2);       // stack is now [2]
    stack.push(5);       // stack is now [2, 5]
    var i = stack.pop(); // stack is now [2]
    alert(i);            // displays 5

    var queue = [];
    queue.push(2);         // queue is now [2]
    queue.push(5);         // queue is now [2, 5]
    var i = queue.shift(); // queue is now [5]
    alert(i);              // displays 2
}
//#endregion
