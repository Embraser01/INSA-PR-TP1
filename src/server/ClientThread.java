/*
 * ClientThread
 * Example of a TCP server
 * Date: 14/12/08
 * Authors:
 */

package server;

import java.io.*;
import java.net.*;

public class ClientThread
        extends Thread {

    private Socket clientSocket;
    private Server server;
    private PrintStream socOut;
    private Room room = null;
    private String nick = null;

    ClientThread(Socket s, Server server) {
        this.server = server;
        this.clientSocket = s;
    }

    /**
     * receives a request from client then sends an echo to the client
     **/
    public void run() {
        try {
            BufferedReader socIn = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));

            socOut = new PrintStream(clientSocket.getOutputStream());

            socOut.println("Bienvenue !");
            socOut.println("Pour rejoindre une salle : '/join numSalle'");
            socOut.println("Pour l'aide : '/help'");

            while (true) {
                String line = socIn.readLine();
                handleStream(line);
            }
        } catch (Exception e) {
            System.err.println("Error in Server:" + e);
        }
    }

    public void handleStream(String string) {
        if (room != null && string.charAt(0) != '/') {
            room.broadcast(this, string);
            return;
        }

        String[] words = string.split(" ");
        String command = words[0];
        switch (command) {
            case "/join":
                if (nick == null) break;
                if (room != null) room.leave(this);
                room = server.join(this, Integer.parseInt(words[1]));
                break;
            case "/leave":
                if (room != null) room.leave(this);
                room = null;
                break;
            case "/nick":
                nick = words[1];
                break;
            default:
                break;
        }

    }

    public void send(String string) {
        socOut.println(string);
        socOut.flush();
    }

    public String getNick() {
        return nick;
    }

    public Room getRoom() {
        return room;
    }
}

  