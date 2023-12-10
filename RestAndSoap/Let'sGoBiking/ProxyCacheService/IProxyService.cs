using static System.Collections.Specialized.BitVector32;
using System.Collections.Generic;
using System.Diagnostics.Contracts;
using System.ServiceModel;
using System.Threading.Tasks;

namespace ProxyCacheService
{
    [ServiceContract]
    interface IProxyService
    {
        [OperationContract]
        Task<List<Contract>> GetContracts();
        [OperationContract]
        Task<List<Station>> GetStationsForContract(string contractName);
        [OperationContract]
        Task<Dictionary<string, Position>> GetAllContractCenters();
    }
}