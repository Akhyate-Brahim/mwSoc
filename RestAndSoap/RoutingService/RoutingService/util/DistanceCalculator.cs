using RouteService.ProxyServ;
using System;
using System.Collections.Generic;

namespace RoutingService.util
{
    internal class DistanceCalculator
    {
        private const double EarthRadius = 6371.0;

        public static double CalculateDistance(Position position1, Position position2)
        {
            double lat1Radians = ToRadians(position1.Lat);
            double lat2Radians = ToRadians(position2.Lat);
            double deltaLat = ToRadians(position2.Lat - position1.Lat);
            double deltaLon = ToRadians(position2.Lng - position1.Lng);

            double a = Math.Sin(deltaLat / 2) * Math.Sin(deltaLat / 2) +
                       Math.Cos(lat1Radians) * Math.Cos(lat2Radians) *
                       Math.Sin(deltaLon / 2) * Math.Sin(deltaLon / 2);
            double c = 2 * Math.Atan2(Math.Sqrt(a), Math.Sqrt(1 - a));
            return EarthRadius * c;
        }

        private static double ToRadians(double angleInDegrees)
        {
            return angleInDegrees * (Math.PI / 180.0);
        }
        public static Position FindClosestPosition(Position targetPosition, IEnumerable<Position> positions)
        {
            Position closest = null;
            double minDistance = double.MaxValue;

            foreach (var position in positions)
            {
                double distance = CalculateDistance(targetPosition, position);
                if (distance < minDistance)
                {
                    minDistance = distance;
                    closest = position;
                }
            }

            return closest;
        }
    }

}
