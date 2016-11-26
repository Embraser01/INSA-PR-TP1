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

            send("Welcome !\n" +
                    "To join a room type '/join <room_id>'\n" +
                    "To get help type '/help'"
            );

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
            case "/help":
                send("Help :\n" +
                        "/join <room_id> : Join a room (if absent, a new one will be created)\n" +
                        "/leave : Return to the lobby\n" +
                        "/nick : Change your nickname\n" +
                        "/help : Display this menu");
                break;
            default:
                if(string.charAt(0) == '/') {
                    send("Unknown command!");
                } else {
                    send("You have to join a room first!");
                }
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
}

  