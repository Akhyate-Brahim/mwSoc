using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.IO;
using System.Net;
using System.Reflection;
using System.Text;
using System.Web;

namespace BasicServerHTTPlistener
{
    internal class Program
    {
        private static void Main(string[] args)
        {

            //if HttpListener is not supported by the Framework
            if (!HttpListener.IsSupported)
            {
                Console.WriteLine("A more recent Windows version is required to use the HttpListener class.");
                return;
            }


            // Create a listener.
            HttpListener listener = new HttpListener();

            // Add the prefixes.
            if (args.Length != 0)
            {
                foreach (string s in args)
                {
                    listener.Prefixes.Add(s);
                    // don't forget to authorize access to the TCP/IP addresses localhost:xxxx and localhost:yyyy 
                    // with netsh http add urlacl url=http://localhost:xxxx/ user="Tout le monde"
                    // and netsh http add urlacl url=http://localhost:yyyy/ user="Tout le monde"
                    // user="Tout le monde" is language dependent, use user=Everyone in english 

                }
            }
            else
            {
                Console.WriteLine("Syntax error: the call must contain at least one web server url as argument");
            }
            listener.Start();

            // get args 
            foreach (string s in args)
            {
                Console.WriteLine("Listening for connections on " + s);
            }

            // Trap Ctrl-C on console to exit 
            Console.CancelKeyPress += delegate
            {
                // call methods to close socket and exit
                listener.Stop();
                listener.Close();
                Environment.Exit(0);
            };


            while (true)
            {
                // Note: The GetContext method blocks while waiting for a request.
                HttpListenerContext context = listener.GetContext();
                HttpListenerRequest request = context.Request;

                string documentContents;
                using (Stream receiveStream = request.InputStream)
                {
                    using (StreamReader readStream = new StreamReader(receiveStream, Encoding.UTF8))
                    {
                        documentContents = readStream.ReadToEnd();
                    }
                }

                // get url 
                Console.WriteLine($"Received request for {request.Url}");

                //get url protocol
                Console.WriteLine(request.Url.Scheme);
                //get user in url
                Console.WriteLine(request.Url.UserInfo);
                //get host in url
                Console.WriteLine(request.Url.Host);
                //get port in url
                Console.WriteLine(request.Url.Port);
                //get path in url 
                Console.WriteLine(request.Url.LocalPath);

                // parse path in url 
                foreach (string str in request.Url.Segments)
                {
                    Console.WriteLine(str);
                }

                //get params un url. After ? and between &

                Console.WriteLine(request.Url.Query);

                //parse params in url
                Console.WriteLine("param1 = " + HttpUtility.ParseQueryString(request.Url.Query).Get("param1"));
                Console.WriteLine("param2 = " + HttpUtility.ParseQueryString(request.Url.Query).Get("param2"));
                Console.WriteLine("param3 = " + HttpUtility.ParseQueryString(request.Url.Query).Get("param3"));
                Console.WriteLine("param4 = " + HttpUtility.ParseQueryString(request.Url.Query).Get("param4"));

                // >>>>>>>>> START OF REFLECTION-BASED CODE >>>>>>>>>

                // Extracting the method name from the URL
                string methodName = request.Url.Segments[request.Url.Segments.Length - 1].Trim('/');

                MyMethods myMethods = new MyMethods();
                MethodInfo methodToInvoke = myMethods.GetType().GetMethod(methodName);

                string responseString;
                if (methodToInvoke != null)
                {
                    string param1 = HttpUtility.ParseQueryString(request.Url.Query).Get("param1");
                    string param2 = HttpUtility.ParseQueryString(request.Url.Query).Get("param2");

                    responseString = (string)methodToInvoke.Invoke(myMethods, new string[] { param1, param2 });
                }
                else
                {
                    // Handle case where method does not exist or URL is malformed
                    Console.WriteLine($"Method {methodName} not found.");
                    responseString = "<HTML><BODY> Hello world!</BODY></HTML>"; // default error message
                }

                // >>>>>>>>>> END OF REFLECTION-BASED CODE >>>>>>>>>>>

                // Construct a response.
                //
                Console.WriteLine(documentContents);

                // Obtain a response object.
                HttpListenerResponse response = context.Response;

                // Construct a response.
                byte[] buffer = System.Text.Encoding.UTF8.GetBytes(responseString);
                // Get a response stream and write the response to it.
                response.ContentLength64 = buffer.Length;
                System.IO.Stream output = response.OutputStream;
                output.Write(buffer, 0, buffer.Length);
                // You must close the output stream.
                output.Close();
            }
            // Httplistener neither stop ... But Ctrl-C do that ...
            // listener.Stop();
        }
    }
    internal class MyMethods
    {
        public string Method1(string param1, string param2)
        {
            return $"<html><body>Hello {param1} and {param2}</body></html>";
        }

        public string Foobar(string param1, string param2)
        {
            return $"<html><body>{param1} meets {param2} in Foobar!</body></html>";
        }

        public string Greetings(string param1, string param2)
        {
            return $"<html><body>{param1} sends greetings to {param2}!</body></html>";
        }
        public string RunExternalProgram(string param1, string param2)
        {
            string output = string.Empty;

            // Setup the process with ProcessStartInfo
            ProcessStartInfo startInfo = new ProcessStartInfo
            {
                FileName = @"C:\Users\ibrah\OneDrive\Bureau\MWSOC\mwSockets\SOC\TD1\GenerateHTML\bin\Debug\net6.0\GenerateHTML.exe",
                Arguments = $"{param1} {param2}",
                RedirectStandardOutput = true,
                UseShellExecute = false,
                CreateNoWindow = true
            };

            // Start the process
            using (Process process = Process.Start(startInfo))
            {
                // Read the output (which will be the HTML)
                output = process.StandardOutput.ReadToEnd();
            }

            return output;
        }
        public string ReturnJsonData(string param1, string param2)
        {
            var data = new
            {
                Argument1 = param1,
                Argument2 = param2,
                Message = "This is a sample JSON response"
            };

            return JsonConvert.SerializeObject(data);
        }
    }
}