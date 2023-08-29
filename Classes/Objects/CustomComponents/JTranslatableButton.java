package Classes.Objects.CustomComponents;

import javax.swing.JButton;

import Interfaces.ITranslatable;

public class JTranslatableButton extends JButton implements ITranslatable
{
    private long languageReference = 0;
    private Object[] args;

    public JTranslatableButton(long languageReference, Object... args)
    {
        this.languageReference = languageReference;
        this.args = args;
    }

    public JTranslatableButton(String text)
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
    public void setArgs(Object... args) {
        this.args = args;
    }
}
