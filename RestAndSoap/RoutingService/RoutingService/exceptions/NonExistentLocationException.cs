using System;
using System.Runtime.Serialization;

namespace RouteService.ExternalClients
{
    [Serializable]
    internal class NonExistentLocationException : Exception
    {
        public NonExistentLocationException()
        {
        }

        public NonExistentLocationException(string message) : base(message)
        {
        }

        public NonExistentLocationException(string message, Exception innerException) : base(message, innerException)
        {
        }

        protected NonExistentLocationException(SerializationInfo info, StreamingContext context) : base(info, context)
        {
        }
    }
}