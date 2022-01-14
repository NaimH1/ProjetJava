package client.network;

import common.Message;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Thread in charge of sending messages to the server
 *
 * @author Remi Watrigant
 *
 */
public class ClientSend implements Runnable {

    /**
     * a reference to the output stream
     */
    private ObjectOutputStream out;

    private Socket socket;

    /**
     *
     * @param client the client object
     * @param out the output stream
     */
    public ClientSend(Socket socket, ObjectOutputStream out) {
        this.socket = socket;
        this.out = out;
    }

    /**
     * Loop that read messages from the standard input (keyboard) and send it to
     * the server using the output stream
     */
    @Override
    public void run() {

        Scanner sc = new Scanner(System.in);

        while (true) {

            try {
                System.out.print("Votre message >> ");
                String m = sc.nextLine();

                Message mess = new Message("client", m);

                out.writeObject(mess);
                out.flush();

                System.out.println("message envoyé");
            } catch (IOException ex) {
                Logger.getLogger(ClientSend.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

}
