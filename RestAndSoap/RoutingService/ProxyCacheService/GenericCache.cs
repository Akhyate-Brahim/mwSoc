using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Runtime.Caching;
using System.Text;
using System.Threading.Tasks;

namespace ProxyCacheService
{
    internal class GenericCache<T> where T : class
    {
        private Dictionary<string, Tuple<T, DateTimeOffset>> _cache = new Dictionary<string, Tuple<T, DateTimeOffset>>();
        public DateTimeOffset dt_default = ObjectCache.InfiniteAbsoluteExpiration;
        HttpClient _httpClient = new HttpClient();

        public T GetData(string key, DateTimeOffset dt)
        {
            if (_cache.TryGetValue(key, out Tuple<T, DateTimeOffset> cachedItem) && cachedItem != null)
            {
                if (DateTimeOffset.UtcNow > cachedItem.Item2)
                {
                    _cache.Remove(key);
                }
                else
                {
                    return cachedItem.Item1;
                }
            }

            HttpResponseMessage response = _httpClient.GetAsync(key).Result;
            response.EnsureSuccessStatusCode();
            string responseBody = response.Content.ReadAsStringAsync().Result;
            var data = JsonConvert.DeserializeObject<T>(responseBody);

            _cache[key] = Tuple.Create(data, dt);
            return data;
        }

        public T GetData(string key)
        {
            return GetData(key, dt_default);
        }
        public T GetData(string key,double dt_seconds)
        {
            return GetData(key, DateTimeOffset.UtcNow.AddSeconds(dt_seconds));
        }
    }
}
