package Talk;/*
 * � 2001-2009, Progress Software Corporation and/or its subsidiaries or affiliates.  All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 
Sample Application

Writing a Basic JMS Application using Point-to-Point (Queues).
This sample starts up with a username, and the queues you are
sending on, and receiving on.

Writing a line and pressing enter will send the message only to the
indicated 'Talk' partner (or QueueReceiver).

Usage:
  java Talk -b <broker:port> -u <username> -p <password> -qs <queue> -qr <queue>
      -b broker:port points to your message broker
                Default: tcp://localhost:61616
      -u username    must be unique (but is not checked)
      -p password    password for user (not checked)
      -qr queue      name of queue to receive
      -qs queue      name of queue to send

You must specify either a queue for sending or receiving (or both).


Suggested demonstration:
  - In separate console windows with the environment set,
    start instances of the application under unique user names.
    For example:
       java Talk -b localhost:2506 -u SALES -qr  Q1 -qs  Q2
       java Talk -b localhost:2506 -u MARKETTING -qr  Q2 -qs  Q1
  - Enter text and then press Enter to send the message.
  - A message is consumed by only one of the receivers on a queue.
  - Stop a session by pressing CTRL+C in its console window.

*/
import org.apache.activemq.*;


public class Talk
    implements javax.jms.MessageListener
{
    private static final String DEFAULT_BROKER_NAME = "tcp://localhost:61616";
    private static final String DEFAULT_PASSWORD = "password";
    private static final int    MESSAGE_LIFESPAN = 1800000;  // milliseconds (30 minutes)

    private javax.jms.Connection connect = null;
    private javax.jms.Session sendSession = null;
    private javax.jms.Session receiveSession = null;
    private javax.jms.MessageProducer sender = null;

    /** Create JMS client for sending and receiving messages. */
    private void talker( String broker, String username, String password, String rQueue, String sQueue)
    {
        // Create a connection.
        try
        {
            javax.jms.ConnectionFactory factory;
            factory = new ActiveMQConnectionFactory(username, password, broker);
            connect = factory.createConnection (username, password);
            sendSession = connect.createSession(false,javax.jms.Session.AUTO_ACKNOWLEDGE);
            receiveSession = connect.createSession(false,javax.jms.Session.AUTO_ACKNOWLEDGE);
        }
        catch (javax.jms.JMSException jmse)
        {
            System.err.println("error: Cannot connect to Broker - " + broker);
            jmse.printStackTrace();
            System.exit(1);
        }

        // Create Sender and Receiver 'Talk' queues
        try
        {
            if (sQueue != null)
            {
                javax.jms.Queue sendQueue = sendSession.createQueue (sQueue);
                sender = sendSession.createProducer(sendQueue);
            }
            if (rQueue != null)
            {
                javax.jms.Queue receiveQueue = receiveSession.createQueue (rQueue);
                javax.jms.MessageConsumer qReceiver = receiveSession.createConsumer(receiveQueue);
                qReceiver.setMessageListener(this);
                // Now that 'receive' setup is complete, start the Connection
                connect.start();
            }
        }
        catch (javax.jms.JMSException jmse)
        {
            jmse.printStackTrace();
            exit();
        }

        try
        {
            if (rQueue != null)
               System.out.println ("");
            else
               System.out.println ("\nNo receiving queue specified.\n");

            // Read all standard input and send it as a message.
            java.io.BufferedReader stdin =
                new java.io.BufferedReader( new java.io.InputStreamReader( System.in ) );
            if (sQueue != null)
                System.out.println ("\nTalk application:\n"
            					+ "=================\n"
            					+ "The application user " + username + " connects to the broker at " + DEFAULT_BROKER_NAME + ".\n"
								+ "The application will send messages to the " + sQueue + " queue.\n"
                                + "The application creates a receiver on the " + rQueue + " queue to consume any messages allocated to it.\n\n"
                                + "Type some text, and then press Enter to send it as a TextMesssage from " + username + ".\n");

            else
                System.out.println ("\nPress CTRL-C to exit.\n");

            while ( true )
            {
                String s = stdin.readLine();

                if ( s == null )
                    exit();
                else if ( s.length() > 0 && sQueue != null)
                {
                    javax.jms.TextMessage msg = sendSession.createTextMessage();
                    msg.setText( username + ": " + s );
                    // Queues usually are used for PERSISTENT messages.
                    // Hold messages for 30 minutes (1,800,000 millisecs).
                    sender.send( msg,
                                 javax.jms.DeliveryMode.PERSISTENT,
                                 javax.jms.Message.DEFAULT_PRIORITY,
                                 MESSAGE_LIFESPAN);
                }
            }
        }
        catch ( java.io.IOException ioe )
        {
            ioe.printStackTrace();
        }
        catch ( javax.jms.JMSException jmse )
        {
            jmse.printStackTrace();
        }
        // Close the connection.
        exit();
    }

    /**
     * Handle the message
     * (as specified in the javax.jms.MessageListener interface).
     */
    public void onMessage( javax.jms.Message aMessage)
    {
        try
        {
            // Cast the message as a text message.
             // Otherwise report that invalid message arrived.
            if (aMessage instanceof javax.jms.TextMessage)
            {
            	javax.jms.TextMessage textMessage = (javax.jms.TextMessage) aMessage;

            	// This handler reads a single String from the
            	// message and prints it to the standard output.
            	try
            	{
                	String string = textMessage.getText();
                	System.out.println( string );
           		}
            	catch (javax.jms.JMSException jmse)
            	{
            	    jmse.printStackTrace();
            	}
          	}
			else
			{
			    System.out.println ("Warning: A message was discarded because it could not be processed " +
			                                    "as a javax.jms.TextMessage.");
			 }

        }
        catch (java.lang.RuntimeException rte)
        {
            rte.printStackTrace();
        }
    }

    /** Cleanup resources and then exit. */
    private void exit()
    {
        try
        {
            connect.close();
        }
        catch (javax.jms.JMSException jmse)
        {
            jmse.printStackTrace();
        }

        System.exit(0);
    }

    //
    // NOTE: the remainder of this sample deals with reading arguments
    // and does not utilize any JMS classes or code.
    //

    /** Main program entry point. */
    public static void main(String argv[]) {

        // Is there anything to do?
        if (argv.length == 0) {
            printUsage();
            System.exit(1);
        }

        // Values to be read from parameters
        String broker    	= DEFAULT_BROKER_NAME;
        String username  	= null;
        String password  	= DEFAULT_PASSWORD;
        String qSender		= null;
        String qReceiver	= null;

        // Check parameters
        for (int i = 0; i < argv.length; i++) {
            String arg = argv[i];

            // Options
            if (!arg.startsWith("-")) {
                System.err.println ("error: unexpected argument - "+arg);
                printUsage();
                System.exit(1);
            }
            else {
                if (arg.equals("-b")) {
                    if (i == argv.length - 1 || argv[i+1].startsWith("-")) {
                        System.err.println("error: missing broker name:port");
                        System.exit(1);
                    }
                    broker = argv[++i];
                    continue;
                }

                if (arg.equals("-u")) {
                    if (i == argv.length - 1 || argv[i+1].startsWith("-")) {
                        System.err.println("error: missing user name");
                        System.exit(1);
                    }
                    username = argv[++i];
                    continue;
                }

                if (arg.equals("-p")) {
                    if (i == argv.length - 1 || argv[i+1].startsWith("-")) {
                        System.err.println("error: missing password");
                        System.exit(1);
                    }
                    password = argv[++i];
                    continue;
                }

                if (arg.equals("-qr")) {
                    if (i == argv.length - 1 || argv[i+1].startsWith("-")) {
                        System.err.println("error: missing receive queue parameter");
                        System.exit(1);
                    }
                    qReceiver = argv[++i];
                    continue;
                }

                if (arg.equals("-qs")) {
                    if (i == argv.length - 1 || argv[i+1].startsWith("-")) {
                        System.err.println("error: missing send queue parameter");
                        System.exit(1);
                    }
                    qSender = argv[++i];
                    continue;
                }


                if (arg.equals("-h")) {
                    printUsage();
                    System.exit(1);
                }
            }
        }

        // Check values read in.
        if (username == null) {
            System.err.println ("error: user name must be supplied");
            printUsage();
            System.exit(1);
        }

        if (qReceiver == null && qSender == null) {
            System.err.println ("error: receive queue, or send queue, must be supplied");
            printUsage();
            System.exit(1);
        }

        // Start the JMS client for the "Talk".
        Talk talk = new Talk();
        talk.talker (broker, username, password, qReceiver, qSender);

    }

    /** Prints the usage. */
    private static void printUsage() {

        StringBuffer use = new StringBuffer();
        use.append("usage: java Talk (options) ...\n\n");
        use.append("options:\n");
        use.append("  -b name:port Specify name:port of broker.\n");
        use.append("               Default broker: "+DEFAULT_BROKER_NAME+"\n");
        use.append("  -u name      Specify unique user name. (Required)\n");
        use.append("  -p password  Specify password for user.\n");
        use.append("               Default password: "+DEFAULT_PASSWORD+"\n");
        use.append("  -qr queue    Specify queue for receiving messages.\n");
        use.append("  -qs queue    Specify queue for sending messages.\n");
        use.append("  -h           This help screen.\n");
        System.err.println (use);
    }

}