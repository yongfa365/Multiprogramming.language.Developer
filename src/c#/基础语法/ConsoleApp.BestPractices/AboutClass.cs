using System;

namespace ConsoleApp.BestPractices
{
    public static class AboutClass
    {


        public static void RunDemo()
        {
            //设计模式用的很少，或者说用了但不知道。
        }
    }


    interface ISpeak
    {
        void Speak();
    }

    interface ISing
    {
        void Sing();
    }

    public abstract class Animal
    {
        public virtual void Shout()
        {
            Console.WriteLine($"Animal.Shout()");
        }

        public abstract void Sleep();
    }

    class Duck : Animal
    {
        public void Eat()
        {
            Console.WriteLine($"Duck.Eat()");
        }

        public override void Sleep()
        {
            Console.WriteLine($"Duck.Sleep()");
        }
    }

    class 唐老鸭 : Duck, ISpeak, ISing //可以继承多个接口，一个基类
    {
        public static readonly 唐老鸭 Instance = new 唐老鸭(); //这个就是单例了
        private 唐老鸭() { }


        public void Speak()
        {
            Console.WriteLine($"唐老鸭.Eat()");
        }
        public void Sing()
        {
            Console.WriteLine($"唐老鸭.Sing()");
        }

        public override void Shout()
        {
            base.Shout();
            Console.WriteLine($"唐老鸭.Shout()");
        }
        public override void Sleep() //
        {
            base.Sleep();
            Console.WriteLine($"唐老鸭.Sleep()");
        }

        public new void Eat() //可以重写父类
        {
            Console.WriteLine($"唐老鸭.Eat()");

        }


    }
}
