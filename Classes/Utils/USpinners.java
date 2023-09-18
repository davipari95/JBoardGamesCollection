package Classes.Utils;

import java.text.*;
import javax.swing.*;
import javax.swing.text.*;
import Classes.CustomExceptions.*;

public class USpinners 
{
    
    public static <T> void removeThousandsSeparator(JSpinner spinner)
    {
        JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor) spinner.getEditor();
        JFormattedTextField textField = editor.getTextField();
        
        SpinnerNumberModel model = null;

        if (!(spinner.getModel() instanceof SpinnerNumberModel))
        {
            throw new ArgumentsException("The spinner does not have a SpinnerNumberModel as model.");
        }

        model = (SpinnerNumberModel) spinner.getModel();

        DecimalFormat format = new DecimalFormat("#");
        
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(model.getMinimum());
        formatter.setMaximum(model.getMaximum());
        formatter.setAllowsInvalid(false);

        DefaultFormatterFactory defaultFormatterFactory = new DefaultFormatterFactory(formatter);
        
        textField.setFormatterFactory(defaultFormatterFactory);
    }
    
}
