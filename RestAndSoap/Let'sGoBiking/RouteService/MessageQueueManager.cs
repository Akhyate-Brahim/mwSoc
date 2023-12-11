using Apache.NMS;
using Apache.NMS.ActiveMQ;
using System;
using System.Collections.Generic;

namespace RouteService
{
    internal class MessageQueueManager : IDisposable
    {
        private ConnectionFactory connectionFactory;
        private IConnection connection;
        private ISession session;
        private Dictionary<string, IMessageProducer> producers = new Dictionary<string, IMessageProducer>();

        public MessageQueueManager(string brokerString)
        {
            Uri brokerUri = new Uri(brokerString);
            connectionFactory = new ConnectionFactory(brokerUri);
            connection = connectionFactory.CreateConnection();
            connection.Start();
            session = connection.CreateSession();
        }

        public IMessageProducer CreateProducer(string queueName)
        {
            if (producers.TryGetValue(queueName, out var existingProducer))
            {
                return existingProducer;
            }

            var destination = session.GetQueue(queueName);
            var producer = session.CreateProducer(destination);
            producer.DeliveryMode = MsgDeliveryMode.Persistent;
            producers[queueName] = producer;

            return producer;
        }

        public void Send(string queueName, List<string> serializedMessages, IMessageProducer producer)
        {
            try
            {
                var destination = session.GetQueue(queueName);

                foreach (var serializedMessage in serializedMessages)
                {
                    var message = session.CreateTextMessage(serializedMessage);
                    producer.Send(message);
                }
            }
            catch (Exception ex)
            {
                throw;
            }
        }

        public void Dispose()
        {
            // Dispose all producers
            foreach (var producer in producers.Values)
            {
                producer.Close();
            }

            session?.Close();
            connection?.Close();
        }
    }
}
