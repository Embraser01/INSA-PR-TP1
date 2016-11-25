package client.ui;

import client.Client;

import javax.swing.*;
import java.io.IOException;

/**
 * Created by marca on 25/11/2016.
 */
public class Window {
    private static JList messageList;
    private static JTextField messageField;
    private static JButton sendButton;
    private static JPanel root;

    public static void main(String[] args) throws IOException {
        Client client = new Client(args[0], Integer.parseInt(args[1]));

        JOptionPane.showInputDialog(null, "Nickname?");

        JFrame frame = new JFrame("Window");
        frame.setContentPane(new Window().root);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);


    }

    public static void addMessage(String message) {
        messageList.add(new JLabel(message));
    }
}
