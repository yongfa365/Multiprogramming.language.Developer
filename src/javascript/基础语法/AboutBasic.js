
//js的数字类型只有两个：Number及BigInt
//Number相当于C#或java的double，而double精确度不够，一般业务项目都不用。但js与后端的C#或java交互时，可能接收到后端的类似long或Int64之类的较大的数字，如9223372036854775807，这个值在js里会被变成9223372036854776000，最后几位变成0了，这个问题在前后端交互时要特别小心，需要转成字符串。

//安全integer的概念是，安全指的是精确表示整数并正确比较它们的能力，超出这个范围的，比较大小可能不准确
var numMax = Number.MAX_SAFE_INTEGER; //9007199254740991
var numMin = Number.MIN_SAFE_INTEGER; //-9007199254740991

var booltrue = true;
var boolfalse = false;

//没有char类型，单引号或双引号都是字符串，所以也无法做到类似C#的：'A' > 26;，可以变相实现：
'A'.charCodeAt() > 26

//BigInt
var b_1 = BigInt("123".repeat(100));
var b_2 = BigInt(90071992547409919007199254740991); //这种写法后面一堆数会变成0
var b_3 = BigInt(90071992547409919007199254740991n); //注意后面有个n，这个就正常了
var b_4 = 90071992547409919007199254740991n; //也是可以的
var b_5 = 90071992547409919007199254740991n / 2n * 3n + 1n - 3n; //如果是BigInt的计算数字都要加个n,不然语法错误


//js没有decimal类型，可以使用第三方库实现

//0.09999999999999998  double 精度不够
var double_bad = 1.0 - 9 * 0.1;

//生成一个GUID,如："7e0d3fc3-5447-4cf2-accd-366e3ade0973"，js没有原生功能，可以自己写个
//https://stackoverflow.com/questions/105034/how-to-create-guid-uuid
function guid() {
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
        var r = Math.random() * 16 | 0;
        var v = c == 'x' ? r : (r & 0x3 | 0x8);
        return v.toString(16);
    });
}

//获取随机数
Math.random();//结果是小数

//指定范围内的随机整数
function random(min, max) {
    return Math.round(Math.random() * (max - min)) + min;
}

//js的数组不是真正的数组，因为他可变长度，更像是List,所以这里只是简单介绍下，详情看相关文件
var strs = [];
var strs2 = ["A","B"];

//三元运算符
var isTrue = 1 == 2 ? "True" : "False";

//可空类型的用法
var a123 = null;
var a3 = a123 ?? 0; //返回第一个不为null的或者返回null

var response = null;
var code = response?.head?.code ?? 500; //500

// ||也可以实现类似效果：
false || 0 || '' || 4 || 'foo' || true
// 4

false || 0 || ''
// ''


//元组 Tuple太容易了
var t1 = ["1", 1, Date.now()]; //可以这么用：t1[1], t1[2],t1[3]
var t_1 = t1[1]+t1[2]+t1[3];

var t5 = {FirstName : "永法", LastName : "柳"};
var t_5 = t5.FirstName;
var t_51 = t5["FirstName"];

//变量赋值
var [FirstName,  LastName] = ["永法","柳"];


//匿名类,Object
var t5 = {FirstName : "永法", LastName : "柳"};

// Action与Func与C#的一样

//Action
function NoErrorInvoke(action){
    try {
        action();
    } catch (error) {
        //console.error(error);
    }
}

var actiontestInt = 1;
NoErrorInvoke(() => {
    actiontestInt++;
    throw Error("xxxx");
});
actiontestInt

//Func
var add = (x, y) => x + y;
var add2 =  (x, y) => {
    if (x > y) {
        return x + y;
    }
    return x - y;
};

add(1, 2); //3




    //dynamic 的更多强大之处及他使类型变弱后带来的问题，就自己去体验吧。
    dynamic dd = DateTime.Now;
    var dd_1 = dd.Year; //动态类型的，所以.Year不会有智能提示，要手敲出来

    dd = "123";
    var dd_2 = dd[2].ToString(); //动态类型的，所以[2]及ToString()不会有智能提示，要手敲出来.
}
 # endregion

 # region if else
    do
        while for foreach switch {
            if (1 + 1 == 2) {
                //code
            } else if (2 > 1) {
                //code
            } else {
                //code
            }

            var i = 10;
            while (i-- > 0) {
                //code
            }

            do {
                //code
            } while (i++ > 10);

            for (int j = 0; j < 10; j++) {
                //code
            }

            var items = new int[]{
                1,
                2,
                3
            };
            foreach(var item in items) {
                Console.WriteLine(item);
            }

            var ptype = ProductType.Flight;
            var ptypes = ProductType.Flight | ProductType.Bus | ProductType.Hotel;
            var ptypeHasFlag = ptype.HasFlag(ProductType.Flight); //true

            //输入switch后按tab,再输入ptype，再按enter，就自动生成一下内容了
            switch (ptype) {
            case ProductType.Default:
                break;
            case ProductType.Hotel:
                break;
            case ProductType.Flight:
                break;
            case ProductType.Bus:
                break;
            case ProductType.FlightHotel:
                break;
            case ProductType.BusHotel:
                break;
            default:
                break;
            }
        }
     # endregion

     # region 数学方法 因业务系统只用decimail所以只关注decimail的，其他的也一样的用法 {

        var du = Math.Ceiling(123.456m); //天花板
        var dd = Math.Floor(123.456m); //地板

        // 四舍五入
        var dd1 = Math.Round(1.44m, 1, MidpointRounding.AwayFromZero);
        var dd2 = Math.Round(1.45m, 1, MidpointRounding.AwayFromZero);

        // 四舍六入五成双,了解下就行
        var d1 = Math.Round(1.44m, 1); //1.4  被修约的数字<=4时，该数字舍去
        var d2 = Math.Round(1.45m, 1); //1.4  被修约的数字等于5时，要看5前面的数字，若是奇数则进位，若是偶数则将5舍掉，即修约后末尾数字都成为偶数
        var d4 = Math.Round(1.451m, 1); //1.5 被修约的数字等于5时，若5的后面还有不为“0”的任何数，则此时无论5的前面是奇数还是偶数，均应进位。
        var d3 = Math.Round(1.75m, 1); //1.8  被修约的数字等于5时，要看5前面的数字，若是奇数则进位，若是偶数则将5舍掉，即修约后末尾数字都成为偶数
        var d6 = Math.Round(1.46m, 1); //1.5  被修约的数字>=6时，则进位
    }
 # endregion

InfinateParam(110, 120, 911);
InfinateParam(new[]{
    111,
    222,
    333
});
}

public static void InfinateParam(params int[]args) {
    foreach(var item in args) {
        Console.WriteLine(item);
    }
}

}

class DynamicDemoClass {
    static dynamic field;
    dynamic prop {
        get;
        set;
    }

    public dynamic exampleMethod(dynamic d) {
        dynamic local = "Local variable";
        int two = 2;

        if (d is int) {
            return local;
        } else {
            return two;
        }
    }
}
}
