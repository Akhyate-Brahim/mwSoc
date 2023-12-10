using System.Collections.Generic;
using System.Diagnostics.Contracts;
using System;
using System.Threading.Tasks; // Include this for async Task

namespace ProxyCacheService
{
    class ProxyService : IProxyService
    {
        const string apiKey = "b1a5bf2860e859e32770061a4cdd5e222a7e9e96";
        const string baseUrl = "https://api.jcdecaux.com/vls/v1/";
        static readonly GenericCache<List<Contract>> _contractsCache = new GenericCache<List<Contract>>();
        static readonly GenericCache<List<Station>> _stationsCache = new GenericCache<List<Station>>();

        public async Task<List<Contract>> GetContracts()
        {
            string url = $"{baseUrl}contracts?apiKey={apiKey}";
            return await _contractsCache.GetData(url, DateTimeOffset.UtcNow.AddHours(5));
        }

        public async Task<List<Station>> GetStationsForContract(string contractName)
        {
            string url = $"{baseUrl}stations?contract={contractName}&apiKey={apiKey}";
            return await _stationsCache.GetData(url, 300);
        }

        public async Task<Dictionary<string, Position>> GetAllContractCenters()
        {
            var contractCenters = new Dictionary<string, Position>();
            var contracts = await GetContracts();

            foreach (var contract in contracts)
            {
                Position center = await GetContractCenter(contract.Name);
                if (center != null)
                {
                    contractCenters[contract.Name] = center;
                }
            }
            return contractCenters;
        }

        public async Task<Position> GetContractCenter(string contractName)
        {
            List<Station> stations = await GetStationsForContract(contractName);
            if (stations == null || stations.Count == 0)
            {
                return null;
            }
            Random random = new Random();
            Station randomStation = stations[random.Next(stations.Count)];

            return randomStation.Position;
        }


    }
}