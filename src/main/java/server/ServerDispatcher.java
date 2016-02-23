package main.java.server;

import main.java.poc.SocketThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by hans on 23.02.16.
 */
public class ServerDispatcher  {

    private static ServerDispatcher serverDispatcher = null;
    private ArrayList<Thread> clientThreads = null;
    private ServerSocket listener = null;
    private static final int port = 8080;

    private ServerDispatcher() throws IOException
    {
        clientThreads = new ArrayList<>();
        listener = new ServerSocket(port);
    }

    protected synchronized void dispatch() throws IOException
    {
        while(true)
        {
            Socket client = listener.accept();
            SocketThread socketThread = new SocketThread(client);
            clientThreads.add(socketThread);
            socketThread.start();
        }
    }

    protected synchronized boolean remove(SocketThread socketThread)
    {
        return clientThreads.remove(socketThread);
    }

    protected synchronized ArrayList<Thread> getThreads()
    {
        return clientThreads;
    }

    protected static ServerDispatcher getInstance() throws IOException
    {
        if(serverDispatcher == null)
            serverDispatcher = new ServerDispatcher();
        return serverDispatcher;
    }

}
