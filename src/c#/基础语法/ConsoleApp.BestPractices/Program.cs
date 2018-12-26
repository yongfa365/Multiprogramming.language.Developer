using System;

namespace ConsoleApp.BestPractices
{

    class Program
    {
        static void Main(string[] args)
        {
            //Console.WriteLine("Please input your name, then press enter:");
            //var input = Console.ReadLine();
            //Console.WriteLine($"Hello {input}");

            //类相关的，直接看Entity目录下的内容吧
            //一下一个一个的F10看吧
            //AboutBasic.RunDemo();
            //AboutDateTime.RunDemo();
            //AboutString.RunDemo();
            // AboutCollection.RunDemo();

            AboutFile.RunDemo();
            //AboutHttp.RunDemo();
            //AboutThread.RunDemo();


            #region 说明
            { //这个花括号只是为了隔离用，可以忽略

            }
            #endregion


            //以下内容不要注释，否则异步时看不到效果，或者自行Ctrl+F5看效果
            Console.WriteLine("Console测试结束，Press Enter Exit");
            Console.ReadLine();

        }
    }
}
