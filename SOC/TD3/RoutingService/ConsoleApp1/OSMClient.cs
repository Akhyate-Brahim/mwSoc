using Newtonsoft.Json.Linq;
using System;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;
using System.Xml.Linq;

namespace ConsoleApp1
{
    class OSMClient
    {
        static HttpClient client = new HttpClient();
        const string baseUrl = "https://nominatim.openstreetmap.org/";
        static OSMClient()
        {
            client.DefaultRequestHeaders.Add("User-Agent", "YourApp/1.0");
        }

        public static async Task<string> GetCityName(Position position)
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
    class Position
    {
        public double Lat { get; set; }
        public double Lng { get; set; }

        public override string ToString()
        {
            return $"Latitude: {Lat}, Longitude: {Lng}";
        }

        // Calculate distance to another Position object in meters
        public double DistanceTo(Position other)
        {
            var earthRadiusKm = 6371;

            var dLat = DegreesToRadians(other.Lat - this.Lat);
            var dLon = DegreesToRadians(other.Lng - this.Lng);

            var lat1 = DegreesToRadians(this.Lat);
            var lat2 = DegreesToRadians(other.Lat);

            var a = Math.Sin(dLat / 2) * Math.Sin(dLat / 2) +
                    Math.Sin(dLon / 2) * Math.Sin(dLon / 2) * Math.Cos(lat1) * Math.Cos(lat2);
            var c = 2 * Math.Atan2(Math.Sqrt(a), Math.Sqrt(1 - a));
            return earthRadiusKm * c * 1000;
        }

        private static double DegreesToRadians(double degrees)
        {
            return degrees * Math.PI / 180.0;
        }
    }
}
