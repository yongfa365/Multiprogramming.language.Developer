import sys
import uuid
from decimal import *
from random import *

#region Numeric Types 只用这三种 int, float, complex
#所有的NumericTypes都支持+,-,*,/,//取整,%等操作，decimal

#python只有一个整数类型，长度不限，代表了int,long,biginteger,Int32,Int64
intValue = 9223372036854775807 ** 2014

#代表了float及double,查看其精度的方法：sys.float_info
floatValue = 1.1  

#bool是integer的子类
boolTrue = True
boolFalse = False

#没有char类型

#decimal对应java里的BigDecimal，并且有重载运算符，所以更方便。但与C#里的写法1.234m比,还是有差距
decimalValue = Decimal("1.1")

#0.09999999999999998 float 精度不够
double_bad = 1.0 - 9 * 0.1

#decimal精度够，企业级应用都用这个，而不用float,double  https://docs.python.org/3.7/library/decimal.html
decimal_good = Decimal(1.0) - 9 * Decimal("0.1")
decimal_all = Decimal(1.0) + Decimal(0.1) * 2 // 3 #不能乘除float

decimal_compare = Decimal(1.0) > Decimal(3.0)

#生成一个GUID,如："7e0d3fc3-5447-4cf2-accd-366e3ade0973" https://docs.python.org/3.7/library/uuid.html#uuid.UUID
guid=uuid.uuid1()
guid2=uuid.UUID("7e0d3fc3-5447-4cf2-accd-366e3ade0973")

#获取随机数 https://docs.python.org/3.7/library/random.html#examples-and-recipes
rnd = randrange(1,10000)

#TODO：以上写了两个小时了

#endregion