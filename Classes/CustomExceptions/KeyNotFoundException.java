package Classes.CustomExceptions;

public class KeyNotFoundException extends RuntimeException
{

    public KeyNotFoundException(String message)
    {
        super(message);
    }

}
