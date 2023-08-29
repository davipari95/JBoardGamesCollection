package Classes.Objects.CustomComponents;

import javax.swing.*;

import Interfaces.ITranslatable;

public class JTranslatableMenu extends JMenu implements ITranslatable
{
    private long languageReference = 0;
    private Object[] args;

    public JTranslatableMenu(long languageReference, Object... args)
    {
        this.languageReference = languageReference;
        this.args = args;
    }

    public JTranslatableMenu(String text)
    {
        setText(text);
    }

    @Override
    public long getLanguageReference() 
    {
        return languageReference;
    }

    @Override
    public void setLanguageReference(long value) 
    {
        if (value != languageReference)
        {
            languageReference = value;
        }
    }

    @Override
    public Object[] getArgs() 
    {
        return args;
    }

    @Override
    public void setArgs(Object... args) 
    {
        this.args = args;
    }
}
