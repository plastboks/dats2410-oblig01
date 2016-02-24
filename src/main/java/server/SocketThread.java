package main.java.server;

import main.java.poc.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by hans on 24.02.16.
 */
public class SocketThread extends Thread {

    private Socket connectSocket;
    private static final String HEARTBEAT = "TICK";
    private static final int THREAD_SLEEP = 100;
    private static final int MOD_NUM = 5;

    public SocketThread(Socket connectSocket)
    {
        this.connectSocket = connectSocket;
    }

    @Override
    public void run()
    {
        try (
                PrintWriter out = new PrintWriter(
                        connectSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(connectSocket.getInputStream()));
        )
        {
            String clientHost = connectSocket.getInetAddress().getHostAddress();
            System.out.printf("Created new thread with %s\n", clientHost);

            int c = 0;
            do {
                Thread.sleep(THREAD_SLEEP);

                if (++c % MOD_NUM == 0) {
                    out.println(Message.get());
                } else {
                    out.println(HEARTBEAT);
                }
                System.out.println("pong");

            } while (in.readLine() != null);

            System.out.printf("Closed connection with %s\n", clientHost);
            connectSocket.close();

        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    public void update(){}
}



