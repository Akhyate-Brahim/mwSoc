using System.Collections.Generic;
using System.Net.Http;
using System;
using System.Runtime.Caching;
using Newtonsoft.Json;
using System.Threading.Tasks;

namespace ProxyCacheService
{
    internal class GenericCache<T> where T : class
    {
        private Dictionary<string, Tuple<T, DateTimeOffset>> _cache = new Dictionary<string, Tuple<T, DateTimeOffset>>();
        public DateTimeOffset dt_default = ObjectCache.InfiniteAbsoluteExpiration;
        HttpClient _httpClient = new HttpClient();

        public async Task<T> GetData(string key, DateTimeOffset dt)
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

            HttpResponseMessage response = await _httpClient.GetAsync(key);
            response.EnsureSuccessStatusCode();
            string responseBody = await response.Content.ReadAsStringAsync();
            var data = JsonConvert.DeserializeObject<T>(responseBody);

            _cache[key] = Tuple.Create(data, dt);
            return data;
        }

        public Task<T> GetData(string key)
        {
            return GetData(key, dt_default);
        }

        public Task<T> GetData(string key, double dt_seconds)
        {
            return GetData(key, DateTimeOffset.UtcNow.AddSeconds(dt_seconds));
        }
    }
}
