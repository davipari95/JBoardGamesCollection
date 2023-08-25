package Interfaces;

public interface ITranslatable 
{
    long getLanguageReference();
    void setLanguageReference(long value);

    Object[] getArgs();
    void setArgs(Object... args);
}
