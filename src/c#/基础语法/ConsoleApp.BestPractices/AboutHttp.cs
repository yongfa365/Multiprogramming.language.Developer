using System;
using System.IO;
using System.IO.Compression;
using System.Net;
using System.Text;

namespace ConsoleApp.BestPractices
{
    /// <summary>
    /// 常用的操作基本都是一句话的事情，这要是在Java，你还是用第三方的HttpClient吧。
    /// </summary>
    public class AboutHttp
    {
        public static void RunDemo()
        {
            var url = "https://www.cnblogs.com";
            var filename = @"D:\1.html";
            var body = "{a:1,b:2}";


            using (var client = new WebClient())
            {
                //下载二进制、文件、String,就是一句话的事情
                client.DownloadString(url);
                client.DownloadData(url);
                client.DownloadFile(url, filename);

                //上传二进制、文件、String,就是一句话的事情
                client.UploadString(url, body);
                client.UploadData(url, Encoding.UTF8.GetBytes(body));
                client.UploadFile(url, filename);
            }


            //如有必要设置各种参数吧
            using (var client = new WebClient())
            {
                client.Encoding = Encoding.UTF8;
  
                //Request Headers

                client.Headers.Add(HttpRequestHeader.Accept, "application/json, text/plain, */*");//有一堆枚举可用
                //client.Headers.Add("Host", "account.geekbang.org"); //有些不能设置
                //client.Headers.Add("Connection", " keep-alive");
                //client.Headers.Add("Content-Length", " 113");
                client.Headers.Add("Accept", " application/json, text/plain, */*");
                client.Headers.Add("Origin", " https://www.baidu.com");
                client.Headers.Add("User-Agent", " Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.67 Safari/537.36");
                client.Headers.Add("Content-Type", " application/json");
                client.Headers.Add("Referer", " https://www.cnblogs.com/");
                client.Headers.Add("Accept-Encoding", " gzip, deflate, br");
                client.Headers.Add("Accept-Language", " zh-CN,zh;q=0.9,en;q=0.8,zh-TW;q=0.7");
                client.Headers.Add("Cookie", "a=b;c=3");

                //Response Headers
                var str = client.ResponseHeaders["Set-Cookie"];
            }



        }


    }




    /// <summary>
    /// 如有必要，可以重写一个新的
    /// </summary>
    public class GZipWebClient : WebClient
    {
        protected override WebRequest GetWebRequest(Uri address)
        {
            var request = (HttpWebRequest)base.GetWebRequest(address);
            request.AutomaticDecompression = DecompressionMethods.GZip | DecompressionMethods.Deflate;
            request.Headers.Add(HttpRequestHeader.AcceptEncoding, "gzip,deflate");
            request.ContentType = "text/xml";
            request.ServicePoint.ConnectionLimit = int.MaxValue;
            request.Credentials = CredentialCache.DefaultNetworkCredentials;
            request.Timeout = 100 * 1000;
            request.ReadWriteTimeout = 300 * 1000;
            request.AllowAutoRedirect = true;


            return request;
        }

        public string UnZip(string input)
        {
            var data = Convert.FromBase64String(input);
            using (var ms = new MemoryStream(data))
            using (var gzs = new GZipStream(ms, CompressionMode.Decompress))
            using (var sr = new StreamReader(gzs))
            {
                return sr.ReadToEnd();
            }
        }
    }
}
