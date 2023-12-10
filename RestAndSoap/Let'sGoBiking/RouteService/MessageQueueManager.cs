using Apache.NMS;
using Apache.NMS.ActiveMQ;
using System;
using System.Collections.Generic;

namespace RouteService
{
    internal class MessageQueueManager : IDisposable
    {
        private ConnectionFactory connectionFactory;
        private Uri brokerUri;
        private IConnection connection;

        public MessageQueueManager(string brokerString)
        {
            brokerUri = new Uri(brokerString);
            connectionFactory = new ConnectionFactory(brokerUri);
            connection = connectionFactory.CreateConnection();
            connection.Start();
        }

        public void Send(string queueName, List<string> serializedMessages)
        {
            // Create a session and a producer
            var session = connection.CreateSession();
            var destination = session.GetQueue(queueName);
            var producer = session.CreateProducer(destination);
            producer.DeliveryMode = MsgDeliveryMode.NonPersistent;

            // Send each message
            foreach (var serializedMessage in serializedMessages)
            {
                var message = session.CreateTextMessage(serializedMessage);
                producer.Send(message);
            }

            // Close the producer and session
            producer.Close();
            session.Close();
        }
        public void Dispose()
        {
            connection.Close();
        }
    }
}
