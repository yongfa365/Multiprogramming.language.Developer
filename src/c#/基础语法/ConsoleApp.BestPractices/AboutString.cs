using System;
using System.Collections.Generic;
using System.Security.Cryptography;
using System.Text;
using System.Text.RegularExpressions;

namespace ConsoleApp.BestPractices
{
    ///<summary>
    ///https://docs.microsoft.com/en-us/dotnet/api/system.string
    ///</summary>
    public class AboutString
    {
        public static void RunDemo()
        {
            #region 构造个string
            {
                //平时使用最多的
                var strCommon = $@"now:{DateTime.Now},path:c:\a\b";

                var strEmpty = string.Empty;

                var strNormal = "now";

                var strPlus = 1 + "2" + DateTime.Now + true + "3"; //如果不是string的会使用object.ToString()后连接在一起。

                var strConcat = string.Concat("1", true, DateTime.Now, 3.3333m); //1True2018/11/22 11:02:083.3333

                var strFormat = string.Format("{0} {1} {0} {2:o}", "hello", 123, DateTime.Now); //hello 123 hello 2018-11-22T11:04:58.3648022+08:00

                //AA@BBB之所以只有2个A是因为Pad的数字是最终总的长度,一般测试性能时会用到
                var strPad = "@".PadLeft(3, 'A').PadRight(6, 'B');

                //大量字符串操作时+性能很差，要换成StringBuilder
                var sb = new StringBuilder();
                sb.Append(1).Append("2").Append(true).Append(DateTime.Now);
                sb.AppendLine(DateTime.Now.ToString());
                sb.AppendFormat("..{0}..{1}", 1, true);
                var sbresult = sb.ToString();


                var strzy = "c:\\a\\b";  //c:\a\b   要转义，

                #region $与@ C#里特有的
                //https://docs.microsoft.com/en-us/dotnet/csharp/language-reference/tokens/

                //@是逐字输出的意思，除了",要用""代替，同时\r\n及\也是逐字输出的而不会变成回车换行，如果要回车换行就直接在字符串里做就行了
                var strRaw1 = @"c:\a\b你是""猪""吗\\\r\n";   //c:\a\b你是"猪"吗\\\r\n

                var strRaw2 = @"
                        c:\a\b
                        d:\a\b\
                        ";

                //$ string interpolation 以内插值替换的字符串，篡改的字符串。
                //可以直接在{}里写代码，有智能提示。如果要输出“{”或“}”就得写成这个了“{{”或“}}”
                var strDollar = $"now:{DateTime.Now}，{{}}"; //now:2018/11/22 11:10:58，{}

                //来个综合的
                var strall = $@"
                        c:\a\b你是""猪""吗{Environment.NewLine}我{{不是}},这里的\r\n会原样输出
                        现在是：{DateTime.Now}
                        ";
                #endregion
            }
            #endregion


            #region string的各种操作，比较
            {
                var strischars = "x123"[0]; //x string也是字符数组，所以可以直接用下标取。

                var strStart = "Usp_SearchCity".StartsWith("usp_", StringComparison.OrdinalIgnoreCase); //true

                var strEnd = "SearchCityDTO".EndsWith("dto", StringComparison.OrdinalIgnoreCase); //true

                var strContains = "abc123".Contains("AbC", StringComparison.OrdinalIgnoreCase);

                //只是演示一下有哪些常用操作
                var strOpt = " 123|a|b|c456| ".Trim().TrimStart('1', '2').TrimEnd('5', '6')
                    .ToUpper().ToLower()
                    .Substring(0, 10) //长度取多了会报错
                    .Replace("a", "aa")
                    .Split('|');

                var strTrim = "\r\n\t   　 　  12 12　 　  \r\n\t".Trim(); //“12 12”， 前后有中英文空格及tab回车换行
                var lstTemp = new List<string> { "A", "BC", "DEF" };
                var sjoin = string.Join(",", lstTemp);//A,BC,DEF


                //各种split
                var split1 = "a|b|c".Split('|');
                var split2 = "aa@bb|cc#dd$ee,ff".Split('@', '|','#','$',',');
                //还可以移除那些为空的数据,空格不是空，以下结果：["a","b","c"," "]
                var split3 = " |||a|b,,c, ,, ".Trim().Split(new string[] { "|", "," }, StringSplitOptions.RemoveEmptyEntries);

                #region string的比较，放心的用==及Equals吧 与java不同
                {
                    var strb0 = "ABC"=="abc"; //false 常用

                    var strb1 = "ABC".Equals("abc"); //false 不常用 ，与java不同，java常用这个

                    var strb2 = "ABC".Equals("abc", StringComparison.OrdinalIgnoreCase); //true 常用

                    var eq1 = "aaa" == "aaa"; //true
                    var eq2 = "aaa" == new string("aaa"); //true
                    var eq3 = "aaa" == "a".PadLeft(3, 'a'); //true 这串代码要是在java里就是false了。
                    var eq4 = "aaa".Equals(new string('a', 3)); //true
                    var eq5 = new string("aaa") == new string("aaa"); //true
                    var eq6 = "aaa".Equals("aaa"); //true
                    var eq7 = "aaa".Equals("AAA", StringComparison.OrdinalIgnoreCase); //true  比较时指定忽略大小写
                }
                #endregion
            }
            #endregion



            #region 正则表达式
            {
                //静态方法最好用了

                var str1 = Regex.Split("aa@bb|cc#dd$ee,ff", "[@|#$,]");  //["aa","bb","cc","dd","ee","ff"]

                var str2 = Regex.Replace("a@b,c", "[@,]", "-"); //a-b-c

                var str3 = Regex.Replace("a111b222c", @"(\d+).(\d+)", "$2-$1"); //a222-111c 后向引用啦。

                var str4 = Regex.Replace("a111b222C", @"[a-z]", "_", RegexOptions.Multiline | RegexOptions.IgnoreCase); //_111_222_

                var match0 = Regex.IsMatch("a@b,c", ","); //true


                //匹配一个
                var match = Regex.Match("a111b222c", @"(\d+).(\d+)");
                if (match.Success) //这个判断不是必须的,但没有匹配到时直接写下面的代码都是返回""且不会报错。
                {
                    var mv = match.Value; //111b222 代表匹配的内容
                    var mg0 = match.Groups[0].Value; //111b222 代表匹配的内容
                    var mg1 = match.Groups[1].Value; //111 代表匹配的内容的第1个引用
                    var mg2 = match.Groups[2].Value; //222 代表匹配的内容的第2个引用
                }

                //如果要渐进匹配可以用这个
                match.NextMatch();

                //匹配多个
                var matches = Regex.Matches("a111b222c x333y444z  o555p666q", @"(\d+).(\d+)"); //匹配了3组
                foreach (Match item in matches)
                {
                    var x1 = item.Groups[1].Value + item.Groups[2].Value; //111222或333444或555666
                }

                //也可以用其实例方法，如果匹配用的多，建议用这种
                var re = new Regex(@"\d+", RegexOptions.Compiled);
                re.Match("a111b222c");

            }
            #endregion


            #region 加解密,MD5,SHA,AES,RSA
            {
                //具体实现参见：Helper/SecurityHelper.cs

                var src = "我是123の뭐라구";

                var base64_to = src.ToBase64();
                var base64_from = base64_to.FromBase64();

                var md5_16bit = src.To16bitMD5();
                var md5_32bit = src.To32bitMD5();

                var sha1 = src.ToSHA1();
                var sha512 = src.ToSHA512();

                var aesKeys = AESHelper.GetKeys();
                var aes_en = src.AESEncrypt(aesKeys.Key, aesKeys.IV);
                var aes_de = aes_en.AESDecrypt(aesKeys.Key, aesKeys.IV);

                var rsaKeys = RSAHelper.GetKeys();
                var rsa_en = src.RSAEncrypt(rsaKeys.PublicKey);
                var rsa_de = rsa_en.RSADecrypt(rsaKeys.PrivateKey);
            }
            #endregion


            #region 序列化反序列化
            {
                //原生的有xml,binary,json等，但不好用要加各种标签，并且性能有问题，直接用json.net吧，以下仅做演示用

                var person = new Person2 { Id = 100, Name = "柳永法", IsMan = true, Birthday = DateTime.Now };
                var xml = SerializeHelper.ToXml(person);
                var xmlobj = SerializeHelper.FromXml<Person2>(xml);

                var json = SerializeHelper.ToJson(person);
                var jsonobj = SerializeHelper.FromJson<Person2>(json);

                var binary = SerializeHelper.ToBinary(person);
                var binaryobj = SerializeHelper.FromBinary<Person2>(binary);

            }
            #endregion



            #region 性能测试
            {
                CodeTimer.Time("StringBuilder性能测试", () =>
                {
                    var sb = new StringBuilder();
                    for (int i = 0; i < 100000; i++)
                    {
                        sb.Append(i.ToString());
                    }
                    var result = sb.ToString();
                }); //15ms ★StringBuilder快多了，在字符串大量连接时一定要用这个。

                CodeTimer.Time("string+测试", () =>
                {
                    var result = string.Empty;
                    for (int i = 0; i < 100000; i++)
                    {
                        result += i.ToString();
                    }

                });//21464ms 一般字符串连接就这个吧，最简单,但大量连接时一定不能用它(for,foreach,等)。

                CodeTimer.Time("string.concat测试", () =>
                {
                    var result = string.Empty;
                    for (int i = 0; i < 100000; i++)
                    {
                        result = string.Concat(result, i.ToString());
                    }

                });//21588ms 貌似没什么优势，一般不用。

            }
            #endregion
        }
    }
}
