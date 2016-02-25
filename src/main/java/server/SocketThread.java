package main.java.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by hans on 24.02.16.
 */
public class SocketThread extends Thread {

    private Socket clientSocket;
    private static final String HEARTBEAT = "TICK";
    private static final int THREAD_SLEEP = 100;
    private volatile boolean newMessage = true; // MessageHandler has new message

    public SocketThread(Socket clientSocket)
    {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run()
    {
        try (
                PrintWriter out = new PrintWriter(
                        clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));
        )
        {
            String clientHost = clientSocket.getInetAddress().getHostAddress();
            System.out.printf("Created new thread with %s\n", clientHost);

            do {
                Thread.sleep(THREAD_SLEEP);

                if (newMessage) {
                    newMessage = false;
                    out.println(MessageHandler.inst.getMessage());
                } else {
                    out.println(HEARTBEAT);
                }

                System.out.println("pong");

            } while (in.readLine() != null);

            System.out.printf("Closed connection with %s\n", clientHost);
            clientSocket.close();

        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    public synchronized void update()
    {
        newMessage = true;
    }
}



