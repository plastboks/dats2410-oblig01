package main.java.client;

import main.java.util.HeartBeat;
import main.java.view.Logger;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;


/**
 * Created by Simon on 17.02.2016.
 */
public class ClientSocket extends Thread
{
    private static String host = "127.0.0.1";
    private static int port = 8080;

    private static final int THREAD_SLEEP = 100;
    private static final String HARTBEAT = "tock";
    private static final String SERVER_BEAT = "tick";
    private static ClientController controller;
    private boolean running = true;
    private Logger logger;
    private HeartBeat heartBeat;

    public ClientSocket(ClientController controller)
    {
        this.controller=controller;
    }

    public void kill()
    {

        running=false;
    }


    @Override
    public void run() {
        super.run();


        try (
                Socket soc = new Socket(host, port);
                PrintWriter out =
                        new PrintWriter(soc.getOutputStream(), true);

        BufferedReader in = new BufferedReader(
                        new InputStreamReader(soc.getInputStream()))
        )
        {

            Protocolparser parser =  new Protocolparser(controller);

            HeartBeat heartBeat = new HeartBeat(out);

            String receivedText;
            while ((receivedText = in.readLine()) != null) {

                Thread.sleep(THREAD_SLEEP);
                if(!running){
                    throw new InterruptedException();
                }
                if (!receivedText.equals(SERVER_BEAT)) {
                    if(parser.signalparse(receivedText)) {
                        controller.setLights(parser.getPayload());
                        pushToLogger(parser.getPayload().toString());
                    }
                } else {
                    heartBeat.receiveSignal();
                }

                if((heartBeat.getLSP() - System.currentTimeMillis()) >= HeartBeat.INTERVAL)
                    heartBeat.sendSignal();
            }

            System.out.println("Server disconnected");

        } catch (UnknownHostException uhe) {
            System.err.printf("Unknown host %s", host);
            System.exit(1);
        } catch (IOException ioe) {
            System.err.printf("Could not get IO from the connection to: %s", host);
            System.exit(1);
        }catch(InterruptedException ie)
        {
            System.exit(1);
        }


    }

    private void pushToLogger(String str)
    {
        if (logger != null) logger.push("ClientSocket: " + str);
    }

    public void setLogger(Logger logger)
    {
        this.logger = logger;
        pushToLogger("Connected to logger");
    }
}