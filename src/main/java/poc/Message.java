package main.java.poc;

/**
 * Created by alex on 2/5/16.
 */
public class Message
{
    private static int counter = 0;

    public static String get()
    {
        return String.format("The count is %d", counter++);
    }
}