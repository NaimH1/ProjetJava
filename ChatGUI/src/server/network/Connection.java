package server.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Class in charge of the connexion of new clients
 *
 * @author Remi Watrigant
 *
 */
public class Connection implements Runnable {

    /*
	 * a reference to the server it belongs to
     */
    private Server server;

    /*
	 * the server socket used to accept new connexions
     */
    private ServerSocket serverSocket;

    /**
     * creates a new ServerSocket
     *
     * @param server a reference to the server
     * @param port the port the server listens to
     * @throws IOException
     */
    public Connection(Server server) throws IOException {
        this.server = server;

        this.serverSocket = new ServerSocket(server.getPort());
    }

    /**
     * Wait for new client connexions using the SocketServer. When a new client
     * connects, it creates a new thread with a new ClientInServer object, and
     * adds this new ClientInServer to the server's list of connected clients
     */
    @Override
    public void run() {

        while (true) {

            try {

                System.out.println("Attente de nouveaux clients...");
                Socket sockNewClient = serverSocket.accept();

                ConnectedClient newClient = new ConnectedClient(server, sockNewClient);
                server.addClient(newClient);
                Thread threadNewClient = new Thread(newClient);
                threadNewClient.start();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

}
