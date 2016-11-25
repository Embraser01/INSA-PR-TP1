package client;

import java.io.*;

/**
 * Created by marca on 25/11/2016.
 */
public class MainClient {

    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.out.println("Usage: java Client <Server host> <Server port>");
            System.exit(1);
        }

        Client client = new Client(args[0], Integer.parseInt(args[1]));
    }
}
