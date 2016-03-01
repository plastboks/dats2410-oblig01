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
    private static final int THREAD_SLEEP = 100;

    private String host;
    private int port;
    private ClientController controller;
    private boolean running = true;
    private Logger logger;
    private HeartBeat heartBeat;

    public ClientSocket(String host, int port, ClientController controller)
    {
        this.host = host;
        this.port = port;
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
                heartBeat = new HeartBeat(out);
                String receivedText;
                while ((receivedText = in.readLine()) != null) {

                    Thread.sleep(THREAD_SLEEP);
                    if(!running){
                        throw new InterruptedException();
                    }
                    if (!receivedText.equals(HeartBeat.INTERVAL_PING)) {
                        if(parser.signalparse(receivedText)) {
                            controller.setLights(parser.getPayload());
                            pushToLogger(parser.getPayload().toString());
                        }
                    } else if(receivedText.equals(HeartBeat.INTERVAL_PING)) {
                        heartBeat.receiveSignal(receivedText);
                        heartBeat.sendSignal();
                    }
                    if(heartBeat.getState() == HeartBeat.State.DISCONNECTED)
                        kill();
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

