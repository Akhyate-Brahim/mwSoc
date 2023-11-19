using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoutingService.model
{

    class Contract
    {
        public string Name { get; set; }
        public string CommercialName { get; set; }
        public List<string> Cities { get; set; }
        public string CountryCode { get; set; }

        public override string ToString()
        {
            var citiesString = Cities != null ? string.Join(", ", Cities) : "None";
            var commercialNameDisplay = !string.IsNullOrWhiteSpace(CommercialName) ? CommercialName : "N/A";
            var countryCodeDisplay = !string.IsNullOrWhiteSpace(CountryCode) ? CountryCode : "N/A";

            return $"Contract: {Name ?? "Unknown"} ({commercialNameDisplay}), Cities: {citiesString}, Country Code: {countryCodeDisplay}\n";
        }
    }
}
