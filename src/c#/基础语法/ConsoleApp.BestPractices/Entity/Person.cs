using System;
using System.Diagnostics;
using System.Runtime.Serialization;

namespace ConsoleApp.BestPractices
{
    // 单行注释

    /*
    多行注释，
    不过一般不这么用
    */


    /// <summary>
    /// 这个是人的类,文档注释官方：https://docs.microsoft.com/en-us/dotnet/csharp/programming-guide/xmldoc/xml-documentation-comments
    /// </summary>
    // DebuggerDisplay官方：https://docs.microsoft.com/en-us/dotnet/api/system.diagnostics.debuggerdisplayattribute
    [DebuggerDisplay("Id:{Id}  Name:{Name} Birthday:{Birthday} Height:{Height}")] // 调试用 如果在List里，默认看不到里面的属性，可以这么暴露出来,当然为了解析这个速度会比较慢
    public class Person
    {
        /// <summary>
        /// 这个是Id的注释，调用时可以看到
        /// </summary>
        public int Id { get; set; }
        public string Name { get; set; }
        public DateTime? Birthday { get; set; } //?表示可空，也就是可以为null
        public bool IsMan { get; set; }
        public decimal Height { get; set; } = 1.78m; //默认值
        public bool IsHuman => true; //只读，总是返回true
        public bool IsHumanOnlyReadDefaultTrue { get { return true; } } //只读，总是返回true
        public bool IsHumanOnlyReadDefaultFalse { get; } //只读
        public bool IsHumanOnlyPrivateSet { get; private set; } //仅内部可改
        public DateTime? Now => DateTime.Now; //每次调用返回当前时间
        public string Now2 => $"当前时间：{this.Now}";


        public override string ToString()
        {
            return base.ToString();
        }

        /// <summary>
        /// 这个是写字的方法
        /// 泛型的详细介绍：https://docs.microsoft.com/en-us/dotnet/csharp/programming-guide/generics/
        /// </summary>
        /// <param name="comparator">可以指定个比较器</param>
        /// <returns>返回值是xxx</returns>
        public bool IsHighter<T>(T comparator) where T : IComparable<decimal> //where T:XX这些是限定操作符 
        {
            comparator.CompareTo(Height);
            return true;
        }
    }


    [DataContract, Serializable]
    public class Person2
    {
        [DataMember]
        public int Id { get; set; }
        [DataMember]
        public string Name { get; set; }
        [DataMember]
        public DateTime Birthday { get; set; }
        [DataMember]
        public bool IsMan { get; set; }
    }
}
