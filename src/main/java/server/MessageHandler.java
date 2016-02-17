package main.java.server;

import main.java.util.ProtocolGenerator;

/**
 * Created by alex on 2/17/16.
 */
public enum MessageHandler
{
    inst;

    private String message;

    /**
     * Usage: (assumes correct imports)
     *    MessageHandler.inst.setMessage(Payload.with().red(ON).green(ON).yellow(OFF));
     *
     * @param payload
     */
    public void setMessage(ProtocolGenerator.Payload payload)
    {
        this.message = payload.get();
    }

    public String getMessage(String message)
    {
        return message;
    }
}
