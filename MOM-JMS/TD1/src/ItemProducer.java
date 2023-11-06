import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class ItemProducer implements AutoCloseable {
    private String baseUrl;
    private ConnectionFactory connectionFactory;
    private Connection connection;
    private Session session;
    private Queue queue;
    private MessageProducer messageProducer;
    private boolean isError;
    private String error;
    private ItemProducer instance;
    private ItemProducer(String baseUrl, String queueName){
        this.baseUrl = baseUrl;
        connectionFactory = new ActiveMQConnectionFactory(this.baseUrl);
        try{
            connection = connectionFactory.createConnection();
        } catch (Exception e){
            isError = true;
            error = e.getMessage();
            return;
        }
        try{
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        } catch (Exception e){
            isError = true;
            error = e.getMessage();
            return;
        }


    }
    @Override
    public void close() throws Exception {

    }
}
