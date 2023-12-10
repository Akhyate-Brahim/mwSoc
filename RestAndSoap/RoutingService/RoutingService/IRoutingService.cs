using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;
using System.Threading.Tasks;

namespace RouteService
{
    [ServiceContract]
    interface IRoutingService
    {
        [OperationContract]
        List<RouteSegment> GetItinerary(string departure, string destination);
    }
}