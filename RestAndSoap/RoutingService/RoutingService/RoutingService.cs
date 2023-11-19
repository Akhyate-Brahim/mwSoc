using Newtonsoft.Json;
using RoutingService.ExternalClients;
using RoutingService.model;
using System;
using System.Collections.Generic;
using System.Diagnostics.Contracts;
using System.Linq;
using System.Net.Http;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;
using System.Threading.Tasks;
using Contract = RoutingService.model.Contract;

namespace RoutingService
{
    class RoutingService : IRoutingService
    {
        const string apiKey = "b1a5bf2860e859e32770061a4cdd5e222a7e9e96";
        const string baseUrl = "https://api.jcdecaux.com/vls/v1/";
        static HttpClient client = new HttpClient();



        public List<Position> GetItinerary(Position departure, Position destination)
        {
            throw new NotImplementedException();
        }






















        static async Task<List<Contract>> GetAllContracts()
        {
            HttpResponseMessage response = client.GetAsync($"{baseUrl}/contracts?apiKey={apiKey}").Result;
            response.EnsureSuccessStatusCode();
            string responseBody = response.Content.ReadAsStringAsync().Result;
            var contracts = JsonConvert.DeserializeObject<List<Contract>>(responseBody);
            return contracts;
        }
        static async Task<Contract> FindContract(List<Contract> contracts, Position position)
        {
            string locationName = await OSMClient.GetLocationName(position);
            foreach (var contract in contracts)
            {
                if (contract.Cities != null && contract.Cities.Contains(locationName, StringComparer.OrdinalIgnoreCase))
                {
                    return contract;
                }
            }
            throw new NotImplementedException();
        }
        static List<Station> GetAllStations(Contract contract)
        {
            HttpResponseMessage response = client.
                GetAsync($"{baseUrl}stations?contract={contract.Name}&apiKey={apiKey}").Result;
            response.EnsureSuccessStatusCode();
            string responseBody = response.Content.ReadAsStringAsync().Result;
            var stations = JsonConvert.DeserializeObject<List<Station>>(responseBody);
            return stations;
        }
        static Station FindClosestStation(List<Station> stations, Station myStation)
        {
            double minDistance = double.MaxValue;
            Station closestStation = null;
            foreach (var station in stations)
            {
                if (station.Number == myStation.Number)
                {
                    continue;
                }
                double distance = myStation.DistanceTo(station);
                if (distance < minDistance)
                {
                    minDistance = distance;
                    closestStation = station;
                }
            }
            return closestStation;
        }
        static Station GetStation(List<Station> stations, int stationNumber)
        {
            foreach (var station in stations)
            {
                if (station.Number.Equals(stationNumber))
                {
                    return station;
                }
            }
            return null;
        }
    }

}
