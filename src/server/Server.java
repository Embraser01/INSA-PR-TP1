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

public class Server {

    private ServerSocket listenSocket;
    private ArrayList<ClientThread> clientThreads;
    private HashMap<Integer ,Room> rooms;

    /**
     * main method
     *
     * @param port port
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

    public Room join(ClientThread clientThread, Integer room) {
        Room room1 = rooms.computeIfAbsent(room, k -> new Room());

        room1.join(clientThread);
        return room1;
    }
}

