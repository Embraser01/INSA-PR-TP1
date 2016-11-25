package server;

public class MainServer {

    public static void main(String[] args) {

        if (args.length != 1) {
            System.out.println("Usage: java Server <Server port>");
            System.exit(1);
        }

        Server server = new Server(Integer.parseInt(args[0]));
    }
}
