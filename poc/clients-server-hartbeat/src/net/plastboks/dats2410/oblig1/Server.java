package net.plastboks.dats2410.oblig1;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Created by alex on 2/5/16.
 */
public class Server
{
    private static int port = 5555;

    public static void main(String ... args) throws InterruptedException
    {
        if (args.length == 0) {
            System.out.printf("Please provide port number argument!\n");
            System.exit(-1);
        }

        try {
            port = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.out.printf("Could not parse the port number argument: '%s'\n", args[0]);
            System.exit(-1);
        }

        System.out.printf("Starting server with port number: %d\n", port);
        try (
                ServerSocket srvSoc = new ServerSocket(port)
        )
        {
            while (true) {
                new SocketThread(srvSoc.accept()).start();
            }

        } catch (IOException e) {
            System.out.printf("Exception caught when trying to listen to port %d", port);
            System.out.println(e.getMessage());
        }
    }
}
