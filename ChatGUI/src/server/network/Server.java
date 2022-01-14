package server.network;

import common.Message;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketImpl;
import java.util.ArrayList;
import java.util.List;

/**
 * Class implementing a simple Chat server. 
 * Contains one thread accepting new clients, and, for each connected client,
 * one thread receiving new messages. When a message is received from a client, 
 * it is broadcasted to every other clients.
 * Relies on TCP connexions.
 * @author Remi Watrigant
 *
 */
public class Server {

	/*
	 * The list of connected clients
	 */
	private List<ConnectedClient> clients;

	/*
	 * the port the server listens to
	 */
	private int port;

	/**
	 * Starts a thread which accepts new clients
	 * @param port the port the server listens to
	 * @throws IOException
	 */
	public Server(int port) throws IOException {
		this.port = port;
		this.clients = new ArrayList<ConnectedClient>();

		Thread threadConnection = new Thread(new Connection(this));
		threadConnection.start();
	}

	/**
	 * add a new client to the list of connected clients
	 * @param newClient the new client
	 */
	public void addClient(ConnectedClient newClient) {
		for (ConnectedClient client : clients) {
                        Message mess = new Message("server", "Le client "+newClient.getId()+" vient de se connecter");
			client.sendMessage(mess);
		}
		this.clients.add(newClient);
	}

	
	/**
	 * Broadcasts a message to all connected clients but the one who sent it
	 * @param message the message to send
	 * @param id the client's id who sent the message 
	 */
	public void broadcastMessage(Message mess, int id) {
		for (ConnectedClient client : clients) {
			if (client.getId() != id) {
				client.sendMessage(mess);
			}
		}

	}

	/**
	 * Remove a client from the list of connected clients
	 * and invoke the closeClient() method on it
	 * @param id if of the client that just disconnected
	 * @throws IOException
	 */
	public void disconnectedClient(ConnectedClient discClient) throws IOException {
	
		
		clients.remove(discClient);				
		discClient.closeClient();
		
		//we tell everyone this client left
		for (ConnectedClient client : clients) {
                        Message mess = new Message("server", "Le client "+discClient.getId()+" nous a quitté");
			client.sendMessage(mess);
		}
		
	}

	/**
	 * getter for the port of the server
	 * @return the port
	 */
	public int getPort() {
		return port;
	}

	
	

}
