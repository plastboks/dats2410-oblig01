package main.java.server;

import main.java.util.ProtocolGenerator;
import main.java.util.ProtocolGenerator.ValidState;
import main.java.util.ProtocolGenerator.Payload;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alex on 2/24/16.
 */
public class AutoLogic implements Runnable
{
    private List<ProtocolGenerator.Payload> payloadList;
    private boolean state;

    private ServerDispatcher dispatcher;

    public AutoLogic(ServerDispatcher dispatcher)
    {
        this.dispatcher = dispatcher;

        payloadList = new ArrayList<>();

        payloadList.add(Payload.with().red(ValidState.ON));
        payloadList.add(Payload.with().red(ValidState.ON).yellow(ValidState.ON));
        payloadList.add(Payload.with().green(ValidState.ON));
        payloadList.add(Payload.with().yellow(ValidState.ON));
    }

    public void setState(boolean state)
    {
        this.state = state;
    }

    @Override
    public void run()
    {
        int i = 0;
        while (true) {
            try {
                Thread.sleep(3000);
                if (state) {
                    dispatcher.newMessage(payloadList.get(i++ % payloadList.size()));
                }
            } catch (InterruptedException ie) {
            }
        }
    }
}
