/*
 * EchoClient
 * Example of a TCP client
 * Date: 10/01/04
 * Authors:
 */
package client;

import java.io.*;
import java.net.*;


public class Client {

    private Socket socket = null;
    private PrintStream socOut = null;
    private Reception reception = null;
    private Thread threadReception = null;

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

    public void sendMessage(String message) {
        socOut.println(message);
    }


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


