using Microsoft.Extensions.Configuration;
using System;
using System.Collections.Generic;

namespace AboutConfiguration.CustomConfigurationProvider
{
    public static class CustomConfigurationExtensions
    {
        public static IConfigurationBuilder AddCustomGuidConfiguration(this IConfigurationBuilder builder)
        {
            return builder.Add(new CustomConfigurationSource(() =>
            {
                return new Dictionary<string, string>
                {
                   { Guid.NewGuid().ToString(), DateTime.Now.ToString() }
                };

            }, 5));
        }

        public static IConfigurationBuilder AddCustomDateTimeConfiguration(this IConfigurationBuilder builder)
        {
            return builder.Add(new CustomConfigurationSource(() =>
            {
                return new Dictionary<string, string>
                {
                   { DateTime.Now.ToString("yyy-MM-dd HH.mm.ss.fff"), DateTime.Now.ToString() }
                };

            }, 5));
        }

        public static IConfigurationBuilder AddApiConfiguration(this IConfigurationBuilder builder)
        {
            return builder.Add(new CustomConfigurationSource(() =>
            {
                //假装调用API取得数据
                return new Dictionary<string, string>
                {
                   { "API1:Key1", DateTime.Now.ToString() },
                   { "API2:Key2", DateTime.Now.ToString() },
                };

            }, 60 * 24 * 365));
        }
    }
}
