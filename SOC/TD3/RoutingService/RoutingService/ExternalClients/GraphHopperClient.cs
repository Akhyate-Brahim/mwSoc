using Newtonsoft.Json.Linq;
using RoutingService.model;
using RoutingService.util;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;

namespace RoutingService
{
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
}
