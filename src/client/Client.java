/*
 * EchoClient
 * Example of a TCP client
 * Date: 10/01/04
 * Authors:
 */
package client;

import java.io.*;
import java.net.*;

/**
 * Class used to initialize the connection to the server and send the data to the server
 * @author Tristan Bourvon
 * @author Marc-Antoine FERNANDES
 * @version 1.0.0
 */
public class Client {

    /**
     * Socket used to connect to the server and exchange data
     */
    private Socket socket = null;

    /**
     * Stream used to send data to the server
     */
    private PrintStream socOut = null;

    /**
     * Object used for reception of data from the server
     */
    private Reception reception = null;

    /**
     * Thread used for the Reception object to be able to receive independently from this thread
     * Thanks to this we avoid having the reception blocked by stdin (for example)
     */
    private Thread threadReception = null;

    /**
     * Starts the connection to the server on ip:port, also starts the reception thread
     *
     * @param ip IP to connect to
     * @param port Port to connect to
     * @throws IOException if the connection fails
     */
    public void connect(String ip, int port) throws IOException {

        try {
            // creation socket ==> connexion
            socket = new Socket(ip, port);

            // Reception

            reception = new Reception(socket);
            threadReception = new Thread(reception);
            threadReception.start();

            // Emitter

            socOut = new PrintStream(socket.getOutputStream());

        } catch (UnknownHostException e) {
            System.err.println("Don't know about host:" + ip);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for "
                    + "the connection to:" + ip);
            System.exit(1);
        }
    }

    /**
     * Sends a message to the server
     *
     * @param message Message to be sent
     */
    public void sendMessage(String message) {
        socOut.println(message);
    }

    /**
     * Quits the chat client
     */
    public void quit() {
        reception.stop();
        socOut.close();

        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


