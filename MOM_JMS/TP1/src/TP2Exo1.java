import javax.jms.JMSException;
import javax.jms.Message;



public class TP2Exo1  implements javax.jms.MessageListener{
	static javax.jms.Session receiveSession=null;  // décalé la déclaration de receiveSession ici (donc en static)
	public static void main(String[] args) throws JMSException {


		javax.jms.ConnectionFactory factory;
        factory = new org.apache.activemq.ActiveMQConnectionFactory("user", "password", "tcp://localhost:61616");
        javax.jms.Connection connect = factory.createConnection ("user", "password");
        
        javax.jms.Session sendSession = connect.createSession(false, javax.jms.Session.AUTO_ACKNOWLEDGE);
        javax.jms.Queue sendQueue = sendSession.createQueue ("sQueue");
        javax.jms.MessageProducer sender = sendSession.createProducer(sendQueue);
        connect.start();
        
        
        //configuration du consommateur devient mode transactionel: suffit de mettre true
        receiveSession = connect.createSession(true, javax.jms.Session.AUTO_ACKNOWLEDGE);
        javax.jms.Queue receiveQueue = receiveSession.createQueue ("sQueue");
        javax.jms.MessageConsumer receiver = receiveSession.createConsumer(receiveQueue);
        
        receiver.setMessageListener(new TP2Exo1()); // pour passer adresse de objet implementant onMessage
        
        
        for (int i=1;i<=5;i++){
        	javax.jms.TextMessage msg = sendSession.createTextMessage(); 
        	if (i==2) msg.setStringProperty("typeMess","important"); 
        	msg.setText( "coucou : " + i );
        	sender.send(msg);
        }
        javax.jms.MapMessage msgmap = sendSession.createMapMessage();
        for (int i=1;i<=2;i++){
        	msgmap.setString("nom", "votre nom");
        	if (i==1) msgmap.setInt("age", 18);
        	if (i==2) msgmap.setInt("age", 19);
        	msgmap.setLong("salaire", 1000000/i);
        	sender.send(msgmap);
        }
        System.out.println("fini de produire ");
        
        
	}
	int nbRecu=0; boolean acommiter=true;
	@Override
	public void onMessage(Message message)  {
		System.out.println("recu "+ message);
		nbRecu++;
		try{
			if (nbRecu==2 && acommiter==true) { 
				// commiter
				receiveSession.commit();
				nbRecu=0;
				acommiter=false;
			}
			if (nbRecu==2 && acommiter==false){
				// rollbacker
				nbRecu=0;
				receiveSession.rollback();
				acommiter=true;
			}
		} catch (JMSException e){}
	}

}
