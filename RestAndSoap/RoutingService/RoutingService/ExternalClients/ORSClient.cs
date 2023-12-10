﻿using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using RouteService.ProxyServ;
using System;
using System.Collections.Generic;
using System.Globalization;
using System.Net.Http;
using System.Threading.Tasks;

namespace RouteService.ExternalClients
{
    class ORSClient
    {
        static HttpClient client = new HttpClient();
        private const string baseUrl = "https://api.openrouteservice.org/v2/directions/";
        private const string apiKey = "5b3ce3597851110001cf6248f46b99f27a9b4851896d308b7a039734";

        static ORSClient()
        {
            client.DefaultRequestHeaders.Add("User-Agent", "YourApp/1.0");
            client.DefaultRequestHeaders.Add("Authorization", apiKey);
        }

        public static RouteSegment GetRoute(string startLocation, string endLocation, string transportMode)
        {
            HttpResponseMessage response = client.GetAsync($"{baseUrl}{transportMode}?start={startLocation}&end={endLocation}").Result;
            response.EnsureSuccessStatusCode();
            string responseBody = response.Content.ReadAsStringAsync().Result;
            FeatureCollection featureCollection = JsonConvert.DeserializeObject<FeatureCollection>(responseBody);
            List<Step> routeSteps = featureCollection.Features[0].Properties.Segments[0].Steps;
            List<List<double>> responsePositions = featureCollection.Features[0].Geometry.Coordinates;
            double distancia = featureCollection.Features[0].Properties.Summary.Distance;
            double time = featureCollection.Features[0].Properties.Summary.Duration;
            List<Position> positions = new List<Position>();
            foreach (var positionData in responsePositions)
            {
                if (positionData.Count >= 2)
                {
                    Position position = new Position
                    {
                        Lng = positionData[0],
                        Lat = positionData[1]
                    };
                    positions.Add(position);
                }
            }
            RouteSegment routeinfo = new RouteSegment
            {
                steps = routeSteps,
                routePositions = positions,
                distance = distancia,
                duration = time
            };
            return routeinfo;
        }
        public static string PositionToString(Position position)
        {
            return String.Format(CultureInfo.InvariantCulture, "{0},{1}", position.Lng, position.Lat);
        }


    }
}