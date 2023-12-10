using System;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
using System.Net.Http;
using System.Net;
using System.Text;
using System.Threading.Tasks;
using System.Xml.Linq;
using Newtonsoft.Json.Linq;
using RouteService.ProxyServ;

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

        public static async Task<Position> GetLocation(string locationName)
        {
            string encodedUrlLocation = WebUtility.UrlEncode(locationName);
            HttpResponseMessage response = await client.GetAsync($"{baseUrl}search?format=json&q={encodedUrlLocation}");
            response.EnsureSuccessStatusCode();
            string content = await response.Content.ReadAsStringAsync();
            JArray array = JArray.Parse(content);

            if (array.Count > 0)
            {
                JObject locationData = (JObject)array[0];
                double latitude = locationData["lat"].Value<double>();
                double longitude = locationData["lon"].Value<double>();

                return new Position
                {
                    lat = latitude,
                    lng = longitude
                };
            }
            else
            {
                throw new NonExistentLocationException($"{locationName} does not exist !");
            }
        }

        public static async Task<string> GetCityNameFromCoordinates(double longitude, double latitude)
        {
            string longitudeString = longitude.ToString(CultureInfo.InvariantCulture);
            string latitudeString = latitude.ToString(CultureInfo.InvariantCulture);
            HttpResponseMessage response = await client.GetAsync($"{baseUrl}reverse?format=json&lat={latitudeString}&lon={longitudeString}&accept-language=fr");
            response.EnsureSuccessStatusCode();
            string content = await response.Content.ReadAsStringAsync();
            JObject locationData = JObject.Parse(content);

            return locationData["address"]["city"]?.ToString() ??
                   locationData["address"]["town"]?.ToString() ??
                   locationData["address"]["village"]?.ToString();
        }
    }
}
