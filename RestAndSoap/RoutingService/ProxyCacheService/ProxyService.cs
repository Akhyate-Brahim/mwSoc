using System.Collections.Generic;

namespace ProxyCacheService
{
    class ProxyService : IProxyService
    {
        const string apiKey = "b1a5bf2860e859e32770061a4cdd5e222a7e9e96";
        const string baseUrl = "https://api.jcdecaux.com/vls/v1/";
        static readonly GenericCache<List<Contract>> _contractsCache = new GenericCache<List<Contract>>();
        static readonly GenericCache<List<Station>> _stationsCache = new GenericCache<List<Station>>();

        public List<Contract> GetContracts()
        {
            string url = $"{baseUrl}contracts?apiKey={apiKey}";
            return _contractsCache.GetData(url);
        }

        public List<Station> GetStationsForContract(string contractName)
        {
            string url = $"{baseUrl}stations?contract={contractName}&apiKey={apiKey}";
            return _stationsCache.GetData(url);
        }
        public Dictionary<string, Position> GetAllContractCenters()
        {
            var contractCenters = new Dictionary<string, Position>();

            foreach (var contract in GetContracts())
            {
                Position center = GetContractCenter(contract.Name);
                if (center != null)
                {
                    contractCenters[contract.Name] = center;
                }
            }

            return contractCenters;
        }

        public Position GetContractCenter(string contractName)
        {
            List<Station> stations = GetStationsForContract(contractName);
            if (stations == null || stations.Count == 0)
            {
                return null;
            }

            double totalLat = 0;
            double totalLng = 0;

            foreach (Station station in stations)
            {
                totalLat += station.Position.Lat;
                totalLng += station.Position.Lng;
            }

            double averageLat = totalLat / stations.Count;
            double averageLng = totalLng / stations.Count;

            return new Position { Lat = averageLat, Lng = averageLng };
        }

    }
}
