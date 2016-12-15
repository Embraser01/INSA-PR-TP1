package server;

import java.util.ArrayList;

/**
 * Represents a chat room, stores an history of the messages and a list of its users
 * Also handles message dispatch as well as join/leave notifications
 *
 * @author Tristan Bourvon
 * @author Marc-Antoine FERNANDES
 * @version 1.0.0
 */
public class Room {

    /**
     * History of the messages sent by the clients
     */
    private ArrayList<String> history = null;

    /**
     * List of the users currently in this room
     */
    private ArrayList<ClientThread> users = null;

    /**
     * Initialization constructor
     */
    public Room() {
        this.history = new ArrayList<>();
        this.users = new ArrayList<>();
    }

    /**
     * Makes a client join this room, displaying the appropriate messages
     *
     * @param clientThread Client joining this room
     * @return
     */
    public boolean join(ClientThread clientThread) {

        users.add(clientThread);

        for (String message :
                history) {
            clientThread.send(message);
        }

        broadcast(null, String.format("<html><b>%s</b> has joined the room.</html>", clientThread.getNick()));
        return false;
    }

    /**
     * Broadcast a message to all users in the room
     *
     * @param emitter Sender of the message
     * @param message Message to be broadcast
     */
    public void broadcast(ClientThread emitter, String message) {

        String sender = "<html><b>" + (emitter == null ? "Server" : emitter.getNick()) + "</b>: " + message + "</html>";
        history.add(sender);

        for (ClientThread ct :
                users) {
            ct.send(sender);
        }
    }

    /**
     * Makes a client leave this room, displaying the appropriate messages
     *
     * @param clientThread Client leaving the room
     * @return
     */
    public boolean leave(ClientThread clientThread) {
        broadcast(null, String.format("<html><b>%s</b> has left the room.</html>", clientThread.getNick()));
        return users.remove(clientThread);
    }
}
