package client;

import client.ui.Window;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;


public class Reception implements Runnable {

    private Socket socket = null;
    private boolean stop = false;

    public Reception(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader socIn = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

            while (!stop) {
                handleInput(socIn.readLine());
            }
        } catch (IOException ignored) {}
    }

    private void handleInput(String input) {
        Window.getInstance().addMessage(input);
    }

    public void stop() {
        stop = true;
    }
}