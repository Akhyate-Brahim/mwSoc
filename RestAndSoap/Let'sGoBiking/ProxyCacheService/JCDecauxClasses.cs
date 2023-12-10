using System;
using System.Collections.Generic;
using System.Runtime.Serialization;

namespace ProxyCacheService
{
    [DataContract]
    class Contract
    {
        [DataMember(Name = "name")]
        public string Name { get; set; }

        [DataMember(Name = "commercial_name")]
        public string CommercialName { get; set; }

        [DataMember(Name = "cities")]
        public List<string> Cities { get; set; }

        [DataMember(Name = "country_code")]
        public string CountryCode { get; set; }

        public override string ToString()
        {
            var citiesString = Cities != null ? string.Join(", ", Cities) : "N/A";
            return $"Name: {Name}, CommercialName: {CommercialName}, Cities: {citiesString}, CountryCode: {CountryCode}";
        }

    }

    [DataContract]
    public class Position
    {
        [DataMember(Name = "lat")]
        public double Lat { get; set; }

        [DataMember(Name = "lng")]
        public double Lng { get; set; }

        public override string ToString()
        {
            return $"Latitude: {Lat}, Longitude: {Lng}";
        }
    }

    [DataContract]
    class Station
    {
        [DataMember(Name = "number")]
        public int Number { get; set; }

        [DataMember(Name = "contract_name")]
        public string ContractName { get; set; }

        [DataMember(Name = "name")]
        public string Name { get; set; }

        [DataMember(Name = "address")]
        public string Address { get; set; }

        [DataMember(Name = "position")]
        public Position Position { get; set; }

        [DataMember(Name = "banking")]
        public bool Banking { get; set; }

        [DataMember(Name = "bonus")]
        public bool Bonus { get; set; }

        [DataMember(Name = "bike_stands")]
        public int BikeStands { get; set; }

        [DataMember(Name = "available_bike_stands")]
        public int AvailableBikeStands { get; set; }

        [DataMember(Name = "available_bikes")]
        public int AvailableBikes { get; set; }

        [DataMember(Name = "status")]
        public string Status { get; set; }

        [DataMember(Name = "last_update")]
        public long? LastUpdate { get; set; }

        public override string ToString()
        {
            return $"Number: {Number}, ContractName: {ContractName}, Name: {Name}, Address: {Address}, Position: {Position}, Banking: {Banking}, Bonus: {Bonus}, BikeStands: {BikeStands}, AvailableBikeStands: {AvailableBikeStands}, AvailableBikes: {AvailableBikes}, Status: {Status}, LastUpdate: {LastUpdate}";
        }
    }
}
