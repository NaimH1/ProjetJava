package client.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import client.view.ClientPanel;
import client.view.IHMBuilderControler;
import common.Message;

/**
 * Class implementing a simple client: - reads messages from standard input -
 * writes in standard output the received messages. Based on a TCP connexion
 *
 * @author Remi Watrigant
 *
 */
public class Client {

	/*
	 * Server's port
	 */
	private int port;

	/*
	 * Server's address
	 */
	private String address;

	/*
	 * The socket we communicate through
	 */
	private Socket socket;

	/*
	 * input and output streams to communicate to the server
	 */
	private ObjectOutputStream out;
	private ObjectInputStream in;

	private ClientPanel view;

	private IHMBuilderControler iHMBuilderControler;

	/**
	 * opens the socket, the input and output streams, and start two threads, one to
	 * send messages (Class ClientSend), one to receive messages (class
	 * ClientReceive)
	 *
	 * @param port    the port the server listens to
	 * @param address the ip address of the server
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public Client(String address, Integer port) throws UnknownHostException, IOException {

		this.address = address;
		this.port = port;

		socket = new Socket(address, port);

		out = new ObjectOutputStream(socket.getOutputStream());

//		Thread threadSend = new Thread(new ClientSend(socket, out));
//		threadSend.start();

		Thread threadReceive = new Thread(new ClientReceive(this, socket));
		threadReceive.start();
	}

	public void disconnectedServer() throws IOException {

		System.out.println("Le server s'est déconnecté");

		out.close();
		socket.close();

		System.exit(0);
	}

	public void setView(ClientPanel clientPanel) {
		this.view = clientPanel;
	}

	public void setViewControler(IHMBuilderControler iHMBuilderControler) {
		this.iHMBuilderControler = iHMBuilderControler;
	}

	public void sendMessage(String text) throws IOException {
		Message mess = new Message("client", text);

		out.writeObject(mess);
		out.flush();
	}

	void messageReceived(Message mess) {

		if (this.view != null)
			this.view.printNewMessage(mess);
		else
			this.iHMBuilderControler.printNewMessage(mess);

	}

}
