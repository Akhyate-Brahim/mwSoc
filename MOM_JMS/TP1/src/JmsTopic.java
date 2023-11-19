import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.Topic;


public class JmsTopic {

    public static void main(String[] args) throws JMSException {
        javax.jms.ConnectionFactory factory;
        factory = new org.apache.activemq.ActiveMQConnectionFactory("user", "password", "tcp://localhost:61616");
        javax.jms.Connection connect = factory.createConnection ("user", "password");
        connect.setClientID("user");

        Session receiveSession = connect.createSession(false,javax.jms.Session.AUTO_ACKNOWLEDGE);
        Topic t=receiveSession.createTopic("TP2Exo2");


        javax.jms.MessageConsumer souscripteur=receiveSession.createDurableSubscriber(t,"user");
        connect.start();
        int i=0;
        while (i<1000) {
            Message m=souscripteur.receive(); // ici la reception est synchrone, mais on peut aussi avoir un listener onMessage()
            System.out.println("recu du topic " + ((javax.jms.TextMessage) m).getText());
            i++;
        }

    }

}
