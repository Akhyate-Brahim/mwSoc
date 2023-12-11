using System.Collections.Generic;
using System.ServiceModel;
using System.Threading.Tasks;

namespace RouteService
{
    [ServiceContract]
    interface IRoutingService
    {
        [OperationContract]
        Task<List<RouteSegment>> GetItinerary(string departure, string destination);
        [OperationContract]
        Task<string> getQueue(string departure, string destination);
        [OperationContract]
        Task RequestMoreSteps(string queueName);
    }
}