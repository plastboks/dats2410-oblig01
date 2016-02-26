package main.java.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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



    public ClientSocket(ClientController controller)
    {
        this.controller=controller;
    }


    @Override
    public void run() {
        super.run();


        try (
                Socket soc = new Socket(host, port);
                PrintWriter out =
                        new PrintWriter(soc.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(soc.getInputStream()));
        )
        {
            Protocolparser parser =  new Protocolparser(controller);
            String receivedText;
            while ((receivedText = in.readLine()) != null) {

                Thread.sleep(THREAD_SLEEP);

                if (!receivedText.equals(SERVER_BEAT)) {
                   if(parser.signalparse(receivedText))
                       controller.setLights(parser.getPayload());

                }
                System.out.println("ping");
                out.println(HARTBEAT);
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

        }
    }
}

