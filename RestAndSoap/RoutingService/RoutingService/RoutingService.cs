using RouteService.ExternalClients;
using RouteService.ProxyServ;
using RoutingService.util;
using System;
using System.Collections.Generic;
using System.Linq;
using System.ServiceModel;
using System.Globalization;

namespace RouteService
{
    [ServiceBehavior(IncludeExceptionDetailInFaults = true)]
    class RoutingService : IRoutingService
    {
        static ProxyServiceClient proxyServiceClient = new ProxyServiceClient();
        private const string Cycling = "cycling-regular/";
        private const string Walking = "foot-walking/";


        public List<RouteSegment> GetItinerary(string departure, string destination)
        {
            Position departureCoord = OSMClient.GetLocation(departure);
            Position destinationCoord = OSMClient.GetLocation(destination);
            
            string departureCoordString = PositionToString(departureCoord);
            string destinationCoordString = PositionToString(destinationCoord);
            // let's calculate the departure and destination stations
            Contract closestContractDeparture = FindClosestContract(departureCoord);
            Contract closestContractDestination = FindClosestContract(destinationCoord);
            Station closestStationDeparture = FindClosestStation(departureCoord, closestContractDeparture, false);
            Station closestStationDestination = FindClosestStation(destinationCoord, closestContractDestination, true);
            
            // error
            string departureStationCoordString = PositionToString(closestStationDeparture.Position); // closest station null??
            string destinationStationCoordString = PositionToString(closestStationDestination.Position);

            // let's find the first on foot itinerary
            RouteSegment walkingToStation = ORSClient.GetRoute(departureCoordString, departureStationCoordString, Walking);

            // let's find the cycling itinerary
            RouteSegment cycling = ORSClient.GetRoute(departureStationCoordString, destinationStationCoordString, Cycling);

            // let's find the final on foot itinerary
            RouteSegment walkingFromStation = ORSClient.GetRoute(destinationStationCoordString, destinationCoordString, Walking);

            // calculate the full itinerary on foot
            RouteSegment fullWalking = ORSClient.GetRoute(departureCoordString, destinationCoordString, Walking);

            // preparing return
            List<RouteSegment> routes = new List<RouteSegment> { walkingToStation, cycling, walkingFromStation };
            List<RouteSegment> walkingReturnList = new List<RouteSegment> { fullWalking };

            // return based on duration
            return walkingToStation.duration + cycling.duration + walkingFromStation.duration > fullWalking.duration ?
                walkingReturnList : routes;
        }




        static Station FindClosestStation(Position position, Contract contract, bool withBike)
        {
            var stationsArray = proxyServiceClient.GetStationsForContract(contract.Name);
            List<Station> stations = stationsArray.ToList();
            // stations = stations.Where(station => station.AvailableBikes > 0 && station.AvailableBikeStands > 0).ToList();
            if (stations == null || stations.Count == 0)
            {
                throw new Exception("ZABA"); // error here stations are null for some reason
            }

            List<Position> positions = stations.Select(station => station.Position).ToList();
            Position closestPosition = DistanceCalculator.FindClosestPosition(position, positions);

            foreach (Station station in stations)
            {
                if (PositionsAreEqual(station.Position, closestPosition))
                {
                    return station;
                }
            }

            return null;
        }


        static Contract FindClosestContract(Position position)
        {
            List<Contract> contracts = proxyServiceClient.GetContracts().ToList();
            string posCity = OSMClient.GetCityNameFromCoordinates(position.Lng, position.Lat);
            var contract = FindContractByCity(posCity, contracts);
            if (contract != null)
            {
                return contract;
            }
            Dictionary<string, Position> contractCenters = proxyServiceClient.GetAllContractCenters();
            Position closestPosition = DistanceCalculator.FindClosestPosition(position, contractCenters.Values);
            string closestContractName = contractCenters.FirstOrDefault(kvp => kvp.Value.Equals(closestPosition)).Key;
            return FindContractByName(closestContractName, contracts);
        }

        static Contract FindContractByCity(string cityName, List<Contract> contracts)
        {
            return contracts.FirstOrDefault(contract => contract.Cities?.Contains(cityName, StringComparer.OrdinalIgnoreCase) == true);
        }
        public static Contract FindContractByName(string contractName, List<Contract> contracts)
        {
            return contracts.FirstOrDefault(contract => contract.Name.Equals(contractName, StringComparison.OrdinalIgnoreCase));
        }

        public static Station FindStationByNumber(int number, List<Station> stations)
        {
            return stations.FirstOrDefault(station => station.Number == number);
        }
        public static string PositionToString(Position position)
        {
            return String.Format(CultureInfo.InvariantCulture, "{0},{1}", position.Lng, position.Lat);
        }
        private const double Tolerance = 1e-6; // Example tolerance value

        private static bool PositionsAreEqual(Position pos1, Position pos2)
        {
            return Math.Abs(pos1.Lat - pos2.Lat) < Tolerance && Math.Abs(pos1.Lng - pos2.Lng) < Tolerance;
        }


    }
}
