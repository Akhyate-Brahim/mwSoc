using RouteService.ProxyServ;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RouteService.util
{
    internal class DistanceCalculator
    {
        private const double EarthRadius = 6371.0;

        public static double CalculateDistance(Position position1, Position position2)
        {
            double lat1Radians = ToRadians(position1.lat);
            double lat2Radians = ToRadians(position2.lat);
            double deltaLat = ToRadians(position2.lat - position1.lat);
            double deltaLon = ToRadians(position2.lng - position1.lng);

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
        public static IEnumerable<Position> FindThreeClosestPositions(Position targetPosition, IEnumerable<Position> positions)
        {
            return positions
                .Select(position => new { Position = position, Distance = CalculateDistance(targetPosition, position) })
                .OrderBy(p => p.Distance)
                .Take(3)
                .Select(p => p.Position);
        }
        private const double Tolerance = 1e-6;

        public static bool PositionsAreEqual(Position pos1, Position pos2)
        {
            return Math.Abs(pos1.lat - pos2.lat) < Tolerance && Math.Abs(pos1.lng - pos2.lng) < Tolerance;
        }
    }
}
