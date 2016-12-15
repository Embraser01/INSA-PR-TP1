package client.ui;

import client.Client;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

/**
 * Main entry point for the chat, also the class responsible for handling the GUI
 * Starts the communication with the server and the interface
 *
 * @author Tristan Bourvon
 * @author Marc-Antoine FERNANDES
 * @version 1.0.0
 */
public class Window {
    /**
     * List used to display messages
     */
    private JList<String> messageList;

    /**
     * Model for the message list
     */
    private DefaultListModel<String> defaultListModel;

    /**
     * Field used to type and send messages
     */
    private JTextField messageField;

    /**
     * Button used to send messages
     */
    private JButton sendButton;

    /**
     * Root panel
     */
    private JPanel root;

    /**
     * Scroll pane allowing the message list to be scrollable
     */
    private JScrollPane scrollPane;

    /**
     * Client used for communication with the server
     */
    private Client client;

    /**
     * Name of the user on the chat
     */
    private static String name;

    /**
     * Instance of the class (singleton)
     */
    private static Window instance = null;

    /**
     * Constructor, which takes a client object and binds all GUI actions to send messages
     *
     * @param client Client used for the connection to the server
     */
    private Window(Client client) {

        this.client = client;

        sendButton.addActionListener(e -> {
            if (!messageField.getText().trim().isEmpty()) {
                client.sendMessage(messageField.getText().trim());
                messageField.setText("");
            }
        });
        messageField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER && !messageField.getText().trim().isEmpty()) {
                    client.sendMessage(messageField.getText().trim());
                    messageField.setText("");
                }
            }
        });
    }

    /**
     * Main entry point of the program, initializes the Window and the Client connection.
     *
     * @param args Program arguments, we use args[0] for the hostname and args[1] for the port
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        if (args.length != 2) {
            System.out.println("Usage: java Client <Server host> <Server port>");
            System.exit(1);
        }

        Client client = new Client();

        name = JOptionPane.showInputDialog(null, "Nickname?");
        String newNameCommand = String.format("/nick %s", name);

        instance = new Window(client);

        client.connect(args[0], Integer.parseInt(args[1]));

        client.sendMessage(newNameCommand);


        JFrame frame = new JFrame(name);
        frame.setContentPane(instance.root);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                client.quit();
                e.getWindow().dispose();
            }
        });
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Static method used for the singleton pattern which returns the Window instance
     *
     * @return Window instance
     */
    public static Window getInstance() {
        return instance;
    }

    /**
     * Adds a message to the message list
     *
     * @param message Message to be added
     */
    public void addMessage(String message) {
        defaultListModel.addElement(message);

        // Stick to bottom
        JScrollBar vertical = scrollPane.getVerticalScrollBar();
        vertical.setValue(vertical.getMaximum());

    }

    /**
     * Initializes the model for the message list
     */
    private void createUIComponents() {
        defaultListModel = new DefaultListModel<>();

        messageList = new JList<>(defaultListModel);
        messageList.setCellRenderer(new MyListCellRenderer());
    }
}
