using Newtonsoft.Json.Linq;
using RouteService.ProxyServ;
using System;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;

namespace RouteService.ExternalClients
{
    class OSMClient
    {
        static HttpClient client = new HttpClient();
        const string baseUrl = "https://nominatim.openstreetmap.org/";
        static OSMClient()
        {
            client.DefaultRequestHeaders.Add("User-Agent", "YourApp/1.0");
        }
        public static Position GetLocation(string locationName)
        {
            string encodedUrlLocation = WebUtility.UrlEncode(locationName);
            HttpResponseMessage response = client.GetAsync($"{baseUrl}search?format=json&q={encodedUrlLocation}").Result;
            response.EnsureSuccessStatusCode();
            string content = response.Content.ReadAsStringAsync().Result;
            JArray array = JArray.Parse(content);
            if (array.Count > 0)
            {
                JObject locationData = (JObject)array[0];
                double latitude = locationData["lat"].Value<double>();
                double longitude = locationData["lon"].Value<double>();
                var position = new Position
                {
                    Lat = latitude,
                    Lng = longitude
                };
                return position;
            }
            else
            {
                throw new NonExistentLocationException($"{locationName} does not exist !");
            }
        }
        public static string GetCityNameFromCoordinates(double longitude, double latitude)
        {
            string longitudeString = longitude.ToString(CultureInfo.InvariantCulture);
            string latitudeString = latitude.ToString(CultureInfo.InvariantCulture);
            HttpResponseMessage response = client.GetAsync($"{baseUrl}reverse?format=json&lat={latitudeString}&lon={longitudeString}&accept-language=fr").Result;
            response.EnsureSuccessStatusCode();
            string content = response.Content.ReadAsStringAsync().Result;
            JObject locationData = JObject.Parse(content);

            string city = locationData["address"]["city"]?.ToString() ??
                          locationData["address"]["town"]?.ToString() ??
                          locationData["address"]["village"]?.ToString();

            return city;
        }
    }

}

