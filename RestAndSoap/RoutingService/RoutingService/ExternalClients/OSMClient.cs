using Newtonsoft.Json.Linq;
using RoutingService.model;
using System;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;

namespace RoutingService.ExternalClients
{
    class OSMClient
    {
        static HttpClient client = new HttpClient();
        const string baseUrl = "https://nominatim.openstreetmap.org/";
        static OSMClient()
        {
            client.DefaultRequestHeaders.Add("User-Agent", "YourApp/1.0");
        }

        public static async Task<string> GetLocationName(Position position)
        {
            string lat = position.Lat.ToString(CultureInfo.InvariantCulture);
            string lng = position.Lng.ToString(CultureInfo.InvariantCulture);
            Console.WriteLine($"latitude and longitude : {lat} + {lng}");
            string requestUrl = $"{baseUrl}/reverse?format=json&lat={lat}&lon={lng}";
            HttpResponseMessage response = await client.GetAsync(requestUrl);
            if (response.IsSuccessStatusCode)
            {
                string content = await response.Content.ReadAsStringAsync();
                var data = JObject.Parse(content);
                var address = data["address"];
                string locationName = address["city"]?.ToString()
                                   ?? address["town"]?.ToString()
                                   ?? address["village"]?.ToString()
                                   ?? address["hamlet"]?.ToString()
                                   ?? address["locality"]?.ToString()
                                   ?? address["suburb"]?.ToString()
                                   ?? address["neighbourhood"]?.ToString();

                return locationName;
            }
            else
            {
                throw new HttpRequestException($"Error fetching route: {response.StatusCode}");
            }
        }
    }
}
