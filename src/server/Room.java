package server;

import java.util.ArrayList;


public class Room {

    private ArrayList<String> history = null;
    private ArrayList<ClientThread> users = null;

    public Room() {
        this.history = new ArrayList<>();
        this.users = new ArrayList<>();
    }

    public boolean join(ClientThread clientThread) {

        users.add(clientThread);

        for (String message :
                history) {
            clientThread.send(message);
        }

        broadcast(null, String.format("<html><b>%s</b> has joined the room.</html>", clientThread.getNick()));
        return false;
    }

    public void broadcast(ClientThread emitter, String message) {

        String sender = "<html><b>" + (emitter == null ? "Server" : emitter.getNick()) + "</b>: " + message + "</html>";
        history.add(sender);

        for (ClientThread ct :
                users) {
            ct.send(sender);
        }
    }

    public boolean leave(ClientThread clientThread) {
        broadcast(null, String.format("<html><b>%s</b> has left the room.</html>", clientThread.getNick()));
        return users.remove(clientThread);
    }
}
