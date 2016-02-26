package main.java.server;

import com.sun.istack.internal.NotNull;
import main.java.view.Logger;

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
    private Logger logger;
    private static final String HEARTBEAT = "TICK";
    private static final int THREAD_SLEEP = 100;
    private volatile boolean readSignal = false;

    public SocketThread(Socket clientSocket, Logger logger)
    {
        this.clientSocket = clientSocket;
        this.logger = logger;
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

            pushToLogger("Created new thread with " + clientHost);

            do {
                Thread.sleep(THREAD_SLEEP);

                if (!readSignal) {
                    readSignal = true;
                    out.println(MessageHandler.inst.getMessage());
                } else {
                    out.println(HEARTBEAT);
                }

                System.out.println("pong");

            } while (in.readLine() != null);

            pushToLogger("Closed connection with " + clientHost);
            clientSocket.close();
            ServerDispatcher.getInstance().remove(this);

        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * The SocketThreads update method notify the
     * heartbeat-loop about a new message at the
     * MessageHandler class.
     */
    public synchronized void update()
    {
        readSignal = false;
    }

    private void pushToLogger(String str)
    {
        if (logger != null) logger.push("SocketThread: " + str);
    }

    public void setLogger(@NotNull Logger logger)
    {
        this.logger = logger;
        pushToLogger("Connected to logger");
    }
}



