package Classes.Frames.Dialogs.Chess;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import Classes.Global.*;
import Classes.Objects.CustomComponents.*;
import Classes.Utils.UImages;
import Interfaces.Chess.*;

public class CheckmateWinnerDialog extends JDialog
{

    public enum DialogResultEnum
    {
        GAME_OVER,
        REMATCH
    }

    JLabel
        winnerIconLabel,
        winnerMessage;
    JTranslatableButton
        gameOverJTranslatableButton,
        rematchJTranslatableButton;
    IChessPiece.ColorEnum
        winnerColor;
    String
        winnerName;
    DialogResultEnum
        dialogResult = DialogResultEnum.GAME_OVER;
    MyActionListener
        myActionListener = new MyActionListener();
    
    public CheckmateWinnerDialog(String winnerName, IChessPiece.ColorEnum winnerColor)
    {
        this.winnerColor = winnerColor;
        this.winnerName = winnerName;

        initializeComponents();

        GlobalMain.sRegion.transltateComponentsInContainer(this);
    }

    private void initializeComponents()
    {
        //#region this
        getContentPane().setPreferredSize(new Dimension(280, 100));
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        setAlwaysOnTop(true);
        setResizable(false);
        setModal(true);
        setLayout(null);
        //#endregion

        //#region winnerIconLabel
        winnerIconLabel = new JLabel();
        winnerIconLabel.setSize(50, 50);
        winnerIconLabel.setLocation(10, 10);
        String winnerKingPath = String.format("Data/Chess/Assets/Pictures/ChessPieces/%sKing.png", winnerColor.getName());
        Icon image = UImages.getImageIconResized(winnerKingPath, 50, 50);
        winnerIconLabel.setIcon(image);
        add(winnerIconLabel);
        //#endregion

        //#region winnerMessage
        winnerMessage = new JLabel();
        winnerMessage.setSize(200, 50);
        winnerMessage.setLocation(70, 10);
        String message = GlobalMain.sRegion.getTranslatedText(18, winnerName);
        String text = String.format("<html><body>%s</body></html>", message);
        winnerMessage.setText(text);
        winnerMessage.setHorizontalAlignment(JLabel.CENTER);
        winnerMessage.setVerticalAlignment(JLabel.CENTER);
        add(winnerMessage);
        //#endregion

        //#region gameOverJTranslatableButton
        gameOverJTranslatableButton = new JTranslatableButton(20);
        gameOverJTranslatableButton.setSize(140, 30);
        gameOverJTranslatableButton.setLocation(0, 70);
        gameOverJTranslatableButton.addActionListener(myActionListener);
        add(gameOverJTranslatableButton);
        //#endregion

        //#region rematchJTranslatableButton
        rematchJTranslatableButton = new JTranslatableButton(19);
        rematchJTranslatableButton.setSize(140, 30);
        rematchJTranslatableButton.setLocation(140, 70);
        rematchJTranslatableButton.addActionListener(myActionListener);
        add(rematchJTranslatableButton);
        //#endregion

        pack();
        setLocationRelativeTo(null);
    }

    public DialogResultEnum getDialogResult()
    {
        return dialogResult;
    }

    private class MyActionListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            if (e.getSource() == gameOverJTranslatableButton)
            {
                dialogResult = DialogResultEnum.GAME_OVER;
                dispose();
            }

            if (e.getSource() == rematchJTranslatableButton)
            {
                dialogResult = DialogResultEnum.REMATCH;
                dispose();
            }
        }
    }

}
