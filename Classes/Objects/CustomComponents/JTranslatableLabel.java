package Classes.Objects.CustomComponents;

import javax.swing.JLabel;

import Interfaces.ITranslatable;

public class JTranslatableLabel extends JLabel implements ITranslatable
{
    private long languageReference = 0;
    Object[] args;

    public JTranslatableLabel(long languageReference)
    {
        this.languageReference = languageReference;
    }

    public JTranslatableLabel(String text)
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
