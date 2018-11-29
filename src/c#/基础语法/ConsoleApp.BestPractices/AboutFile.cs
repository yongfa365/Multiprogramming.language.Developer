using System;
using System.Collections.Generic;
using System.IO;
using System.Text;

namespace ConsoleApp.BestPractices
{


    /// <summary>
    /// 一般用静态方法就行，如果对性能要求很高，才考虑打开后操作，操作完再关闭
    /// </summary>
    public class AboutFile
    {
        public static void RunDemo()
        {
            var filepath = "C:\\FileTest\\haha\\1.txt";
            var context = "内容";
            var lstContext = new List<string> { "A", "B" };
            var byteContext = Encoding.UTF8.GetBytes(context);


            var files = Directory.GetFiles("C:\\Windows\\", "*.exe");

            Directory.CreateDirectory("C:\\FileTest\\haha"); //存在不报错

            if (!File.Exists(filepath))
            {
                //xx
            }


            File.AppendAllText(filepath, context);

            File.WriteAllText(filepath, context);
            File.WriteAllLines(filepath, lstContext);
            File.WriteAllBytes(filepath, byteContext);

            var cnt1 = File.ReadAllText(filepath);
            var cnt2 = File.ReadAllLines(filepath);
            var cnt3 = File.ReadAllBytes(filepath);


            File.Copy(filepath, filepath + ".txt");
            File.Delete(filepath);





            var tempPath = Path.Combine("C:", "a", "b", "c.txt"); //C:\a\b 
            var filename = Path.GetFileName(tempPath);
            var fullpath = Path.GetFullPath(tempPath);
            var extend = Path.GetExtension(tempPath);


        }
    }
}
