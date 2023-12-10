using System.Collections.Generic;
using System.Globalization;
using System.ServiceModel;
using System;
using RouteService.ExternalClients;
using RouteService.ProxyServ;
using Contract = RouteService.ProxyServ.Contract;
using System.Linq;
using RouteService.util;
using Newtonsoft.Json;
using System.Threading.Tasks;
using System.Threading;

namespace RouteService
{
    [ServiceBehavior(IncludeExceptionDetailInFaults = true)]
    class RoutingService : IRoutingService
    {
        static ProxyServiceClient proxyServiceClient = new ProxyServiceClient();
        private const string Cycling = "cycling-regular/";
        private const string Walking = "foot-walking/";
        static private List<Contract> contracts;

        public async Task<List<RouteSegment>> GetItinerary(string departure, string destination)
        {
            Position departureCoord = await OSMClient.GetLocation(departure);
            Position destinationCoord = await OSMClient.GetLocation(destination);

            string departureCoordString = PositionToString(departureCoord);
            string destinationCoordString = PositionToString(destinationCoord);

            // let's calculate the departure and destination stations
            Contract closestContractDeparture = await FindClosestContractAsync(departureCoord);
            Contract closestContractDestination = await FindClosestContractAsync(destinationCoord);
            Station closestStationDeparture = await FindClosestStationAsync(departureCoord, closestContractDeparture, false);
            Station closestStationDestination = await FindClosestStationAsync(destinationCoord, closestContractDestination, true);

            string departureStationCoordString = PositionToString(closestStationDeparture.position);
            string destinationStationCoordString = PositionToString(closestStationDestination.position);

            // let's find the first on foot itinerary
            RouteSegment walkingToStation = await ORSClient.GetRoute(departureCoordString, departureStationCoordString, Walking);

            // let's find the cycling itinerary
            RouteSegment cycling = await ORSClient.GetRoute(departureStationCoordString, destinationStationCoordString, Cycling);

            // let's find the final on foot itinerary
            RouteSegment walkingFromStation = await ORSClient.GetRoute(destinationStationCoordString, destinationCoordString, Walking);

            // calculate the full itinerary on foot
            RouteSegment fullWalking = await ORSClient.GetRoute(departureCoordString, destinationCoordString, Walking);

            // preparing return
            List<RouteSegment> routes = new List<RouteSegment> { walkingToStation, cycling, walkingFromStation };
            List<RouteSegment> walkingReturnList = new List<RouteSegment> { fullWalking };
            // return based on duration
            return walkingToStation.duration + cycling.duration + walkingFromStation.duration > fullWalking.duration ?
                walkingReturnList : routes;
        }




        static async Task<Station> FindClosestStationAsync(Position position, Contract contract, bool withBike)
        {
            var stationsArray = proxyServiceClient.GetStationsForContract(contract.name).ToList();
            List<Station> stations = stationsArray
                .Where(station => ((withBike && station.available_bike_stands > 0) || (!withBike && station.available_bikes > 0)))
                .ToList();
            Console.WriteLine(contract.name);
            var closestPositionsQuery = DistanceCalculator.FindThreeClosestPositions(position, stations.Select(s => s.position).ToList());
            List<Position> closestPositions = closestPositionsQuery.ToList();
            double smallestDistance = double.MaxValue;
            Position closestPosition = null;

            foreach (var closestPos in closestPositions)
            {
                double distance = await ORSClient.GetDistance(position, closestPos, Walking);

                if (distance < smallestDistance)
                {
                    smallestDistance = distance;
                    closestPosition = closestPos;
                }
            }
            return stations.FirstOrDefault(station => DistanceCalculator.PositionsAreEqual(station.position, closestPosition));
        }



        static async Task<Contract> FindClosestContractAsync(Position position)
        {
            contracts = proxyServiceClient.GetContracts().ToList();
            string posCity = await OSMClient.GetCityNameFromCoordinates(position.lng, position.lat);
            var contract = FindContractByCity(posCity, contracts);
            if (contract != null)
            {
                return contract;
            }
            Dictionary<string, Position> contractCenters = proxyServiceClient.GetAllContractCenters();
            Position closestPosition = DistanceCalculator.FindClosestPosition(position, contractCenters.Values);
            string closestContractName = contractCenters.FirstOrDefault(kvp => kvp.Value.Equals(closestPosition)).Key;
            Console.WriteLine(closestContractName);
            Contract returnContract = FindContractByName(closestContractName, contracts);
            Station[] stations = proxyServiceClient.GetStationsForContract(closestContractName);
            if (stations == null || stations.Length == 0)
            {
                contracts.Remove(returnContract);
                return await FindClosestContractAsync(position);
            }
            else
            {
                return returnContract;
            }
          
        }

        static Contract FindContractByCity(string cityName, List<Contract> contracts)
        {
            return contracts.FirstOrDefault(contract => contract.cities?.Contains(cityName, StringComparer.OrdinalIgnoreCase) == true);
        }
        public static Contract FindContractByName(string contractName, List<Contract> contracts)
        {
            return contracts.FirstOrDefault(contract => contract.name.Equals(contractName, StringComparison.OrdinalIgnoreCase));
        }


        public static string PositionToString(Position position)
        {
            return String.Format(CultureInfo.InvariantCulture, "{0},{1}", position.lng, position.lat);
        }


    }
}