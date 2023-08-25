package Classes.Frames.InternalFrames;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import Classes.Global.*;
import Classes.Global.Subs.Region.RegionListener;
import Classes.Objects.CustomComponents.*;
import Classes.Utils.UInternalFrames;

public class SettingsInternalFrame extends JInternalFrame
{
    JPanel
        centralPanel,
        southPanel;
    JTranslatableLabel
        languageJTranslatableLabel;
    JHashMapComboBox<String>
        availableLanguagesHashMapComboBox;
    JTranslatableButton
        cancelTranslatableButton,
        saveTranslatableButton;
    MyActionListener
        myActionListener = new MyActionListener();
    MyRegionListener
        myRegionListener = new MyRegionListener();

    public SettingsInternalFrame()
    {
        super("Settings", false, true, false, true);

        initializeComponents();

        translate();

        GlobalMain.sRegion.addRegionListener(myRegionListener);
    }

    private void initializeComponents()
    {
        //#region this
        setSize(270, 100);
        //#endregion

        //#region centralPanel
        centralPanel = new JPanel();
        centralPanel.setLayout(new GridBagLayout());
        GridBagConstraints gk = new GridBagConstraints();
        gk.fill = GridBagConstraints.HORIZONTAL;
        gk.insets = new Insets(10, 10, 10, 10);
        add(centralPanel, BorderLayout.CENTER);
        //#endregion

        //#region langaugJTranslatableLabel
        languageJTranslatableLabel = new JTranslatableLabel(33);
        languageJTranslatableLabel.setHorizontalAlignment(JLabel.RIGHT);
        gk.gridx = 0;
        gk.gridy = 0;
        gk.weightx = 1;
        centralPanel.add(languageJTranslatableLabel, gk);
        //#endregion

        //#region availableLanguagesHashMapComboBox
        availableLanguagesHashMapComboBox = new JHashMapComboBox<>(GlobalMain.sRegion.getAvailableRegions(), true);
        gk.gridx = 1;
        gk.gridy = 0;
        gk.weightx = 2;
        availableLanguagesHashMapComboBox.setSelectedKey(GlobalMain.sRegion.getActiveLanguage());
        centralPanel.add(availableLanguagesHashMapComboBox, gk);
        //#endregion

        //#region southPanel
        southPanel = new JPanel();
        southPanel.setLayout(new GridLayout(1, 2));
        add(southPanel, BorderLayout.SOUTH);
        //#endregion

        //#region cancelTranslatableButton
        cancelTranslatableButton = new JTranslatableButton(9);
        cancelTranslatableButton.addActionListener(myActionListener);
        southPanel.add(cancelTranslatableButton);
        //#endregion

        //#region saveTranslatableButton
        saveTranslatableButton = new JTranslatableButton(34);
        saveTranslatableButton.addActionListener(myActionListener);
        southPanel.add(saveTranslatableButton);
        //#endregion

        setLocation(UInternalFrames.retrivePointForCentering(this, GlobalMain.mdiPane));
    }

    private void translate()
    {
        setTitle(GlobalMain.sRegion.getTranslatedText(32));

        GlobalMain.sRegion.transltateComponentsInContainer(this);
    }

    private class MyActionListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e) 
        {
            if (e.getSource() == cancelTranslatableButton)
            {
                dispose();
            }

            if (e.getSource() == saveTranslatableButton)
            {
                String langKey = availableLanguagesHashMapComboBox.getSelectedKey();
                GlobalMain.sRegion.setActiveLanguage(langKey);
                dispose();
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
