package Classes.Utils;

import javax.swing.JOptionPane;
import Classes.Global.GlobalMain;

public class UDialogs 
{

    /**
     * Type of dialog.<p/> 
     * Used to choose which buttons to show.
     */
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

        /**
         * 
         * @return enum value <ul><li>0 = Yes & No </li><li>1 = Yes & No & Cancel</li><li>2 = Ok & Cancel</li></ul>
         */
        public int getValue()
        {
            return value;
        }
    }

    /**
     * Icon displayed in the dialog.
     */
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

        /**
         * @return enum value
         * <ul>
         *  <li> 0 = Error </li>
         *  <li> 1 = Information </li>
         *  <li> 2 = Warning </li>
         *  <li> 3 = Question </li>
         *  <li> 4 = Plain </li>
         * </ul>
         */
        public int getValue()
        {
            return value;
        }
    }

    /**
     * Result of the clicked button.
     */
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

        /**
         * @return enum value
         * <ul>
         *  <li> 0 = OK or YES </li>
         *  <li> 1 = NO </li>
         *  <li> 2 = CANCEL </li>
         * </ul>
         */
        public int getValue()
        {
            return value;
        }

        /**
         * Retrive the enum from the value.
         * @param value enum value
         * @return      <code>DialogResultEnum</code> value if the <code>value</code> inserted exists, otherwise <code>null</code>.
         */
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

    /**
     * Show a translated confirm dialog. "Confirm dialog" means that will show a dialog with these following buttons:
     * <ul>
     *      <li>Yes and no</li>
     *      <li>Ok and cancel</li>
     *      <li>Yes, no and cancel</li>
     * </ul>
     * 
     * <pre>&nbsp;</pre>
     * <p/>
     * 
     * @param titleId       the language reference of the title.
     * @param messageId     the language reference of the message.
     * @param dialogType    buttons to show in this confirm dialog ((Yes or No), (Ok or Cancel), (Yes or No or Cancel)).
     * @param icon          icon to show in this confirm dialog (plain, error, information, warning, question).
     * @return              <code>DialogResultEnum</code> that returns the pressed button.
     */
    public static DialogResultEnum showConfirmDialogTranslated(long titleId, long messageId, DialogTypeEnum dialogType, IconTypeEnum icon)
    {
        String title = GlobalMain.sRegion.getTranslatedText(titleId);
        String message = GlobalMain.sRegion.getTranslatedText(messageId);

        int result = JOptionPane.showInternalConfirmDialog(GlobalMain.mdiPane, message, title, dialogType.getValue(), icon.getValue());

        return DialogResultEnum.getResultEnum(result);
    }

    /**
     * Show a translatad message dialog. "Messa dialog" means that will show a dialog with "OK" button.
     * @param titleId       the language reference of the title.
     * @param messageId     the language reference of the message.
     * @param icon          icon to show in this confirm dialog (plain, error, information, warning, question).
     */
    public static void showMessageDialogTranslated(long titleId, long messageId, IconTypeEnum icon)
    {
        String title = GlobalMain.sRegion.getTranslatedText(titleId);
        String message = GlobalMain.sRegion.getTranslatedText(messageId);

        JOptionPane.showInternalMessageDialog(GlobalMain.mdiPane, message, title, icon.getValue(), null);
    }

}
