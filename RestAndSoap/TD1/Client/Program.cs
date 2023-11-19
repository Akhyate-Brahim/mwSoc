using System;
using System.Net.Http;
using System.Xml.Linq;
using Newtonsoft.Json.Linq;

namespace JsonClientApp
{
    class Program
    {
        static async System.Threading.Tasks.Task Main(string[] args)
        {
            var client = new HttpClient();

            // Assuming your server is running on localhost:8081
            var response = await client.GetStringAsync("http://localhost:8081/ReturnJsonData?param1=value1&param2=value2");

            // Parse and display the JSON response
            var jsonResponse = JObject.Parse(response);
            Console.WriteLine("Argument 1: " + jsonResponse["Argument1"]);
            Console.WriteLine("Argument 2: " + jsonResponse["Argument2"]);
            Console.WriteLine("Message: " + jsonResponse["Message"]);
        }
    }
}
