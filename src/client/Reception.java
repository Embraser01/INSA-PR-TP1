package client;

import client.ui.Window;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Reception implements Runnable {

    private Socket socket = null;
    private BufferedReader socIn = null;

    boolean stop = false;
    int port = -1;
    private String message = null;

    public Reception(Socket socket, int port) {
        this.socket = socket;
        this.port = port;
    }

    @Override
    public void run() {
        try {
            socIn = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

            while (!stop) {
                handleInput(socIn.readLine());
            }
        } catch (IOException ex) {
            Logger.getLogger(Reception.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void handleInput(String input) {
        if (input.charAt(0) != '/') {
            printMessage(input);
            return;
        }

        switch (input) {
            case "/join":
                break;
            case "/leave":
                break;
            default:
                break;
        }
    }

    public void printMessage(String message) {
        Window.addMessage(message);
    }


    public void stop() {
        stop = true;
    }
}