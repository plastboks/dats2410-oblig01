package main.java.server;

import com.sun.istack.internal.NotNull;
import main.java.util.ProtocolGenerator;
import main.java.view.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hans on 23.02.16.
 */
public class ServerDispatcher implements Runnable
{

    private static ServerDispatcher serverDispatcher = null;
    private List<SocketThread> threads = null;
    private ServerSocket listener = null;
    private static final int port = 8080;
    private Logger logger;
    private boolean running = false;

    /**
     * Private constructor disable other classes of making instances of ServerDispatcher.
     *
     * @throws IOException
     */
    private ServerDispatcher() throws IOException
    {
        // Look for more suitable synchronized list.
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
     * @throws IOException
     * @Return the singleton ServerDispatcher instance.
     */
    protected synchronized static ServerDispatcher getInstance() throws IOException
    {
        if (serverDispatcher == null)
            serverDispatcher = new ServerDispatcher();

        return serverDispatcher;
    }

    /**
     * Notify threads that MessageHandler has signal to them.
     */
    protected synchronized void newMessage(ProtocolGenerator.Payload payload)
    {
        // send to MessageHandler.
        MessageHandler.inst.setMessage(payload);
        // signal threads about
        logger.push(payload.get());
        threads.forEach(thread -> thread.update());
    }

    /**
     * Closes the ServerSocket listener.
     */
    protected synchronized void exit()
    {
        try {
            System.out.println("Closing server socket.");
            listener.close();
            // Destroy this thread??
            running = false;
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public synchronized void run() {
        try {
            if (serverDispatcher == null) {
                serverDispatcher = new ServerDispatcher();
            }
            running = true;
            while(running)
            {
                Socket client = listener.accept();
                SocketThread socketThread = new SocketThread(client);
                threads.add(socketThread);
                socketThread.start();

                pushToLogger(String.format("Client %s connected", client.getInetAddress()));
            }

        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    private void pushToLogger(String str)
    {
        if (logger != null) logger.push("ServerDispatcher: " + str);
    }

    public void setLogger(@NotNull Logger logger)
    {
        this.logger = logger;
        pushToLogger("Connected to logger");
    }
}
