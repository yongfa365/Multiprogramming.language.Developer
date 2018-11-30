using System;
using System.Collections.Generic;
using System.IO;
using System.Runtime.Serialization.Json;
using System.Security.Cryptography;
using System.Text;

namespace ConsoleApp.BestPractices
{
    public static class SecurityHelper
    {

        public static string ToBase64(this string input)
        {
            var result = Convert.ToBase64String(Encoding.UTF8.GetBytes(input));
            return result;
        }

        public static string FromBase64(this string input)
        {
            var result = Encoding.UTF8.GetString(Convert.FromBase64String(input));
            return result;
        }

        /// <summary>
        /// ToSHA1
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        public static string ToSHA1(this string input)
        {
            using (var sha1 = new SHA1CryptoServiceProvider())
            {
                string result = BitConverter.ToString(sha1.ComputeHash(Encoding.UTF8.GetBytes(input)));
                return result.Replace("-", "");
            }
        }

        /// <summary>
        /// 采用SHA512算法对字符串加密
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        public static string ToSHA512(this string input)
        {
            using (var sha = new SHA512Managed())
            {
                string result = BitConverter.ToString(sha.ComputeHash(UTF8Encoding.UTF8.GetBytes(input)));
                return result.Replace("-", "");
            }
        }

        /// <summary>
        /// 16位，低8位
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        public static string To16bitMD5(this string input)
        {
            using (var md5 = new MD5CryptoServiceProvider())
            {
                string result = BitConverter.ToString(md5.ComputeHash(Encoding.UTF8.GetBytes(input)), 4, 8);
                return result.Replace("-", "");
            }
        }

        /// <summary>
        /// 32位
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        public static string To32bitMD5(this string input)
        {
            using (var md5 = new MD5CryptoServiceProvider())
            {
                string result = BitConverter.ToString(md5.ComputeHash(Encoding.UTF8.GetBytes(input)));
                return result.Replace("-", "");
            }
        }


    }




    public static class AESHelper
    {

        /// <summary>
        /// 实际使用时生成Key及IV后保存起来
        /// </summary>
        /// <returns></returns>
        internal static (string Key, string IV) GetKeys()
        {
            using (var provider = new AesCryptoServiceProvider())
            {
                provider.GenerateKey();
                provider.GenerateIV();

                var key = Convert.ToBase64String(provider.Key);
                var iv = Convert.ToBase64String(provider.IV);

                return (key, iv);
            }
        }


        public static string AESEncrypt(this string input, string key, string iv)
        {
            string result;

            var rgbKey = Convert.FromBase64String(key);
            var rgbIV = Convert.FromBase64String(iv);

            using (var provider = new AesCryptoServiceProvider())
            using (var crypto = provider.CreateEncryptor(rgbKey, rgbIV))
            using (var ms = new MemoryStream())
            using (var cs = new CryptoStream(ms, crypto, CryptoStreamMode.Write))
            {
                using (var sw = new StreamWriter(cs))
                {
                    sw.Write(input);
                }
                result = Convert.ToBase64String(ms.ToArray());
            }

            return result;
        }





        public static string AESDecrypt(this string input, string key, string iv)
        {
            string result;

            var bytes = Convert.FromBase64String(input);
            var rgbKey = Convert.FromBase64String(key);
            var rgbIV = Convert.FromBase64String(iv);

            using (var provider = new AesCryptoServiceProvider())
            using (var crypto = provider.CreateDecryptor(rgbKey, rgbIV))
            using (var ms = new MemoryStream(bytes))
            using (var cs = new CryptoStream(ms, crypto, CryptoStreamMode.Read))
            {
                using (var read = new StreamReader(cs))
                {
                    result = read.ReadToEnd();
                }
            }

            return result;
        }



    }

    public static class RSAHelper
    {
        /// <summary>
        /// 实际使用时生成一对Key要记录好，PublicKey给调用方，PrivateKey自己保留好
        /// </summary>
        /// <returns></returns>
        internal static (string PublicKey, string PrivateKey) GetKeys()
        {
            using (var rsa = new RSACryptoServiceProvider())
            {

                var publicKey = SerializeHelper.ToXml(rsa.ExportParameters(false)); // 公钥
                var privateKey = SerializeHelper.ToXml(rsa.ExportParameters(true)); // 私钥
                return (publicKey, privateKey);
            }
        }

        /// <summary>
        /// RSA加密
        /// </summary>
        /// <param name="publicKey"></param>
        /// <param name="input"></param>
        /// <returns></returns>
        public static string RSAEncrypt(this string input, string publicKey)
        {

            using (var rsa = new RSACryptoServiceProvider())
            {
                rsa.ImportParameters(SerializeHelper.FromXml<RSAParameters>(publicKey));
                var bytes = rsa.Encrypt(Encoding.UTF8.GetBytes(input), false);
                var result = Convert.ToBase64String(bytes);
                return result;
            }
        }


        /// <summary>
        /// RSA解密
        /// </summary>
        /// <param name="privateKey"></param>
        /// <param name="input"></param>
        /// <returns></returns>
        public static string RSADecrypt(this string input, string privateKey)
        {
            using (var rsa = new RSACryptoServiceProvider())
            {
                rsa.ImportParameters(SerializeHelper.FromXml<RSAParameters>(privateKey));
                var bytes = rsa.Decrypt(Convert.FromBase64String(input), false);
                var result = Encoding.UTF8.GetString(bytes);
                return result;
            }
        }
    }
}
