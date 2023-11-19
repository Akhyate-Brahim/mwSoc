using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace BasicWebServer
{
    internal class Class1
    {
        static void Main(string[] args)
        {
            if (args.Length < 2)
            {
                Console.WriteLine("Please provide two arguments.");
                return;
            }

            string param1 = args[0];
            string param2 = args[1];

            Console.WriteLine($"<html><body>Argument 1: {param1}, Argument 2: {param2}</body></html>");
        }
    }
}
