using Microsoft.EntityFrameworkCore;

namespace AboutConfiguration.EFConfigurationProvider
{
    public class EFConfigurationContext : DbContext
    {
        public EFConfigurationContext(DbContextOptions options) : base(options)
        {
        }

        public DbSet<EFConfigurationValue> Values { get; set; }
    }
}
