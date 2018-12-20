using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Cryptography;
using System.Text;

namespace ConsoleApp.BestPractices
{
    public static class Helper
    {
        #region To int 的扩展方法

        public static int ToInt(this string input, int defaultValue = 0)
        {
            if (int.TryParse(input, out int value))
            {
                return value;
            }
            else
            {
                return defaultValue;
            }
        }


        public static int? ToIntOrNull(this string input, int? defaultValue)
        {
            if (int.TryParse(input, out int value))
            {
                return value;
            }
            else
            {
                return defaultValue;
            }
        }


        public static int? ToIntOrNull(this string input)
        {
            return input.ToIntOrNull(null);
        }

        #endregion


        #region Collection<T>的扩展方法
        /// <summary>
        /// input==null || input.Count==0 时返回true
        /// </summary>
        /// <typeparam name="T"></typeparam>
        /// <param name="input"></param>
        /// <returns></returns>
        public static bool IsNullOrEmpty<T>(this ICollection<T> input)
        {
            if (input == null || input.Count == 0)
            {
                return true;
            }
            return false;
        }

        /// <summary>
        /// input==null && input.Count>0 时返回true
        /// </summary>
        /// <typeparam name="T"></typeparam>
        /// <param name="input"></param>
        /// <returns></returns>
        public static bool HasValue<T>(this ICollection<T> input)
        {
            return !input.IsNullOrEmpty();
        }


        #endregion


        #region String的扩展方法
        public static string TrimOrNull(this string input)
        {
            if (string.IsNullOrWhiteSpace(input))
            {
                return null;
            }
            else
            {
                return input.Trim();
            }
        }

        public static string Right(this string input, int length)
        {
            if (string.IsNullOrEmpty(input) || input.Length <= length)
            {
                return input;
            }
            else
            {
                return input.Substring(input.Length - length);
            }
        }


        public static string Left(this string input, int length)
        {
            if (string.IsNullOrEmpty(input) || input.Length <= length)
            {
                return input;
            }
            else
            {
                return input.Substring(0, length);
            }
        }

        /// <summary>
        /// 
        /// </summary>
        /// <param name="input"></param>
        /// <param name="count">复制1百万次50ms,相当快呀</param>
        /// <returns></returns>
        public static string Repeat(this string input, int count)
        {
            var temp = Enumerable.Repeat(input, count);
            var result = string.Concat(temp);
            return result;
        }


        #endregion

        public static void NoErrorInvoke(Action act)
        {
            try
            {
                act();
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex);  //写日志
            }
        }



    }




}
