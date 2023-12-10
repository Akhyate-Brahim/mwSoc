using System;

namespace RouteService.exceptions
{
    public class ContractNotFoundException : Exception
    {
        
        public ContractNotFoundException()
            : base()
        {
        }

        public ContractNotFoundException(string message)
            : base(message)
        {
        }

        // Constructor with a message and an inner exception
        public ContractNotFoundException(string message, Exception innerException)
            : base(message, innerException) 
        {
        }
    }
}
