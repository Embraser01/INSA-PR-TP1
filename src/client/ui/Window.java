package client.ui;

import client.Client;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

/**
 * Created by marca on 25/11/2016.
 */
public class Window {
    private JList<String> messageList;
    private DefaultListModel<String> defaultListModel;
    private JTextField messageField;
    private JButton sendButton;
    private JPanel root;
    private JScrollPane scrollPane;
    private Client client;

    private static String name;

    private static Window instance = null;

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

    public static Window getInstance() {
        return instance;
    }

    public void addMessage(String message) {
        defaultListModel.addElement(message);

        // Stick to bottom
        JScrollBar vertical = scrollPane.getVerticalScrollBar();
        vertical.setValue(vertical.getMaximum());

    }

    private void createUIComponents() {
        defaultListModel = new DefaultListModel<>();

        messageList = new JList<>(defaultListModel);
        messageList.setCellRenderer(new MyListCellRenderer());
    }
}
