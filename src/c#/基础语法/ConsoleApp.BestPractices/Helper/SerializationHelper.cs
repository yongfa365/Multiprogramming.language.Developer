using System;
using System.IO;
using System.Runtime.Serialization.Formatters.Binary;
using System.Runtime.Serialization.Json;
using System.Text;
using System.Xml;
using System.Xml.Serialization;

namespace ConsoleApp.BestPractices
{
    public static class SerializeHelper
    {
        #region XmlSerializer
        public static string ToXml<T>(T obj)
        {
            var sb = new StringBuilder();
            var settings = new XmlWriterSettings
            {
                Encoding = Encoding.UTF8,
                Indent = true,
                OmitXmlDeclaration = true
            };
            using (var writer = XmlWriter.Create(sb, settings))
            {
                var ns = new XmlSerializerNamespaces();
                ns.Add("", "");
                new XmlSerializer(obj.GetType()).Serialize(writer, obj, ns);
                return sb.ToString();
            }
        }


        public static T FromXml<T>(string input)
        {
            using (var reader = new XmlTextReader(new StringReader(input)))
            {
                var serializer = new XmlSerializer(typeof(T));
                return (T)serializer.Deserialize(reader);
            }
        }
        #endregion

        #region BinaryFormatter
        public static string ToBinary<T>(T obj)
        {
            using (var ms = new MemoryStream())
            {
                new BinaryFormatter().Serialize(ms, obj);
                ms.Position = 0;
                var bytes = ms.ToArray();
                var sb = new StringBuilder();
                foreach (var item in bytes)
                {
                    sb.Append(string.Format("{0:X2}", item));
                }
                return sb.ToString();
            }
        }

        public static T FromBinary<T>(string input)
        {
            var intLen = input.Length / 2;
            var bytes = new byte[intLen];
            for (int i = 0; i < intLen; i++)
            {
                var ibyte = Convert.ToInt32(input.Substring(i * 2, 2), 16);
                bytes[i] = (byte)ibyte;
            }
            using (var ms = new MemoryStream(bytes))
            {
                return (T)new BinaryFormatter().Deserialize(ms);
            }
        }
        #endregion



        #region JsonSerializer
        public static string ToJson<T>(T obj)
        {
            using (var ms = new MemoryStream())
            {
                new DataContractJsonSerializer(obj.GetType()).WriteObject(ms, obj);
                return Encoding.UTF8.GetString(ms.ToArray());
            }
        }

        public static T FromJson<T>(string input) where T : class
        {
            using (var ms = new MemoryStream(Encoding.UTF8.GetBytes(input)))
            {
                return new DataContractJsonSerializer(typeof(T)).ReadObject(ms) as T;
            }
        }
        #endregion

    }
}
