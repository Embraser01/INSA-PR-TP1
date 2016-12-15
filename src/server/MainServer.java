package server;

/**
 * Main class
 *
 * @author Tristan Bourvon
 * @author Marc-Antoine FERNANDES
 * @version 1.0.0
 */
public class MainServer {

    /**
     * Entry point for the program, creates a new server with the port given
     *
     * @param args Arguments of the program, we use args[0] for the server port
     */
    public static void main(String[] args) {

        if (args.length != 1) {
            System.out.println("Usage: java Server <Server port>");
            System.exit(1);
        }

        Server server = new Server(Integer.parseInt(args[0]));
    }
}
