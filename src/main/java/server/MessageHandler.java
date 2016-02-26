package main.java.server;

import main.java.poc.Message;
import main.java.util.Payload;
import main.java.util.Payload.ValidState;

/**
 * Created by alex on 2/17/16.
 */
public enum MessageHandler
{
    inst;

    private String message;

    MessageHandler()
    {
        message = generate(Payload.with());
    }

    public void setMessage(Payload payload)
    {
        this.message = generate(payload);
    }

    private static String generate(Payload payload)
    {
        return String.format("%d;%d;%d",
                payload.getGreen() == ValidState.OFF ? 0 : 1,
                payload.getYellow() == ValidState.OFF ? 0 : 1,
                payload.getRed() == ValidState.OFF ? 0 : 1);
    }

    public String getMessage()
    {
        return message;
    }
}
