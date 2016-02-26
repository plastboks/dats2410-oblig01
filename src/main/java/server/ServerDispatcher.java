package main.java.server;

import com.sun.istack.internal.NotNull;
import main.java.util.Payload;
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
 * Payload class and push these signals further to the MessageHandler
 * class and also notify each SocketThread that there is a new signal to pull.
 */
public class ServerDispatcher extends Thread
{
    private static final int PORT = 8080;
    private static final int THREAD_SLEEP = 100;
    private static ServerDispatcher serverDispatcher;
    private ServerSocket listener;
    private Logger logger;
    private volatile List<SocketThread> threads;
    private volatile boolean isRunning = true;
    private final Object threadListLock = new Object();
    private final Object listenerLock = new Object();

    /**
     * Private constructor disable other classes of making instances of ServerDispatcher.
     *
     * @throws IOException
     */
    private ServerDispatcher() throws IOException
    {
        // Look for more suitable synchronized list.
        synchronized (threadListLock)
        {
            threads = new ArrayList<>();
        }

        listener = new ServerSocket(PORT);
    }

    @Override
    public void run()
    {
        try
        {
            if (serverDispatcher == null)
            {
                serverDispatcher = new ServerDispatcher();
            }
            while(isRunning)
            {
                Socket client;

                synchronized (listenerLock)
                {
                    client = listener.accept();
                }

                SocketThread socketThread = new SocketThread(client);

                synchronized (threadListLock)
                {
                    threads.add(socketThread);
                }

                socketThread.start();

                pushToLogger(String.format("Client %s connected", client.getInetAddress()));
            }
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    /**
     *
     * @return
     */
    public List<String> getConnectedHosts()
    {
        List<String> hosts = new ArrayList<>();

        threads.forEach(thread -> hosts.add(thread.getClientHost()));

        return hosts;
    }

    /**
     * @throws IOException
     * @return the singleton ServerDispatcher instance.
     */
    public static ServerDispatcher getInstance() throws IOException
    {
        if (serverDispatcher == null)
            serverDispatcher = new ServerDispatcher();

        return serverDispatcher;
    }

    /**
     * Method newMessage takes Payload and forwards it to
     * the MessageHandler who will distribute the Payload
     * further.
     *
     * Next the newMessage method takes care of signaling
     * each SocketThread that there is a new Payload ready
     * for polling at the MessageHandler.
     *
     * @param payload
     */
    protected void newMessage(Payload payload)
    {

        MessageHandler.inst.setMessage(payload);

        logger.push(MessageHandler.inst.getMessage());

        synchronized (threadListLock)
        {
            threads.forEach(thread -> thread.update());
        }
    }

    /**
     * The requestStop method disconnects the ServerSocket,
     * and stops the HeartBeat loop from running. This
     * stops the ServerDispatching.
     */
    public void requestStop()
    {
        try
        {
            isRunning = false;
            synchronized (listenerLock)
            {
                listener.close();
            }
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    /**
     * @param socketThread
     * @return Removes a SocketThread from the list.
     */
    protected void remove(SocketThread socketThread)
    {
        synchronized (threadListLock)
        {
            pushToLogger("Closed connection with " + socketThread.getClientHost());
            threads.remove(socketThread);
            // send signal to ServerController
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
