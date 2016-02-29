package main.java.util;

import java.io.PrintWriter;
import java.util.Date;

/**
 * Created by hans on 28.02.16.
 */
public class HeartBeat {

    private long LRP; // Last Received Ping
    private long LSP; // Last Sent Ping
    public static final long INTERVAL = 2000; // HB pinging interval
    private long timeout;
    private State state;
    private PrintWriter output;
    private static final String INTERVAL_PING = "PING";

    public enum State {
        NORMAL, WAITING, TIMEOUT, DISCONNECTED
    }

    /**
     * Standard construct that sets timeout parameter to 10 seconds.
     * @param output
     */
    public HeartBeat(PrintWriter output)
    {
        this(output, 10000);
    }

    public HeartBeat(PrintWriter output, long timeout)
    {
        if(timeout < 1)
            throw new IllegalArgumentException("Variable timeout must be larger than 0.");

        this.timeout = timeout;
        this.output = output;
        state = State.NORMAL;
        LRP = LSP = System.currentTimeMillis();
    }

    public void receiveSignal()
    {
        System.out.println("received signal: \t" + new Date().toString());
        LRP = System.currentTimeMillis();
        state = State.NORMAL;
    }

    public void sendSignal()
    {
        System.out.println("sent signal: \t" + new Date().toString());
        output.println(INTERVAL_PING);
        LSP = System.currentTimeMillis();
        setState();
    }

    private void setState()
    {
        if((System.currentTimeMillis() - LRP) > timeout)
        {
            if(state == State.NORMAL)
                state = State.WAITING;
            else if(state == State.WAITING)
                state = State.TIMEOUT;
            else
                state = State.DISCONNECTED;

            LRP = LSP = System.currentTimeMillis();
            System.out.println("State: " + state);
            return;
        }
        System.out.println("State: " + state);
    }

    public State getState()
    {
        return state;
    }

    public long getLRP()
    {
        return LRP;
    }

    public long getLSP()
    {
        return LSP;
    }

}
