package main.java.server;

import main.java.server.SocketThread;
import main.java.util.ProtocolGenerator;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hans on 23.02.16.
 */
public class ServerDispatcher implements Runnable {

    private static ServerDispatcher serverDispatcher = null;
    private List<SocketThread> threads = null;
    private ServerSocket listener = null;
    private static final int port = 8080;

    /**
     * Private constructor disable other classes of making instances of ServerDispatcher.
     * @throws IOException
     */
    private ServerDispatcher() throws IOException
    {
        threads = new ArrayList<>();
        listener = new ServerSocket(port);
    }

    /**
     * @param socketThread
     * @return Removes a SocketThread from the list.
     */
    protected synchronized boolean remove(SocketThread socketThread)
    {
        return threads.remove(socketThread);
    }

    /**
     * @return Return alive threads.
     */
    protected synchronized List<SocketThread> getThreads()
    {
        return threads;
    }

    /**
     * @Return the singleton ServerDispatcher instance.
     * @throws IOException
     */
    protected static ServerDispatcher getInstance() throws IOException
    {
        if(serverDispatcher == null)
            serverDispatcher = new ServerDispatcher();
        return serverDispatcher;
    }

    /**
     * Notify threads that MessageHandler has signal to them.
     */
    protected void newMessage(ProtocolGenerator.Payload payload)
    {
        // send to MessageHandler.
        MessageHandler.inst.setMessage(payload);
        // signal threads about
        threads.forEach(thread -> thread.update());
    }

    protected void exit()
    {
        try {
            listener.close();
        } catch (IOException e){

        }

    }

    @Override
    public void run() {
        try {
            if(serverDispatcher == null) {
                serverDispatcher = new ServerDispatcher();
            }
            while(true)
            {
                Socket client = listener.accept();
                SocketThread socketThread = new SocketThread(client);
                threads.add(socketThread);
                socketThread.start();
            }

        } catch (IOException e){

        }
    }
}
