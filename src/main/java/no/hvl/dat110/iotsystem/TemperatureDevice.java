package no.hvl.dat110.iotsystem;

import no.hvl.dat110.client.Client;
import no.hvl.dat110.common.Common;

public class TemperatureDevice {

    private static final int COUNT = 10;

    public static void main(String[] args) {

        // simulated / virtual temperature sensor
        TemperatureSensor sn = new TemperatureSensor();

        System.out.println("Temperature device started");

        try {

            // create client with username "sensor"
            Client client = new Client("sensor", Common.SERVER, Common.PORT);

            // connect to broker
            client.connect();

            // publish temperatures
            for (int i = 0; i < COUNT; i++) {

                int temperature = sn.read();

                System.out.println("READING: " + temperature);

                client.publish("temperature", String.valueOf(temperature));

                Thread.sleep(1000);
            }

            // disconnect
            client.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Temperature device stopping ... ");
    }
}