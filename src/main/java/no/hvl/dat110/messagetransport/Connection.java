package no.hvl.dat110.messagetransport;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Connection {

    private DataOutputStream outStream;
    private DataInputStream inStream;
    private Socket socket;

    public Connection(Socket socket) {

        try {

            this.socket = socket;

            outStream = new DataOutputStream(socket.getOutputStream());
            inStream = new DataInputStream(socket.getInputStream());

        } catch (IOException ex) {

            System.out.println("Connection: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void send(TransportMessage message) {

        try {

            byte[] sendbuf = message.encapsulate();

            outStream.write(sendbuf);
            outStream.flush();

        } catch (IOException ex) {

            System.out.println("Connection: " + ex.getMessage());
            ex.printStackTrace();
        }

    }

    public boolean hasData() {

        boolean hasdata = false;

        try {

            hasdata = inStream.available() > 0;

        } catch (IOException ex) {

            System.out.println("Connection: " + ex.getMessage());
            ex.printStackTrace();
        }

        return hasdata;
    }

    public TransportMessage receive() {

        TransportMessage message;
        byte[] recvbuf;

        recvbuf = new byte[MessageConfig.SEGMENTSIZE];

        try {

            int read = inStream.read(recvbuf, 0, MessageConfig.SEGMENTSIZE);

            if (read != MessageConfig.SEGMENTSIZE) {
                throw new IOException("receive - missing data");
            }

        } catch (IOException ex) {

            System.out.println("Connection: " + ex.getMessage());
        }

        message = new TransportMessage();

        message.decapsulate(recvbuf);

        return message;

    }

    public void close() {

        try {

            outStream.close();
            inStream.close();
            socket.close();

        } catch (IOException ex) {

            System.out.println("Connection: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}