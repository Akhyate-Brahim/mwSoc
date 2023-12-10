package com.soap.client.util;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Sender {
    private static String brokerUrl = "tcp://localhost:61616"; // Default broker URL
    private static ConnectionFactory factory = new ActiveMQConnectionFactory(brokerUrl);
    private Sender() {
    }

    public static void setBrokerUrl(String url) {
        brokerUrl = url;
        factory = new ActiveMQConnectionFactory(brokerUrl);
    }

    public static void sendMessage(String queueName, String messageText) throws JMSException {
        try (Connection connection = factory.createConnection();
             Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
             MessageProducer producer = session.createProducer(session.createQueue(queueName))) {

            connection.start();
            TextMessage message = session.createTextMessage(messageText);
            producer.send(message);
        }
    }

}
