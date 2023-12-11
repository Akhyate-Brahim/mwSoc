using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.ServiceModel;
using System.ServiceModel.Description;
using Apache.NMS.ActiveMQ;
using Apache.NMS;
using System.Threading;

namespace RouteService
{
    internal class Program
    {
        static async Task Main(string[] args)
        {
            Uri httpUrl = new Uri("http://localhost:8734/LetsGoBiking/RouteService/");
            ServiceHost host = new ServiceHost(typeof(RoutingService), httpUrl);

            // Create a BasicHttpBinding instance with increased message size
            BasicHttpBinding binding = new BasicHttpBinding();
            IRoutingService routingService = new RoutingService();
            host.AddServiceEndpoint(typeof(IRoutingService), binding, "");

            ServiceMetadataBehavior smb = new ServiceMetadataBehavior();
            smb.HttpGetEnabled = true;
            host.Description.Behaviors.Add(smb);

            //Start the Service
            host.Open();

            Console.WriteLine("Service is host at " + httpUrl);
            Console.WriteLine("Host is running... Press <Enter> key to stop");
            Console.ReadLine();
        }
    }
}
