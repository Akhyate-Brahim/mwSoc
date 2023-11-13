using MathsLibraryClient.MathsLibraryReference;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MathsLibraryClient
{
    internal class MathsLibraryClient
    {
        static void Main(string[] args)
        {
            MathsOperationsClient client = new MathsOperationsClient();

            try
            {
                int addResult = (int)client.Add(1, 2);
                Console.WriteLine("add result : "+addResult);

                int multiplyResult = (int)client.Multiply(4, 25);
                Console.WriteLine("multiplication result : "+ multiplyResult);
                Console.ReadLine();
                client.Close();


            } catch (Exception ex)
            {
                Console.WriteLine($"An error occurred: {ex.Message}");
                client.Abort();
            }

        }
    }
}
