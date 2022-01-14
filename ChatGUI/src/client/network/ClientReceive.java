package client.network;

import common.Message;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Thread in charge of the reception of messages from the server
 *
 * @author Remi Watrigant
 *
 */
public class ClientReceive implements Runnable {

    /**
     * a reference to the client object
     */
    private Client client;

    /**
     * a reference to the input stream
     */
    private ObjectInputStream in;
    
    private Socket socket;

    /**
     * @param client the client object
     * @param in the input stream
     */
    public ClientReceive(Client client, Socket socket) {
        this.client = client;
        this.socket = socket;
    }

    /**
     * Loop that reads messages from the input stream (i.e. reception of
     * messages from the server) if we receive null, it means the server
     * disconnected, in which case we inform the Client object
     */
    @Override
    public void run() {

        try {
            
            


            boolean isActive = true;
            while (isActive) {
                try {
                    Message mess;
                    System.out.println("attente de nouveaux message");
                    
                    if (in==null) {
                        in = new ObjectInputStream(socket.getInputStream());
                    }
                    mess = (Message) in.readObject();
                    
                    
                    
                    

                    if (mess != null) {
                        System.out.println("\nMessage reçu : " + mess);
                        this.client.messageReceived(mess);
                    } else {
                        isActive = false;
                    }
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(ClientReceive.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
            client.disconnectedServer();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
