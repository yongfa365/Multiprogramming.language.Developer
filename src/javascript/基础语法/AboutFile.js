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
            Console.WriteLine("控制台输入输出，随便输入个，回车结束:");
            var input = Console.ReadLine();
            Console.WriteLine($"你输入了：{input}");



            var filepath = "C:\\FileTest\\haha\\1.txt";
            var context = "内容";
            var lstContext = new List<string> { "A", "B" };
            var byteContext = Encoding.UTF8.GetBytes(context);

            //获取当前工作目录
            var workingDirectory = Environment.CurrentDirectory;
            
            //改变当前工作目录，说改就改，之后的文件操作的BasePath就是这个了，Java11没有这样的功能。
            Environment.CurrentDirectory = "c:\\windows";


            //仅当前目录
            var files1 = Directory.GetFiles("C:\\Windows\\", "*.exe");

            //包括子目录，可以指定多种筛选规则
            var files2 = Directory.GetFiles("C:\\Windows\\", "*.EXE", new EnumerationOptions
            {
                RecurseSubdirectories = true,
                MatchCasing = MatchCasing.PlatformDefault,
                AttributesToSkip = FileAttributes.Normal
            });

            Directory.CreateDirectory("C:\\FileTest\\haha"); //存在不报错

            if (!File.Exists(filepath))
            {
                //xx
            }


            File.AppendAllText(filepath, context);

            File.WriteAllText(filepath, context);
            File.WriteAllLines(filepath, lstContext);
            File.WriteAllBytes(filepath, byteContext);

            //文件在用时无权删除，如果要删除则需要包装下kernel32.dll

            //http://www.jeremyshanks.com/fastest-way-to-write-text-files-to-disk-in-c/
            using (var writer = new StreamWriter("C:\\bigtest.txt", true, Encoding.UTF8, 65536))
            {
                var temp = "12345".Repeat(1024);
                for (int i = 0; i < 1000000; i++)
                {
                    writer.WriteLine(temp);
                }
            }
            File.Delete("C:\\bigtest.txt");


            var cnt1 = File.ReadAllText(filepath);
            var cnt2 = File.ReadAllLines(filepath);
            var cnt3 = File.ReadAllBytes(filepath);


            File.Copy(filepath, filepath + ".txt", true);
            File.Delete(filepath);


            var tempPath = Path.Combine("C:", "a", "b", "c.txt"); //C:\a\b\c.txt 
            var filename = Path.GetFileName(tempPath);
            var fullpath = Path.GetFullPath(tempPath);
            var extend = Path.GetExtension(tempPath);
        }
    }
}
