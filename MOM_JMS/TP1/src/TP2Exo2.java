import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.Topic;


public class TP2Exo2 {

	public static void main(String[] args) throws JMSException {
		javax.jms.ConnectionFactory factory;
        factory = new org.apache.activemq.ActiveMQConnectionFactory("user", "password", "tcp://localhost:61616");
        javax.jms.Connection connect = factory.createConnection ("user", "password");
        connect.setClientID("user"); // identifier de manière unique la connection de "user". 
        // utile que si on utilise des durablesubscriptions.
        
        Session receiveSession = connect.createSession(false,javax.jms.Session.AUTO_ACKNOWLEDGE);  
        Topic t=receiveSession.createTopic("TP2Exo2");
        // javax.jms.MessageConsumer souscripteur=receiveSession.createConsumer(t); 
        // A la place d'un souscripteur, on crée un durable souscripteur/une durable subscription,
        // pour l'utilisateur qu'on nomme "user", et sur le topic "TP2Exo2"

        javax.jms.MessageConsumer souscripteur=receiveSession.createDurableSubscriber(t,"user"); 
        connect.start();
        
        while (true) {
        	Message m=souscripteur.receive(); // ici la reception est synchrone, mais on peut aussi avoir un listener onMessage()
        	System.out.println("recu du topic " + ((javax.jms.TextMessage) m).getText());
        	
        }

	}

}
