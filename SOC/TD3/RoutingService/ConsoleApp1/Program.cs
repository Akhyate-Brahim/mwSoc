using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ConsoleApp1
{
    internal class Program
    {
        static async Task Main(string[] args)
        {
            double latitude = 43.6155793;  // Example latitude
            double longitude = 7.0718748; // Example longitude
            Position position = new Position
            {
                Lng = longitude,
                Lat = latitude
            };
            try
            {
                string cityName = await OSMClient.GetCityName(position);
                Console.WriteLine(cityName);
            }
            catch (Exception ex)
            {
                Console.WriteLine($"An error occurred: {ex.Message}");
            }
            Console.ReadLine();
        }
    }
}
