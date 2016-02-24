package main.java.server;

import main.java.util.ProtocolGenerator;
import main.java.util.ProtocolGenerator.ValidState;
import main.java.util.ProtocolGenerator.Payload;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alex on 2/24/16.
 */
public class AutoLogic extends Thread
{
    private List<ProtocolGenerator.Payload> payloadList;
    private boolean state;
    private boolean running = true;

    private int randomTimer = 3000; // not very random...

    private ServerDispatcher dispatcher;
    private ServerController controller;

    public AutoLogic(ServerController controller, ServerDispatcher dispatcher)
    {
        this.controller = controller;
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

    public void kill()
    {
        running = false;
    }

    @Override
    public void run()
    {
        int i = 0;
        while (running) {
            try {
                if (state) {
                    controller.setLights(payloadList.get(i % payloadList.size()));
                    dispatcher.newMessage(payloadList.get(i % payloadList.size()));
                    i++;
                }
                Thread.sleep(randomTimer);
            } catch (InterruptedException ie) { }
        }
    }
}
