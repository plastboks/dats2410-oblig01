package main.java.server;

/**
 * Created by alex on 2/17/16.
 */
public enum MessageHandler
{
    inst;

    private String message;

    public void setMessage(String message)
    {
        this.message = message;
    }

    public String getMessage(String message)
    {
        return message;
    }
}
