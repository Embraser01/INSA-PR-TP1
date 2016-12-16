/*
  EchoServer
  Example of a TCP server
  Date: 10/01/04
  Authors:
 */

package server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Main server class, used to listen to incoming connections and start appropriate client threads
 * Also stores the list of the rooms (with the roomID to room object table) and of users.
 *
 * @author Tristan Bourvon
 * @author Marc-Antoine FERNANDES
 * @version 1.0.0
 */
public class Server {

    /**
     * Server socket used to listen to incoming connections
     */
    private ServerSocket listenSocket;

    /**
     * List of client threads currently connected
     */
    private ArrayList<ClientThread> clientThreads;

    /**
     * Map of the chat rooms by roomID
     */
    private HashMap<String ,Room> rooms;

    /**
     * Constructor used to initialize the class and wait for incoming connections, spawning threads as needed
     *
     * @param port Connection port
     **/
    public Server(int port) {

        clientThreads = new ArrayList<>();
        rooms = new HashMap<>();
        try {
            listenSocket = new ServerSocket(port); //port
            System.out.println("Server ready...");
            while (true) {
                Socket clientSocket = listenSocket.accept();
                System.out.println("Connexion from:" + clientSocket.getInetAddress());
                ClientThread ct = new ClientThread(clientSocket, this);
                clientThreads.add(ct);
                ct.start();

            }
        } catch (Exception e) {
            System.err.println("Error in Server:" + e);
        }
    }

    /**
     * Used to make a user join a room, translating the roomID used to the required Room object
     *
     * @param clientThread User joining the room
     * @param room Room to be joined
     * @return The room
     */
    public Room join(ClientThread clientThread, String room) {
        Room room1 = rooms.computeIfAbsent(room, k -> new Room());

        room1.join(clientThread);
        return room1;
    }
}

