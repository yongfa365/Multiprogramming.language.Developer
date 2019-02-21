using Microsoft.Extensions.Configuration;
using System;
using System.Collections.Generic;

namespace AboutConfiguration.CustomConfigurationProvider
{
    public class CustomConfigurationProvider : ConfigurationProvider
    {
        private readonly Func<Dictionary<string, string>> loadData;
        public CustomConfigurationProvider(Func<Dictionary<string, string>> loadData, int intervalSecond)
        {
            this.loadData = loadData;

            new System.Timers.Timer
            {
                Interval = 1000 * intervalSecond,
                Enabled = true
            }.Elapsed += (sender, e) =>
            {
                Load();
            };
        }

        public override void Load()
        {
            Data = loadData();
        }

    }
}
