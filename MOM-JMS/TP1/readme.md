# Message oriented middleware and JMS

## Basic Components

1. **Producer**: The entity that sends messages.
2. **Consumer**: The entity that receives messages.
3. **Message Queue/Topic**: Where messages are temporarily stored.
4. **Broker**: The central server that manages the transmission of messages.

## Broker's Role

The broker sits in the middle of your messaging architecture, acting like a post office. It receives messages from producers and routes them to the appropriate queues or topics, from which consumers can then pick up the messages.

## How it Works

### Point-to-Point (Queue)

- A producer sends a message to a specific queue.
- The broker ensures that the message will be delivered to one and only one consumer.
- If multiple consumers are listening, only one will get the message.

### Publish-Subscribe (Topic)

- A producer sends a message to a topic.
- The broker distributes the message to all consumers who have subscribed to that topic.

## Advanced Features

- **Message Persistence**: The broker can store messages on disk so that they survive system failures.
- **Load Balancing**: In a cluster of brokers, workload can be distributed across multiple instances.
- **High Availability**: Failover mechanisms can be implemented for seamless operation even when a broker goes down.
- **Security**: The broker can handle authentication and encryption.
- **Transformation and Routing**: Some brokers can transform message formats and route messages dynamically based on content.

## In practice

## Maven Dependencies
For your `pom.xml`:
```xml
<dependency>
<groupId>org.apache.activemq</groupId>
<artifactId>activemq-client</artifactId>
<version>5.16.0</version>
</dependency>
```

### Apache ActiveMQ Specific Methods

1. **`ActiveMQConnectionFactory("tcp://localhost:61616")`**
    - Creates a connection factory with the specified broker URL.

### JMS API Methods

1. **`createConnection()`**
    - Initiates a connection to the message broker.

2. **`connection.start()`**
    - Starts the connection's delivery of incoming messages.

3. **`createSession(false, Session.AUTO_ACKNOWLEDGE)`**
    - Creates a new session for message production and consumption.
        - **Transactional**: `false`
        - **Acknowledgment Mode**: `Session.AUTO_ACKNOWLEDGE`

4. **`createQueue("MyQueue")`**
    - Creates a new queue named "MyQueue".

5. **`createProducer(queue)`**
    - Creates a new message producer for sending messages to the specified queue.

6. **`createTextMessage("Hello, World!")`**
    - Creates a new text message with the specified content.

7. **`producer.send(textMessage)`**
    - Sends the message to the designated queue.

8. **`createConsumer(queue)`**
    - Creates a new message consumer for the specified queue.

9. **`consumer.setMessageListener(example)`**
    - Sets the message listener for the consumer, enabling it to reactively handle incoming messages.

10. **`onMessage(Message message)`**
    - An implementation of the `MessageListener` interface.
    - Called when a message is received.

