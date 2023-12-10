using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;
using System.Threading.Tasks;

namespace RoutingService.model
{
    [DataContract]
    class Contract
    {
        [DataMember]
        public string Name { get; set; }
        [DataMember]
        public string CommercialName { get; set; }
        [DataMember]
        public List<string> Cities { get; set; }
        [DataMember]
        public string CountryCode { get; set; }
    }
}
