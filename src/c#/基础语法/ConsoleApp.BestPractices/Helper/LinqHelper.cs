using System;
using System.Collections.Generic;
using System.Linq;

namespace ConsoleApp.BestPractices
{
    public static class LinqHelper
    {
        public static IEnumerable<TSource> Distinct<TSource>(this IEnumerable<TSource> source, Func<TSource, TSource, bool> predicate)
        {
            return source.Distinct(new PredicateEqualityComparer<TSource>(predicate));
        }
    }

    public class PredicateEqualityComparer<T> : EqualityComparer<T>
    {
        private readonly Func<T, T, bool> predicate;

        public PredicateEqualityComparer(Func<T, T, bool> predicate)
        {
            this.predicate = predicate;
        }

        public override bool Equals(T x, T y)
        {
            if (x == null || y == null)
            {
                return false;
            }

            return predicate(x, y);
        }

        public override int GetHashCode(T obj)
        {
            // Always return the same value to force the call to IEqualityComparer<T>.Equals
            return 0;
        }
    }
}