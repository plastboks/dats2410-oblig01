package main.java.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by hans on 24.02.16.
 * Class SocketThread manage a client socket connection and communication.
 *
 */
public class SocketThread extends Thread {

    private Socket clientSocket;
    private volatile boolean isRead = false;
    private boolean isRunning = true;
    private long lastSentSignal;
    private long lastReceivedSignal;
    private long requestedTimeout;
    private long HB_INT = 200;
    private int life = 3;

    private static final String SERVER_HB = "PING";

    private PrintWriter out;
    private BufferedReader in;

    public SocketThread(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;

        out = new PrintWriter(
                clientSocket.getOutputStream(), true);
        in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));

        isRead = false;
        isRunning = true;
        requestedTimeout = 10000; // 10 seconds

    }

    @Override
    public void run()
    {
        try {
            while (isRunning)
            {
                sendSignal();
                receiveSignal();
                calculate();
            }
            killConnection();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    protected void receiveSignal() throws IOException
    {
        if(in.readLine() != null)
        {
            lastReceivedSignal = System.currentTimeMillis();
            life = 3;
        }
    }

    protected void sendSignal() throws IOException
    {
        if(isRead)
        {
            if(lastSentSignal > HB_INT )
            {
                out.println(SERVER_HB);
                lastSentSignal = System.currentTimeMillis();
            }
        } else {
            out.println(MessageHandler.inst.getMessage());
            lastSentSignal = System.currentTimeMillis();
        }
    }

    protected void calculate() throws IOException {
        if((lastSentSignal - lastReceivedSignal) > requestedTimeout)
        {
            if(life == 0)
            {
                requestStop();
            }

            lastReceivedSignal = System.currentTimeMillis();
            life--;
        }
    }

    protected void killConnection() throws IOException {
        clientSocket.close();
        ServerDispatcher.getInstance().remove(this);
    }

    private void requestStop()
    {
        isRunning = false;
    }

    protected String getClientHost()
    {
        return clientSocket.getInetAddress().getHostAddress();
    }

    public synchronized void update()
    {
        isRead = false;
    }
}