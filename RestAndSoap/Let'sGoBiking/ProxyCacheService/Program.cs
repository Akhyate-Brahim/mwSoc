using System;
using System.Collections.Generic;
using System.Linq;
using System.ServiceModel;
using System.ServiceModel.Description;
using System.Text;
using System.Threading.Tasks;

namespace ProxyCacheService
{
    internal class Program
    {
        static async Task Main(string[] args)
        {
            Uri httpUrl = new Uri("http://localhost:8753/LetsGoBiking/ProxyCacheService/");
            ServiceHost host = new ServiceHost(typeof(ProxyService), httpUrl);

            // Create a BasicHttpBinding instance with increased message size
            BasicHttpBinding binding = new BasicHttpBinding();

            host.AddServiceEndpoint(typeof(IProxyService), binding, "");

            ServiceMetadataBehavior smb = new ServiceMetadataBehavior();
            smb.HttpGetEnabled = true;
            host.Description.Behaviors.Add(smb);

            //Start the Service
            host.Open();

            Console.WriteLine("Service is host at " + httpUrl);
            Console.WriteLine("Host is running... Press <Enter> key to stop");
            IProxyService proxyService = new ProxyService();
            var contracts = await proxyService.GetContracts();
            foreach(Contract pos in contracts)
            {
                Console.WriteLine(pos.ToString());
            }
            Console.ReadLine();
        }
    }
}
