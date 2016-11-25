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
    private BufferedReader stdIn = null;
    private Reception reception = null;
    private Thread threadReception = null;
    private String name = null;

    /**
     * main method
     * accepts a connection, receives a message from client then sends an echo to the client
     **/
    public Client(String ip, int port) throws IOException {

        try {
            // creation socket ==> connexion
            socket = new Socket(ip, port);

            // Reception

            reception = new Reception(socket, port);
            threadReception = new Thread(reception);
            threadReception.start();

            socOut = new PrintStream(socket.getOutputStream());
            stdIn = new BufferedReader(new InputStreamReader(System.in));

        } catch (UnknownHostException e) {
            System.err.println("Don't know about host:" + ip);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for "
                    + "the connection to:" + ip);
            System.exit(1);
        }

        System.out.print("Nick? ");
        name = stdIn.readLine();
        socOut.println(String.format("/nick %s", name));
        String line;

        while (true) {
            line = stdIn.readLine();
            if (line.equals("/quit")) break;
            socOut.println(line);
        }

        socOut.close();
        reception.stop();
        stdIn.close();
        socket.close();
    }


}


