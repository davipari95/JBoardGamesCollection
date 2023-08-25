package Classes.Frames.InternalFrames.Chess;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import Classes.Global.*;
import Classes.Global.Subs.Region.*;
import Classes.Objects.CustomComponents.*;
import Classes.Utils.*;

public class ChessHotSeatSettingsInternalFrame extends JInternalFrame
{
    
    JPanel
        formJPanel,
        buttonsJPanel;
    JTranslatableLabel
        whitesTranslatableLabel,
        blacksTranslatableLabel;
    JTextField
        whitesNameTextField,
        blacksNameTextField;
    JTranslatableButton
        cancelTranslatableButton,
        playTranslatableButton;
    MyActionListener
        myActionListener = new MyActionListener();
    MyRegionListener
        myRegionListener = new MyRegionListener();

    public ChessHotSeatSettingsInternalFrame()
    {
        super("", false, true, false, true);

        initializeComponents();

        translate();

        GlobalMain.sRegion.addRegionListener(myRegionListener);
    }

    private void initializeComponents()
    {
        //#region this
        setSize(0, 0);
        getContentPane().setPreferredSize(new Dimension(400, 100));
        setDefaultCloseOperation(ChessHotSeatSettingsInternalFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        //#endregion

        //#region formJPanel
        formJPanel = new JPanel();
        formJPanel.setLayout(new GridLayout(2, 2));
        add(formJPanel, BorderLayout.CENTER);
        //#endregion

        //#region whitesTranslatableLabel
        whitesTranslatableLabel = new JTranslatableLabel(6);
        whitesTranslatableLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        formJPanel.add(whitesTranslatableLabel);
        //#endregion

        //#region whitesNameTextField
        whitesNameTextField = new JTextField();
        whitesNameTextField.setText(GlobalMain.isDebug() ? "Walter White" : "");
        formJPanel.add(whitesNameTextField);
        //#endregion

        //#region blacksTranslatableLabel
        blacksTranslatableLabel = new JTranslatableLabel(7);
        blacksTranslatableLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        formJPanel.add(blacksTranslatableLabel);
        //#endregion

        //#region blacksNameTextField
        blacksNameTextField = new JTextField();
        blacksNameTextField.setText(GlobalMain.isDebug() ? "Sirius Black" : "");
        formJPanel.add(blacksNameTextField);
        //#endregion

        //#region buttonsJPanel
        buttonsJPanel = new JPanel();
        buttonsJPanel.setLayout(new GridLayout(1, 2));
        add(BorderLayout.SOUTH, buttonsJPanel);
        //#endregion

        //#region cancelTranslatableButton
        cancelTranslatableButton = new JTranslatableButton(9);
        cancelTranslatableButton.addActionListener(myActionListener);
        buttonsJPanel.add(cancelTranslatableButton);
        //#endregion

        //#region playTranslatableButton
        playTranslatableButton = new JTranslatableButton(1);
        playTranslatableButton.addActionListener(myActionListener);
        buttonsJPanel.add(playTranslatableButton);
        //#endregion

        pack();
        setLocation(UInternalFrames.retrivePointForCentering(this, GlobalMain.mdiPane));
    }

    private void translate()
    {
        setTitle(GlobalMain.sRegion.getTranslatedText(8));

        GlobalMain.sRegion.transltateComponentsInContainer(this);
    }

    private class MyActionListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e) 
        {
            if (e.getSource() == cancelTranslatableButton)
            {
                GlobalMain.fMainFrame.setVisible(true);
                ChessHotSeatSettingsInternalFrame.this.dispose();
            }

            if (e.getSource() == playTranslatableButton)
            {
                String whitesPlayerName = whitesNameTextField.getText();
                String blacksPlayerName = blacksNameTextField.getText();

                boolean isWPlayerBlank = whitesPlayerName.isEmpty();
                boolean isBPlayerBlank = blacksPlayerName.isEmpty();

                if (isWPlayerBlank || isBPlayerBlank)
                {
                    String title = GlobalMain.sRegion.getTranslatedText(10);
                    String message = GlobalMain.sRegion.getTranslatedText(11);

                    JOptionPane.showMessageDialog(null, message, title, JOptionPane.WARNING_MESSAGE);

                    whitesNameTextField.setBackground(isWPlayerBlank ? Color.YELLOW : UIManager.getColor("TextField.background"));
                    blacksNameTextField.setBackground(isBPlayerBlank ? Color.YELLOW : UIManager.getColor("TextField.background"));

                    return;
                }

                ChessHotSeatGameInternalFrame frame = new ChessHotSeatGameInternalFrame(whitesPlayerName, blacksPlayerName);
                GlobalMain.mdiPane.add(frame);
                frame.setVisible(true);
                ChessHotSeatSettingsInternalFrame.this.dispose();

            }
        }
        
    }

    private class MyRegionListener implements RegionListener
    {

        @Override
        public void activeLangaugeChangedEvent(String activeLanguage) 
        {
            translate();
        }
        
    }
}
