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
using Apache.NMS;

namespace RouteService
{
    [ServiceBehavior(IncludeExceptionDetailInFaults = true)]
    class RoutingService : IRoutingService
    {
        static readonly ProxyServiceClient proxyServiceClient = new ProxyServiceClient();
        private const string Cycling = "cycling-regular/";
        private const string Walking = "foot-walking/";
        static private List<Contract> contracts;
        private readonly MessageQueueManager messageQueueManager = new MessageQueueManager("activemq:tcp://localhost:61616");
        private static Dictionary<string, (IMessageProducer producer, int stepsSent, string departure, string destination)> clientQueues = new Dictionary<string, (IMessageProducer producer, int stepsSent, string departure, string destination)>();


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

        public async Task<List<RouteSegment>> GetLimitedItinerary(string departure, string destination, int stepLimit)
        {
            // First, get the full itinerary using the existing GetItinerary method
            List<RouteSegment> fullItinerary = await GetItinerary(departure, destination);

            // Initialize a list to hold the limited segments
            List<RouteSegment> limitedSegments = new List<RouteSegment>();

            int totalSteps = 0;
            foreach (var segment in fullItinerary)
            {
                double totalDistance = 0;
                double totalDuration = 0;
                var limitedSteps = segment.steps.Take(stepLimit - totalSteps).ToList();

                // Initialize a list for the limited route positions
                List<Position> limitedRoutePositions = new List<Position>();

                int lastWaypointIndex = 0;
                foreach (var step in limitedSteps)
                {
                    totalDistance += step.Distance;
                    totalDuration += step.Duration;

                    // Get the range of coordinates for this step
                    var stepCoordinates = segment.routePositions.GetRange(step.WayPoints[0], step.WayPoints[1] - step.WayPoints[0] + 1);

                    // Add these coordinates to the limited route positions, avoiding duplicates
                    if (step.WayPoints[0] <= lastWaypointIndex)
                    {
                        stepCoordinates = stepCoordinates.Skip(1).ToList(); // Skip the first coordinate if it's a duplicate
                    }
                    limitedRoutePositions.AddRange(stepCoordinates);
                    lastWaypointIndex = step.WayPoints[1];
                }

                RouteSegment limitedSegment = new RouteSegment
                {
                    distance = totalDistance,
                    duration = totalDuration,
                    routePositions = limitedRoutePositions,
                    steps = limitedSteps
                };
                limitedSegments.Add(limitedSegment);

                totalSteps += limitedSteps.Count;
                if (totalSteps >= stepLimit)
                {
                    break; // Stop processing further segments as the limit is reached
                }
            }

            return limitedSegments;
        }
        public async Task<string> getQueue(string departure, string destination)
        {
            var queueName = Guid.NewGuid().ToString();

            // Start with the first 10 steps
            List<RouteSegment> routeSegments = await GetLimitedItinerary(departure, destination, 10);
            string serializedSegments = JsonConvert.SerializeObject(routeSegments);

            // Create a producer for this queue
            IMessageProducer producer = messageQueueManager.CreateProducer(queueName);

            // Save the producer, initial step count, and locations in the dictionary
            clientQueues.Add(queueName, (producer, 10, departure, destination));
            // Send the first set of steps
            messageQueueManager.Send(queueName, new List<string> { serializedSegments }, producer);
            return queueName;
        }

        public async Task RequestMoreSteps(string queueName)
        {
            if (clientQueues.TryGetValue(queueName, out var queueInfo))
            {
                // Increase the number of steps by 10
                int stepsSent = queueInfo.stepsSent + 10;

                // Fetch the itinerary with the increased step count
                List<RouteSegment> routeSegments = await GetLimitedItinerary(queueInfo.departure, queueInfo.destination, stepsSent);
                string serializedSegments = JsonConvert.SerializeObject(routeSegments);

                // Update the step count in the dictionary
                clientQueues[queueName] = (queueInfo.producer, stepsSent, queueInfo.departure, queueInfo.destination);

                // Send the next set of steps
                messageQueueManager.Send(queueName, new List<string> { serializedSegments }, queueInfo.producer);
            }
            else
            {
                throw new Exception("queue not found");
            }
        }


    }
}