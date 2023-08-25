package Classes.Frames;

import java.awt.event.*;
import javax.swing.*;
import Classes.Frames.InternalFrames.*;
import Classes.Frames.InternalFrames.Chess.*;
import Classes.Global.*;
import Classes.Global.Subs.Region.*;
import Classes.Objects.CustomComponents.*;
import Classes.Utils.*;
import Classes.Utils.UDialogs.*;
import Enums.*;

public class MainFrame extends JFrame
{
    JMenuBar
        menuBar;
    JTranslatableMenu
        fileTranslatableMenu,
        gameTranslatableMenu,
        optionsTranslatableMenu,
        gameChessTranslatableMenu,
        gameChessLanTranslatableMenu;
    JTranslatableMenuItem
        optionsSettingsTranslatableMenuItem,
        gameChessHotseatJTranslatableMenuItem,
        gameChessOpenRulesTranslatableMenuItem,
        gameChessLanCreateGameTranslatableMenuItem,
        gameChessLanJoinGameTranslatableMenuItem;
    JSeparator
        gameChessSeparator;
    MyActionListener
        myActionListener = new MyActionListener();
    MyRegionListener
        myRegionListener = new MyRegionListener();
    MyWindowListener
        myWindowListener = new MyWindowListener();

    public MainFrame()
    {
        initializeComponents();

        translate();

        GlobalMain.sRegion.addRegionListener(myRegionListener);
    }

    private void initializeComponents()
    {
        //#region this
        setTitle("J - Board Games Collection");
        setSize(800, 600);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        GlobalMain.mdiPane = new JDesktopPane();
        setContentPane(GlobalMain.mdiPane);
        setIconImage(new ImageIcon("Data/Icon.png").getImage());
        addWindowListener(myWindowListener);
        //#endregion

        //#region menuBar
        menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        //#endregion

        //#region gameTranslatableMenu
        gameTranslatableMenu = new JTranslatableMenu(30);
        menuBar.add(gameTranslatableMenu);
        //#endregion

        //#region optionsTranslatableMenu
        optionsTranslatableMenu = new JTranslatableMenu(31);
        menuBar.add(optionsTranslatableMenu);
        //#endregion

        //#region optionsSettingsTranslatableMenuItem
        optionsSettingsTranslatableMenuItem = new JTranslatableMenuItem(32);
        optionsSettingsTranslatableMenuItem.addActionListener(myActionListener);
        optionsTranslatableMenu.add(optionsSettingsTranslatableMenuItem);
        //#endregion

        //#region gameChessTranslatableMenu
        gameChessTranslatableMenu = new JTranslatableMenu(35);
        gameChessTranslatableMenu.setIcon(Enums.GameEnum.CHESS.getFavIcon());
        gameTranslatableMenu.add(gameChessTranslatableMenu);
        //#endregion

        //#region gameChessHotseatJTranslatableMenuItem
        gameChessHotseatJTranslatableMenuItem = new JTranslatableMenuItem(3);
        gameChessHotseatJTranslatableMenuItem.addActionListener(myActionListener);
        gameChessTranslatableMenu.add(gameChessHotseatJTranslatableMenuItem);
        //#endregion

        //#region gameChessLanTranslatableMenu
        gameChessLanTranslatableMenu = new JTranslatableMenu(4);
        gameChessTranslatableMenu.add(gameChessLanTranslatableMenu);
        //#endregion

        //#region gameChessLanCreateGameTranslatableMenuItem
        gameChessLanCreateGameTranslatableMenuItem = new JTranslatableMenuItem(24);
        gameChessLanCreateGameTranslatableMenuItem.addActionListener(myActionListener);
        gameChessLanTranslatableMenu.add(gameChessLanCreateGameTranslatableMenuItem);
        //#endregion
    
        //#region gameChessLanJoinGameTranslatableMenuItem
        gameChessLanJoinGameTranslatableMenuItem = new JTranslatableMenuItem(25);
        gameChessLanJoinGameTranslatableMenuItem.addActionListener(myActionListener);
        gameChessLanTranslatableMenu.add(gameChessLanJoinGameTranslatableMenuItem);
        //#endregion

        //#region gameChessSeparator
        gameChessSeparator = new JSeparator();
        gameChessTranslatableMenu.add(gameChessSeparator);
        //#endregion

        //#region gameChessOpenRulesTranslatableMenuItem
        gameChessOpenRulesTranslatableMenuItem = new JTranslatableMenuItem(21);
        gameChessOpenRulesTranslatableMenuItem.addActionListener(myActionListener);
        gameChessTranslatableMenu.add(gameChessOpenRulesTranslatableMenuItem);
        //#endregion
    }

    private void translate()
    {
        GlobalMain.sRegion.transltateComponentsInContainer(this);
    }

    private class MyActionListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            if (e.getSource() == optionsSettingsTranslatableMenuItem)
            {
                SettingsInternalFrame frame = new SettingsInternalFrame();
                GlobalMain.mdiPane.add(frame);
                frame.setVisible(true);
            }

            if (e.getSource() == gameChessHotseatJTranslatableMenuItem)
            {
                ChessHotSeatSettingsInternalFrame frame = new ChessHotSeatSettingsInternalFrame();
                GlobalMain.mdiPane.add(frame);
                frame.setVisible(true);
            }

            if (e.getSource() == gameChessOpenRulesTranslatableMenuItem)
            {
                UFiles.openHtmlFileWithDefaultBrowser(GameEnum.CHESS.getRulePath(true));
            }

            if (e.getSource() == gameChessLanCreateGameTranslatableMenuItem)
            {
                ChessLanServerSettingsInternalFrame frame = new ChessLanServerSettingsInternalFrame();
                GlobalMain.mdiPane.add(frame);
                frame.setVisible(true);
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

    private class MyWindowListener implements WindowListener
    {

        @Override
        public void windowOpened(WindowEvent e) 
        {
        }

        @Override
        public void windowClosing(WindowEvent e) 
        {
            UDialogs.DialogResultEnum res = UDialogs.showConfirmDialogTranslated(13, 36, DialogTypeEnum.YesNo, IconTypeEnum.Question);

            if (res == DialogResultEnum.YesOrOk)
            {
                setDefaultCloseOperation(MainFrame.EXIT_ON_CLOSE);
            }
        }

        @Override
        public void windowClosed(WindowEvent e) 
        {
        }

        @Override
        public void windowIconified(WindowEvent e) 
        {
        }

        @Override
        public void windowDeiconified(WindowEvent e) 
        {
        }

        @Override
        public void windowActivated(WindowEvent e) 
        {
        }

        @Override
        public void windowDeactivated(WindowEvent e) 
        {
        }
        
    }
}
