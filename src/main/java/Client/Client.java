package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Simon on 17.02.2016.
 */
public class Client
{
    private static String host = "10.253.9.95";
    private static int port = 1234;

    private static final int THREAD_SLEEP = 100;
    private static final String HARTBEAT = "tock";
    private static final String SERVER_BEAT = "tick";

    /* Få inn instans av (client)APP.java */

    public static void main(String[] args) throws InterruptedException
    {

        if (args.length < 2) {
            System.out.printf("Please provide the host and port argument!\n");
            System.exit(-1);
        }

        try {
            port = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.out.printf("Could not parse the port number argument: '%s'\n", args[0]);
            System.exit(-1);
        }

        try (
                Socket soc = new Socket(host, port);
                PrintWriter out =
                        new PrintWriter(soc.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(soc.getInputStream()));
        )
        {
            Protocolparser parser =  new Protocolparser();
            String receivedText;
            while ((receivedText = in.readLine()) != null) {

                Thread.sleep(THREAD_SLEEP);

                if (!receivedText.equals(SERVER_BEAT)) {
                    parser.signalparse(receivedText); //Send med client APP.java instans
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
        }
    }



}

