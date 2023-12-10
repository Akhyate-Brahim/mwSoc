
using Newtonsoft.Json.Linq;
using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Net.Http;
    using System.Text;
    using System.Threading.Tasks;
    using System.Xml.Linq;

    namespace GraphHopperClient
    {
        class Program
        {
        static async Task Main(string[] args)
        {
            string start = "51.131108,12.414551"; // Replace with actual start coordinates
            string end = "51.165691,10.451526";   // Replace with actual end coordinates
            string vehicle = "car";               // Replace with desired vehicle type if needed

            try
            {
                List<Position> route = await GraphHopperClient.GetRoute(start, end, vehicle);
                foreach (var position in route)
                {
                    Console.WriteLine(position);
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

            public static async Task<List<Position>> GetRoute(string start, string end, string vehicle = "bike")
            {
                string requestUrl = $"{baseUrl}?point={start}&point={end}&vehicle={vehicle}&key={apiKey}";
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
        public static List<Position> DecodedPositions(string encoded)
        {
            var poly = new List<Position>();
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

                Position p = new Position
                {
                    Lat = lat / 1E5,
                    Lng = lng / 1E5
                };
                poly.Add(p);
            }

            return poly;
        }
    }

}

