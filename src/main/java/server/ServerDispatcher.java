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
 *
 * ServerDispatcher is purposed to listen to incoming socket connections,
 * it will accept these, and instantiate SocketThreads, then manage them in
 * the List: threads.
 *
 * On the other side ServerDispatcher will get Payloads of signals from the
 * ProtocolGenerator class and push these signals further to the MessageHandler
 * class and also notify each SocketThread that there is a new signal to pull.
 */
public class ServerDispatcher implements Runnable
{
    private static final int PORT = 8080;
    private static final int THREAD_SLEEP = 100;
    private static ServerDispatcher serverDispatcher;
    private ServerSocket listener;
    private Logger logger;
    private volatile List<SocketThread> threads;
    private volatile boolean running = true;

    /**
     * Private constructor disable other classes of making instances of ServerDispatcher.
     *
     * @throws IOException
     */
    private ServerDispatcher() throws IOException
    {
        // Look for more suitable synchronized list.
        threads = new ArrayList<>();
        listener = new ServerSocket(PORT);
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
            listener.close();
            // Destroy this thread??
            running = false;
            pushToLogger("ServerDispatcher stopped.");
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
            while(running)
            {
                Thread.sleep(THREAD_SLEEP);
                Socket client = listener.accept();
                SocketThread socketThread = new SocketThread(client);
                threads.add(socketThread);
                socketThread.start();

                pushToLogger(String.format("Client %s connected", client.getInetAddress()));
            }

        } catch (IOException e){
            System.out.println(e.getMessage());
        } catch (InterruptedException e) {
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
