//https://developer.mozilla.org/en-US/docs/Web/API/Fetch_API/Using_Fetch

fetch('https://jsonplaceholder.typicode.com/todos/1')
    .then(response => response.json())
    .then(json => console.log(json))

fetch('https://jsonplaceholder.typicode.com/todos/1', { mode: 'no-cors' })
    .then(response => response.json())
    .then(json => console.log(json))

//这些都是Promise,而不是最终的结果
response.json()
response.text()



var url = "https://www.cnblogs.com";
var filename = "D:\\1.html";
var body = { a: 1, b: 2 };

fetch(url, {mode:"no-cors"})
    .then(rs => rs.text())
    .then(body => console.log(body));

//no-cors获取不到rs.headers,可以在一个网站请求自己的页面 来看此功能
fetch("https://www.baidu.com/")
    .then(rs => rs.headers.forEach(console.log));

//貌似没关注是否gzip,直接就用了
fetch("https://www.baidu.com/", { headers: { "Accept-Encoding": "gzip" } })
    .then(rs => rs.text())
    .then(body => console.log(body));


// Example POST method implementation:
async function postData(url = '', data = {}) {
    // Default options are marked with *
    const response = await fetch(url, {
        method: 'POST', // *GET, POST, PUT, DELETE, etc.
        mode: 'no-cors', // no-cors, *cors, same-origin
        cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
        credentials: 'same-origin', // include, *same-origin, omit
        headers: {
            'Content-Type': 'application/json'
            // 'Content-Type': 'application/x-www-form-urlencoded',
        },
        redirect: 'follow', // manual, *follow, error
        referrerPolicy: 'no-referrer', // no-referrer, *no-referrer-when-downgrade, origin, origin-when-cross-origin, same-origin, strict-origin, strict-origin-when-cross-origin, unsafe-url
        body: JSON.stringify(data) // body data type must match "Content-Type" header
    });
    return response.json(); // parses JSON response into native JavaScript objects
}

postData('https://jsonplaceholder.typicode.com/posts', { answer: 42 })
    .then(data => {
        console.log(data); // JSON data parsed by `data.json()` call
    });


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

                //Async
                client.DownloadStringTaskAsync(new Uri(url))
                    .ContinueWith(p => Console.Write(p.Result));
            }


            //如有必要设置各种参数吧
            using (var client = new WebClient())
            {
                client.Encoding = Encoding.UTF8;

                //Request Headers

                client.Headers.Add(HttpRequestHeader.Accept, "application/json, text/plain, */*");//有一堆枚举可用
                //client.Headers.Add("Host", "www.cnblogs.com"); //有些不能设置
                //client.Headers.Add("Connection", "keep-alive");
                //client.Headers.Add("Content-Length", "113");
                client.Headers.Add("Accept", "application/json, text/plain, */*");
                client.Headers.Add("Origin", "https://www.baidu.com");
                client.Headers.Add("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.67 Safari/537.36");
                client.Headers.Add("Content-Type", "application/json");
                client.Headers.Add("Referer", "https://www.cnblogs.com/");
                client.Headers.Add("Accept-Encoding", "gzip, deflate, br");
                client.Headers.Add("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8,zh-TW;q=0.7");
                client.Headers.Add("Cookie", "a=1;b=3");

                //Response Headers
                var str = client.ResponseHeaders?["Set-Cookie"];
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
