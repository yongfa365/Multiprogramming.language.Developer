using AboutConfiguration.Entity;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.Primitives;
using System.Collections.Generic;

namespace AboutConfiguration
{
    /// <summary>
    /// 仅仅是个不完善的Demo,实际使用要考虑是Load还是ReLoad,多个源如何处理，如何解决并发等一堆问题。
    /// </summary>
    public static class ConfigurationManager
    {
        static ConfigurationManager()
        {
            ChangeToken.OnChange(
                   () => configRoot.GetReloadToken(),
                   () =>
                   {
                       //如果Build后有赋值给别的变量，reload后那些变量的值并不会跟着变。
                       //所以监控到变更后，需要在这里再更新下相关变量
                       BuildConnectionStrings();
                       BuildPersons();
                   }
               );
        }

        private readonly static IConfigurationRoot configRoot = new ConfigurationBuilder()
                .AddJsonFile("Configs\\basicsettings.json", optional: false, reloadOnChange: true)
                .Build();


        #region ConnectionStrings
        private static ConnectionStrings _ConnectionStrings;
        public static ConnectionStrings ConnectionStrings
        {
            get
            {
                if (_ConnectionStrings == null)
                {
                    BuildConnectionStrings();
                }
                return _ConnectionStrings;
            }
        }

        private static void BuildConnectionStrings()
        {
            _ConnectionStrings = configRoot
                .GetSection("ConnectionStrings")
                .Get<ConnectionStrings>();
        }
        #endregion


        #region Persons
        private static List<Person> _Persons;
        public static List<Person> Persons
        {
            get
            {
                if (_Persons == null)
                {
                    BuildPersons();
                }
                return _Persons;
            }
        }

        private static void BuildPersons()
        {
            _Persons = configRoot
                .GetSection("Persons")
                .Get<List<Person>>();
        }
        #endregion


    }
}
