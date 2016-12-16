/*
 * ClientThread
 * Example of a TCP server
 * Date: 14/12/08
 * Authors:
 */

package server;

import java.io.*;
import java.net.*;

/**
 * Each ClientThread is a new thread used by a single client, which allows users to chat concurrently
 *
 * @author Tristan Bourvon
 * @author Marc-Antoine FERNANDES
 * @version 1.0.0
 */
public class ClientThread
        extends Thread {

    /**
     * Socket used for the communication with the client
     */
    private Socket clientSocket;

    /**
     * Main server class used to dispatch actions to other clients and to handle rooms and clients creation
     */
    private Server server;

    /**
     * Stream used to output to the socket
     */
    private PrintStream socOut;

    /**
     * Current room of the user (the user can only by in a single room)
     */
    private Room room = null;

    /**
     * Current nickname of the client
     */
    private String nick = null;

    /**
     * Initialization constructor
     *
     * @param s see {@link #clientSocket}
     * @param server see {@link #server}
     */
    ClientThread(Socket s, Server server) {
        this.server = server;
        this.clientSocket = s;
    }

    /**
     * Method called when starting the thread.
     * Initializes the streams of communication, displays a welcome/help message, and starts
     * reading input from the client
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

    /**
     * Handles an incoming message from the client
     * An incoming message can be a simple message or a command prefixed by /
     *
     * @param string Message from the client
     */
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
                room = server.join(this, words[1]);
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

    /**
     * Sends a message to the client
     *
     * @param string Message to be sent
     */
    public void send(String string) {
        socOut.println(string);
        socOut.flush();
    }

    /**
     * Nickname getter
     *
     * @return see {@link #nick}
     */
    public String getNick() {
        return nick;
    }
}

  