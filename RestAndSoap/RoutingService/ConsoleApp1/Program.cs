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
    internal class Program
    {
        static async Task Main(string[] args)
        {
            try
            {
                // Example points (latitude,longitude format)
                List<string> points = new List<string>
                {
                    "48.8588443,2.2943506", // Eiffel Tower
                    "48.8606111,2.337644",  // Louvre Museum
                    "48.8529682,2.3499021", // Notre-Dame
                    "48.8867046,2.3431043"  // Sacré-Cœur
                };

                // Fetching the route
                var route = await GraphHopperClient.GetRoute(points, "bike");

                // Printing each position in the route
                foreach (var position in route)
                {
                    Console.WriteLine($"Latitude: {position.Lat}, Longitude: {position.Lng}");
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine($"An error occurred: {ex.Message}");
            }
            Console.ReadLine(); 
        }
    }
    class GraphHopperClient
    {
        const string apiKey = "00ad9ec2-3eaa-4086-bc08-c03c0dcc5b80";
        const string baseUrl = "https://graphhopper.com/api/1/route";
        static HttpClient client = new HttpClient();

        public static async Task<List<string>> GetRoute(List<string> points, string vehicle = "bike")
        {
            var pointParams = string.Join("&", points.Select(p => $"point={p}"));
            string requestUrl = $"{baseUrl}?{pointParams}&vehicle={vehicle}&key={apiKey}";

            HttpResponseMessage response = await client.GetAsync(requestUrl);
            if (response.IsSuccessStatusCode)
            {
                string content = await response.Content.ReadAsStringAsync();
                var data = JObject.Parse(content);
                string encodedPolyline = data["paths"][0]["points"].Value<string>();
                return RoutePositionCalculator.DecodedPositions(encodedPolyline);
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
        class RoutePositionCalculator
        {
            public static List<string> DecodedPositions(string encoded)
            {
                var poly = new List<string>();
                int index = 0, len = encoded.Length;
                int lat = 0, lng = 0;

                while (index < len)
                {
                    int b, shift = 0, result = 0;
                    do
                    {
                        b = encoded[index++] - 63;
                        result |= (b & 0x1f) << shift;
                        shift += 5;
                    } while (b >= 0x20);
                    int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
                    lat += dlat;

                    shift = 0;
                    result = 0;
                    do
                    {
                        b = encoded[index++] - 63;
                        result |= (b & 0x1f) << shift;
                        shift += 5;
                    } while (b >= 0x20);
                    int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
                    lng += dlng;

                    double latDouble = lat / 1E5;
                    double lngDouble = lng / 1E5;

                    string positionString = $"{latDouble.ToString(CultureInfo.InvariantCulture)},{lngDouble.ToString(CultureInfo.InvariantCulture)}";
                    poly.Add(positionString);
                }

                return poly;
            }
        }
    }

