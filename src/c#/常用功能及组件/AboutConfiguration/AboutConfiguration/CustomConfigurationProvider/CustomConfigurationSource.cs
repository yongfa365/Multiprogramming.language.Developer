using Microsoft.Extensions.Configuration;
using System;
using System.Collections.Generic;

namespace AboutConfiguration.CustomConfigurationProvider
{
    public class CustomConfigurationSource : IConfigurationSource
    {
        private readonly Func<Dictionary<string, string>> loadData;
        private readonly int intervalSecond;

        public CustomConfigurationSource(Func<Dictionary<string, string>> loadData, int intervalSecond)
        {
            this.loadData = loadData;
            this.intervalSecond = intervalSecond;
        }

        public IConfigurationProvider Build(IConfigurationBuilder builder)
        {
            return new CustomConfigurationProvider(loadData,intervalSecond);
        }
    }
}
