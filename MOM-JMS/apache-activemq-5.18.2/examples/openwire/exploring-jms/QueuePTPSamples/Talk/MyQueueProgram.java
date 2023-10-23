package Talk;
import javax.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MyQueueProgram implements MessageListener {
    private Connection connect = null;
    private Session sendSession = null;
    private Session receiveSession = null;
    private MessageProducer sender = null;

    public static void main(String[] args) {
        MyQueueProgram myProgram = new MyQueueProgram();
        myProgram.run();
    }

    public void run() {
        try {
            // Step 4: Create Connection
            ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
            connect = factory.createConnection();

            // Step 5: Producer Code
            sendSession = connect.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue sendQueue = sendSession.createQueue("MyQueue");
            sender = sendSession.createProducer(sendQueue);

            // Step 6: Consumer Code
            receiveSession = connect.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue receiveQueue = receiveSession.createQueue("MyQueue");
            MessageConsumer consumer = receiveSession.createConsumer(receiveQueue);
            consumer.setMessageListener(this);

            // Start the connection
            connect.start();

            // Step 7: Sending Messages
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                System.out.println("Enter a message to send (or 'exit' to quit): ");
                String msgToSend = reader.readLine();
                if ("exit".equalsIgnoreCase(msgToSend)) {
                    break;
                }
                TextMessage message = sendSession.createTextMessage();
                message.setText(msgToSend);
                sender.send(message);
            }
        } catch (JMSException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                connect.close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }

    // Step 8: Receiving Messages
    @Override
    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            try {
                System.out.println("Received: " + ((TextMessage) message).getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}
