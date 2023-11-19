using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Cache;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;

namespace TD2
{
    internal class RESTClient
    {
        const string apiKey = "b1a5bf2860e859e32770061a4cdd5e222a7e9e96";
        static HttpClient client = new HttpClient();
        static void Main(string[] args) {
            try
            {
                List<Contract> contracts = GetAllContracts();
                PrintAllElements(contracts);
                Console.WriteLine("choose one of these contracts");
                string contractName = Console.ReadLine();
                Contract contract = GetContract(contracts, contractName);
                List<Station> stations = GetAllStations(contract);
                Console.WriteLine("Stations for chosen contract : ");
                PrintAllElements(stations);
                Console.WriteLine("choose a station by its number : ");
                int stationId = int.Parse(Console.ReadLine());
                Station station = GetStation(stations, stationId);
                Console.WriteLine("your station : "+station.ToString());
                Station closestStation = FindClosestStation(stations, station);
                Console.WriteLine("The closest station : " + closestStation.ToString());
                Console.WriteLine("the distance between them in meters : " + station.DistanceTo(closestStation));
                Console.ReadLine();

            } catch(Exception ex)
            {
                Console.WriteLine(ex.ToString());
                Console.ReadLine();
            }

        }
        static Contract GetContract(List<Contract> contracts, string contractName)
        {
            foreach (var contract in contracts){
                if (contract.Name.Equals(contractName))
                {
                    return contract;
                }
            }
            return null;
        }

        static List<Contract> GetAllContracts() {
            HttpResponseMessage response = client
                .GetAsync($"https://api.jcdecaux.com/vls/v1/contracts?apiKey={apiKey}").Result;
            response.EnsureSuccessStatusCode();
            string responseBody = response.Content.ReadAsStringAsync().Result;
            var contracts = JsonConvert.DeserializeObject<List<Contract>>(responseBody);
            return contracts;
        }
        static void PrintAllElements<T>(List<T> itemList)
        {
            if (itemList == null || itemList.Count == 0)
            {
                Console.WriteLine("empty List");
                return;
            }
            foreach (T item in itemList)
            {
                Console.WriteLine(item.ToString());
            }
        }
        static List<Station> GetAllStations(Contract contract)
        {
            HttpResponseMessage response = client.
                GetAsync($"https://api.jcdecaux.com/vls/v1/stations?contract={contract.Name}&apiKey={apiKey}").Result;
            response.EnsureSuccessStatusCode();
            string responseBody = response.Content.ReadAsStringAsync().Result;
            var stations =JsonConvert.DeserializeObject<List<Station>>(responseBody);
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

    public class Contract
    {
        public string Name { get; set; }
        public string CommercialName { get; set; }
        public List<string> Cities { get; set; }
        public string CountryCode { get; set; }

        public override string ToString()
        {
            var citiesString = Cities != null ? string.Join(", ", Cities) : "None";
            var commercialNameDisplay = !string.IsNullOrWhiteSpace(CommercialName) ? CommercialName : "N/A";
            var countryCodeDisplay = !string.IsNullOrWhiteSpace(CountryCode) ? CountryCode : "N/A";

            return $"Contract: {Name ?? "Unknown"} ({commercialNameDisplay}), Cities: {citiesString}, Country Code: {countryCodeDisplay}\n";
        }
    }

    public class Station
    {
        public int Number { get; set; }
        public string ContractName { get; set; }
        public string Name { get; set; }
        public string Address { get; set; }
        public Position Position { get; set; }
        public bool Banking { get; set; }
        public bool Bonus { get; set; }
        public int BikeStands { get; set; }
        public int AvailableBikeStands { get; set; }
        public int AvailableBikes { get; set; }
        public string Status { get; set; }
        public long LastUpdate { get; set; }

        public override string ToString()
        {
            var positionString = Position?.ToString() ?? "Unknown position";
            var statusDisplay = !string.IsNullOrWhiteSpace(Status) ? Status : "N/A";

            return $"Station Number: {Number}, Name: {Name ?? "Unnamed"}, Address: {Address ?? "No Address"}, Position: {positionString}, Banking: {Banking}, Bonus: {Bonus}, Bike Stands: {BikeStands}, Available Stands: {AvailableBikeStands}, Available Bikes: {AvailableBikes}, Status: {statusDisplay}, Last Update: {LastUpdate}\n";
        }
        public double DistanceTo(Station other)
        {
            return this.Position.DistanceTo(other.Position);
        }
    }

    public class Position
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
