package client;

import client.ui.Window;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Class used to receive data from the server in a separate thread
 *
 * @author Tristan Bourvon
 * @author Marc-Antoine FERNANDES
 * @version 1.0.0
 */
public class Reception implements Runnable {

    /**
     * Socket used for the connection to the server
     */
    private Socket socket = null;

    /**
     * Used to indicate to the object to stop receiving
     */
    private boolean stop = false;

    /**
     *
     * @param socket see {@link socket}
     */
    public Reception(Socket socket) {
        this.socket = socket;
    }

    /**
     * Method called when instanciated in a new thread.
     * Handles the reception loop from the server
     */
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

    /**
     * Dispatches the received input to the GUI
     * @param input Message received
     */
    private void handleInput(String input) {
        Window.getInstance().addMessage(input);
    }

    /**
     * Stops the reception
     */
    public void stop() {
        stop = true;
    }
}