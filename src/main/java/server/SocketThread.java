package main.java.server;

import main.java.util.HeartBeat;

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
    private static final long THREAD_SLEEP = 200;
    private volatile boolean isRead = false;
    private boolean isRunning = true;
    private HeartBeat heartBeat;
    private PrintWriter out;
    private BufferedReader in;

    public SocketThread(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;

        out = new PrintWriter(
                clientSocket.getOutputStream(), true);
        in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));

        heartBeat = new HeartBeat(out);
    }

    @Override
    public void run()
    {
        try {
            while (isRunning)
            {
                Thread.sleep(THREAD_SLEEP);
                sendSignal();
                receiveSignal();
                if (heartBeat.getState().equals(HeartBeat.State.DISCONNECTED))
                {
                    requestStop();
                }
            }
            killConnection();
        } catch (IOException e)
        {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void sendSignal() throws IOException
    {
        if(!isRead)
        {
            out.println(MessageHandler.inst.getMessage());
            isRead = true;
            //System.out.println(isRead);
        }
        else if(isRead)
        {
            heartBeat.sendSignal();
        }
    }

    private void receiveSignal() throws IOException
    {
        if(in.ready())
            heartBeat.receiveSignal(in.readLine());
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