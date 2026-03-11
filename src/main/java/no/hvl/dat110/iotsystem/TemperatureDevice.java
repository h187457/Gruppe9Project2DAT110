package no.hvl.dat110.iotsystem;
import no.hvl.dat110.client.Client;

public class TemperatureDevice {

    private static final int COUNT = 10;

    public static void main(String[] args) {

        TemperatureSensor sn = new TemperatureSensor();

        System.out.println("Temperature device started");

        try {

            Client client = new Client("sensor", Common.BROKERHOST, Common.BROKERPORT);

            client.connect();

            for (int i = 0; i < COUNT; i++) {

                int temperature = sn.read();

                System.out.println("READING: " + temperature);

                client.publish(Common.TEMPTOPIC, String.valueOf(temperature));

                Thread.sleep(1000);
            }

            client.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Temperature device stopping ... ");
    }
}