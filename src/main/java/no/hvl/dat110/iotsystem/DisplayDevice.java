package no.hvl.dat110.iotsystem;

import no.hvl.dat110.client.Client;
import no.hvl.dat110.messages.Message;
import no.hvl.dat110.messages.PublishMsg;

public class DisplayDevice {

    private static final int COUNT = 10;

    public static void main (String[] args) {

        System.out.println("Display starting ...");

        try {

            // create client with username "display"
            Client client = new Client("display", Common.BROKERHOST, Common.BROKERPORT);

            // connect to broker
            client.connect();

            // create the topic
            client.createTopic("temperature");

            // subscribe to topic
            client.subscribe("temperature");

            // receive COUNT messages
            for(int i = 0; i < COUNT; i++) {

                Message msg = client.receive();

                if(msg instanceof PublishMsg) {

                    PublishMsg pmsg = (PublishMsg) msg;

                    System.out.println("DISPLAY: " + pmsg.getMessage());
                }
            }

            // unsubscribe
            client.unsubscribe("temperature");

            // disconnect
            client.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Display stopping ... ");
    }
}