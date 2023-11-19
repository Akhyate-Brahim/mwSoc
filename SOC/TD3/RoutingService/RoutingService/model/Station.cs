using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoutingService.model
{
    class Station
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
}
