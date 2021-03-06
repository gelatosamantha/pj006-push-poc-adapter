/**
 *  Copyright 2012-2020 Solace Corporation. All rights reserved.
 *
 *  http://www.solace.com
 *
 *  This source is distributed under the terms and conditions
 *  of any contract or contracts between Solace and you or
 *  your company. If there are no contracts in place use of
 *  this source is not authorized. No support is provided and
 *  no distribution, sharing with others or re-use of this
 *  source is authorized unless specifically stated in the
 *  contracts referred to above.
 *
 * HelloWorldSub
 *
 * This sample shows the basics of creating session, connecting a session,
 * subscribing to a topic, and receiving a message. This is meant to be a
 * very basic example for demonstration purposes.
 */

package com.solacesystems.jcsmp.samples.introsamples.intro;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import com.solacesystems.jcsmp.ConsumerFlowProperties;
import com.solacesystems.jcsmp.EndpointProperties;
import com.solacesystems.jcsmp.FlowReceiver;
import com.solacesystems.jcsmp.JCSMPException;
import com.solacesystems.jcsmp.JCSMPFactory;
import com.solacesystems.jcsmp.JCSMPProperties;
import com.solacesystems.jcsmp.JCSMPSession;
import com.solacesystems.jcsmp.JCSMPStreamingPublishEventHandler;
import com.solacesystems.jcsmp.Queue;
import com.solacesystems.jcsmp.XMLMessageProducer;

public class SimpleTranslatorService {

    public static void main(String... args) throws JCSMPException {
        String currentDirectory = System.getProperty("user.dir");
        System.out.println("The current working directory is " + currentDirectory);
    	String hostString1 = "";
    	String userName1 = "";
    	String passwordString1 = "";
    	String vpnName1 = "";
    	String queueName = "";
    	String hostString2 = "";
    	String userName2 = "";
    	String passwordString2 = "";
    	String vpnName2 = "";
    	
    	// File file = new File(currentDirectory+File.separator+"bin"+File.separator+"com"+File.separator+"solacesystems"+File.separator+"jcsmp"+File.separator+"samples"+File.separator+"introsamples"+File.separator+"intro"+File.separator+"config.txt");
    	File file = new File(currentDirectory+File.separator+"config.txt");
    	
    	BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(file));
	    	String st;
	    	Integer arg = 0;
	    	Integer total_param = 0;
	    	while ((st = br.readLine()) != null) { 
	    		switch (arg) {
	    			case(1):
	    				// Read host
	    				hostString1 = st;
	    				total_param++;
	    				arg = 0;
	    			case(2):
	    				// Read username
	    				userName1 = st;
	    				total_param++;
	    				arg = 0;
	    			case(3):
	    				// Read passowrd
	    				passwordString1=st;
	    				total_param++;
	    				arg = 0;
	    			case(4):
	    				// Read vpn
	    				vpnName1=st;
	    				total_param++;
	    				arg = 0;
	    			case(5):
	    				// Read queue
	    				queueName=st;
	    				total_param++;
	    				arg = 0;
	    			case(6):
	    				// Read host
	    				hostString2 = st;
	    				total_param++;
	    				arg = 0;
	    			case(7):
	    				// Read username
	    				userName2 = st;
	    				total_param++;
	    				arg = 0;
	    			case(8):
	    				// Read passowrd
	    				passwordString2=st;
	    				total_param++;
	    				arg = 0;
	    			case(9):
	    				// Read vpn
	    				vpnName2=st;
	    				total_param++;
	    				arg = 0;
	    			default:
	    	    		if (st.equalsIgnoreCase("HostString1")) {
	    	    			arg = 1;
	    	    		}
	    	    	    if (st.equalsIgnoreCase("Username1")) {
	    	    	    	arg = 2;
	    	    	    }
	    	    	    if (st.equalsIgnoreCase("Password1")) {
	    	    	    	arg = 3;
	    	    	    }
	    	    	    if (st.equalsIgnoreCase("VPN1")) {
	    	    	    	arg = 4;
	    	    	    }
	    	    	    if (st.equalsIgnoreCase("Queue1")) {
	    	    	    	arg = 5;
	    	    	    }
	    	    		if (st.equalsIgnoreCase("HostString2")) {
	    	    			arg = 6;
	    	    		}
	    	    	    if (st.equalsIgnoreCase("Username2")) {
	    	    	    	arg = 7;
	    	    	    }
	    	    	    if (st.equalsIgnoreCase("Password2")) {
	    	    	    	arg = 8;
	    	    	    }
	    	    	    if (st.equalsIgnoreCase("VPN2")) {
	    	    	    	arg = 9;
	    	    	    }
	    		}

	    	  } 
	    	if (total_param < 9) {
	            System.out.println("Not enough information in config file");
	            System.exit(-1);
	    	}

		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        System.out.println("SimpleTranslatorService initializing...");
        final JCSMPProperties properties_1 = new JCSMPProperties();
        properties_1.setProperty(JCSMPProperties.HOST, hostString1);  // msg-backbone-ip:port
        properties_1.setProperty(JCSMPProperties.VPN_NAME, vpnName1); // message-vpn
        properties_1.setProperty(JCSMPProperties.USERNAME, userName1);
        properties_1.setProperty(JCSMPProperties.PASSWORD, passwordString1);
        final Queue queue = JCSMPFactory.onlyInstance().createQueue(queueName);
        final JCSMPSession session_1 = JCSMPFactory.onlyInstance().createSession(properties_1);

        final CountDownLatch latch = new CountDownLatch(1); // used for
                                                            // synchronizing b/w threads

        final JCSMPProperties properties_2 = new JCSMPProperties();
        properties_2.setProperty(JCSMPProperties.HOST, hostString2);  // msg-backbone-ip:port
        properties_2.setProperty(JCSMPProperties.VPN_NAME, vpnName2); // message-vpn
        properties_2.setProperty(JCSMPProperties.USERNAME, userName2);
        properties_2.setProperty(JCSMPProperties.PASSWORD, passwordString2);
        final JCSMPSession session_2 = JCSMPFactory.onlyInstance().createSession(properties_2);

        // Create producer for republishing message to the second appliance
        XMLMessageProducer prod = session_2.getMessageProducer(new JCSMPStreamingPublishEventHandler() {
            public void responseReceived(String messageID) {
                System.out.println("Producer received response for msg: " + messageID);
            }
            public void handleError(String messageID, JCSMPException e, long timestamp) {
                System.out.printf("Producer received error for msg: %s@%s - %s%n",
                        messageID,timestamp,e);
            }
        });
        
        // Create a Flow be able to bind to and consume messages from the Queue.
        final ConsumerFlowProperties flow_prop = new ConsumerFlowProperties();
        flow_prop.setEndpoint(queue);
        // set to "auto acknowledge" where the API will ack back to Solace at the
        // end of the message received callback
        flow_prop.setAckMode(JCSMPProperties.SUPPORTED_MESSAGE_ACK_CLIENT);
        EndpointProperties endpoint_props = new EndpointProperties();
        endpoint_props.setAccessType(EndpointProperties.ACCESSTYPE_EXCLUSIVE);
        // bind to the queue, passing null as message listener for no async callback
        final FlowReceiver cons = session_1.createFlow(new TranslateConsumer(prod), flow_prop, endpoint_props);
        // Start the consumer
        System.out.println("Connected!");
        cons.start();
        // Consume-only session is now hooked up and running!

    	try {
			System.in.read();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        // Close consumer
        cons.close();
        System.out.println("Exiting.");
        session_1.closeSession();
    }
}
