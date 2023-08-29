package Classes.Objects.CustomComponents;

import javax.swing.*;
import Interfaces.ITranslatable;

public class JTranslatableRadioButton extends JRadioButton implements ITranslatable
{

    long languageReference = 0;
    Object[] args;

    public JTranslatableRadioButton(long languageReference, Object... args)
    {
        this.languageReference = languageReference;
        this.args = args;
    }

    public JTranslatableRadioButton(String text)
    {
        super(text);
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
