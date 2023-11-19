using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using WS_SOAPClient.CalculatorServiceReference;

namespace WS_SOAPClient
{
    internal class SoapClient
    {
        static void Main(string[] args)
        {
            CalculatorSoapClient calculatorClient = new CalculatorSoapClient();
            int sum = calculatorClient.Add(5, 3);
            Console.WriteLine($"5 + 3 = {sum}");

            
            int difference = calculatorClient.Subtract(5, 3);
            Console.WriteLine($"5 - 3 = {difference}");

           
            int product = calculatorClient.Multiply(5, 3);
            Console.WriteLine($"5 * 3 = {product}");

          
            int quotient = calculatorClient.Divide(10, 2);
            Console.WriteLine($"10 / 2 = {quotient}");

            calculatorClient.Close();
            Console.ReadLine();


        }
    }
}
