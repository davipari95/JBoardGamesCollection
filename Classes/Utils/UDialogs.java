package Classes.Utils;

import javax.swing.JOptionPane;
import Classes.Global.GlobalMain;

public class UDialogs 
{

    public enum DialogTypeEnum
    {
        YesNo(JOptionPane.YES_NO_OPTION),
        OkCancel(JOptionPane.OK_CANCEL_OPTION),
        YesNoCancel(JOptionPane.YES_NO_CANCEL_OPTION);

        private int value;

        private DialogTypeEnum(int value)
        {
            this.value = value;
        }

        public int getValue()
        {
            return value;
        }
    }

    public enum IconTypeEnum
    {
        Error(JOptionPane.ERROR_MESSAGE),
        Information(JOptionPane.INFORMATION_MESSAGE),
        Warning(JOptionPane.WARNING_MESSAGE),
        Question(JOptionPane.QUESTION_MESSAGE),
        Plain(JOptionPane.PLAIN_MESSAGE);

        private int value;

        private IconTypeEnum(int value)
        {
            this.value = value;
        }

        public int getValue()
        {
            return value;
        }
    }

    public enum DialogResultEnum
    {
        YesOrOk(JOptionPane.OK_OPTION),
        Cancel(JOptionPane.CANCEL_OPTION),
        No(JOptionPane.NO_OPTION);

        private int value;

        private DialogResultEnum(int value)
        {
            this.value = value;
        }

        public int getValue()
        {
            return value;
        }

        public static DialogResultEnum getResultEnum(int value)
        {
            for (DialogResultEnum dre : DialogResultEnum.values())
            {
                if (dre.getValue() == value)
                {
                    return dre;
                }
            }

            return null;
        }
    }

    public static DialogResultEnum showConfirmDialogTranslated(long titleId, long messageId, DialogTypeEnum dialogType, IconTypeEnum icon)
    {
        String title = GlobalMain.sRegion.getTranslatedText(titleId);
        String message = GlobalMain.sRegion.getTranslatedText(messageId);

        int result = JOptionPane.showInternalConfirmDialog(GlobalMain.mdiPane, message, title, dialogType.getValue(), icon.getValue());

        return DialogResultEnum.getResultEnum(result);
    }

    public static void showMessageDialogTranslated(long titleId, long messageId, IconTypeEnum icon)
    {
        String title = GlobalMain.sRegion.getTranslatedText(titleId);
        String message = GlobalMain.sRegion.getTranslatedText(messageId);

        JOptionPane.showInternalMessageDialog(GlobalMain.mdiPane, message, title, icon.getValue(), null);
    }

}
