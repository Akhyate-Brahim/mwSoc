using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;

namespace ProxyCacheService
{
    [ServiceContract]
    interface IProxyService
    {
        [OperationContract]
        List<Contract> GetContracts();
        [OperationContract]
        List<Station> GetStationsForContract(string contractName);
        [OperationContract]
        Dictionary<string, Position> GetAllContractCenters();
    }


}
