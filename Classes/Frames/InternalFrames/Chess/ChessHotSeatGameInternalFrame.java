package Classes.Frames.InternalFrames.Chess;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import Classes.Frames.Dialogs.Chess.*;
import Classes.Global.*;
import Classes.Global.Subs.Region.*;
import Classes.Objects.*;
import Classes.Objects.Chess.*;
import Classes.Objects.Chess.ChessBoard.*;
import Classes.Objects.Chess.Game.*;
import Classes.Objects.Chess.Games.*;
import Classes.Objects.CustomComponents.*;
import Classes.Utils.*;
import Classes.Utils.UDialogs.*;
import Interfaces.Chess.*;
import Interfaces.Chess.IChessPiece.*;
import Structs.Chess.*;

public class ChessHotSeatGameInternalFrame extends JInternalFrame
{
    JLabel
        chessBoardBackgroundLabel;
    JTranslatableLabel
        infoLabel;
    JGridLabel[][]
        chessboardGridLabel;
    GridListener
        gridListener = new GridListener();
    HotSeatGame
        game;
    MyGameListener
        myGameListener = new MyGameListener();
    MyChessBoardActionListener
        myChessBoardActionListener = new MyChessBoardActionListener();
    MyInternalFrameListener
        myInternalFrameListener = new MyInternalFrameListener();
    MyRegionListener
        myRegionListener = new MyRegionListener();
    final int
        CHESSBOARD_SIZE = 800,
        GRID_SIZE = CHESSBOARD_SIZE / 8,
        GRID_PADDING = 5,
        INFO_LABEL_HEIGHT = 30;

    public ChessHotSeatGameInternalFrame(String whitePlayerName, String blacksPlayerName)
    {
        super("", false, true, false, true);

        game = new HotSeatGame(whitePlayerName, blacksPlayerName);
        game.addGameActionListener(myGameListener);
        game.getChessBoard().addChessBoardActionListener(myChessBoardActionListener);
        GlobalMain.sRegion.addRegionListener(myRegionListener);

        initializeComponents();

        updateGrid();

        translate();
    }

    private void initializeComponents()
    {
        //#region this
        getContentPane().setPreferredSize(new Dimension(CHESSBOARD_SIZE, CHESSBOARD_SIZE + INFO_LABEL_HEIGHT));
        setTitle(GlobalMain.sRegion.getTranslatedText(12));
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setLayout(null);
        addInternalFrameListener(myInternalFrameListener);
        //#endregion

        //#region chessBoardLabel
        chessBoardBackgroundLabel = new JLabel();
        chessBoardBackgroundLabel.setBounds(0, 0, CHESSBOARD_SIZE, CHESSBOARD_SIZE);
        chessBoardBackgroundLabel.setIcon(new ImageIcon("Data/Chess/Assets/Pictures/ChessBoard.png"));
        chessBoardBackgroundLabel.setLayout(null);
        add(chessBoardBackgroundLabel);
        //#endregion

        //#region chessboardGridLabel
        chessboardGridLabel = new JGridLabel[8][8];
        for (int i=0; i<chessboardGridLabel.length; i++)
        {
            for (int j=0; j<chessboardGridLabel[i].length; j++)
            {
                chessboardGridLabel[i][j] = new JGridLabel(i, j);
                chessboardGridLabel[i][j].setSize(GRID_SIZE - (2 * GRID_PADDING), GRID_SIZE - (2 * GRID_PADDING));
                chessboardGridLabel[i][j].setLocation((j * GRID_SIZE) + GRID_PADDING, (i * GRID_SIZE) + GRID_PADDING);
                chessboardGridLabel[i][j].addMouseListener(gridListener);
                chessBoardBackgroundLabel.add(chessboardGridLabel[i][j]);
            }
        }
        //#endregion

        //#region infoLabel
        infoLabel = new JTranslatableLabel("");
        infoLabel.setLocation(0, CHESSBOARD_SIZE);
        infoLabel.setSize(CHESSBOARD_SIZE, INFO_LABEL_HEIGHT);
        infoLabel.setVerticalAlignment(JLabel.CENTER);
        infoLabel.setHorizontalAlignment(JLabel.LEFT);
        infoLabel.setLanguageReference(15);
        infoLabel.setArgs(game.getWhitesPlayerName());
        GlobalMain.sRegion.translateITranslatableElement(infoLabel);
        add(infoLabel);
        //#endregion

        pack();
        setLocation(UInternalFrames.retrivePointForCentering(this, GlobalMain.mdiPane));
    }

    private void updateGrid()
    {
        clearGrid();

        for (ChessPiece piece : game.getChessBoard().getChessPieces())
        {
            GridPosition piecePosition = piece.getGridPosition();
            JGridLabel cell = chessboardGridLabel[piecePosition.getRow()][piecePosition.getColumn()];
            Dimension cellSize = cell.getSize();
            cell.setIcon(piece.getIcon(cellSize.width, cellSize.height));
        }
    }

    private void clearGrid()
    {
        for (int i=0; i<chessboardGridLabel.length; i++)
        {
            for (int j=0; j<chessboardGridLabel[i].length; j++)
            {
                chessboardGridLabel[i][j].setIcon(null);
            }
        }
    }

    private void printSelectableCells(AvailableMove[] moves, boolean clear)
    {
        if (clear)
        {
            clearSelectableCells();
        }

        for (int i=0; i<moves.length; i++)
        {
            printCellInPosition(moves[i].getGridPosition(), moves[i].getEatedChessPiece() != null ? Color.RED : Color.YELLOW);
        }
    }

    private void printAvailableMoves(ChessPiece piece)
    {
        clearSelectableCells();

        if (piece != null)
        {
            printCellInPosition(piece.getGridPosition(), Color.GREEN);
            printSelectableCells(piece.getAvailableMoves(), false);
        }
    }

    private void printCellInPosition(GridPosition position, Color color)
    {
        chessboardGridLabel[position.getRow()][position.getColumn()].setBackground(color);
        chessboardGridLabel[position.getRow()][position.getColumn()].setOpaque(true);
        
        repaint();
    }

    private void clearSelectableCells()
    {
        for (int i=0; i<chessboardGridLabel.length; i++)
        {
            for (int j=0; j<chessboardGridLabel[i].length; j++)
            {
                chessboardGridLabel[i][j].setOpaque(false);
            }
        }

        repaint();
    }

    private void restartGame()
    {
        //Removing listeners
        game.getChessBoard().removeChessBoardActionListener(myChessBoardActionListener);
        game.removeGameActionListener(myGameListener);

        //Create a new game
        game = new HotSeatGame(game.getWhitesPlayerName(), game.getWhitesPlayerName());

        //Adding listeners
        game.addGameActionListener(myGameListener);
        game.getChessBoard().addChessBoardActionListener(myChessBoardActionListener);

        //Update grid
        clearSelectableCells();
        updateGrid();

        //Update infolabel
        infoLabel.setLanguageReference(15);
        infoLabel.setArgs(game.getWhitesPlayerName());
        GlobalMain.sRegion.translateITranslatableElement(infoLabel);
    }

    private void translate()
    {
        setTitle(GlobalMain.sRegion.getTranslatedText(12));

        GlobalMain.sRegion.transltateComponentsInContainer(this);
    }

    private class GridListener implements MouseListener
    {

        @Override
        public void mouseClicked(MouseEvent e) 
        {
        }

        @Override
        public void mousePressed(MouseEvent e) 
        {
        }

        @Override
        public void mouseReleased(MouseEvent e) 
        {
            if (game.getGameOver())
            {
                return;
            }

            JGridLabel clickedLabel = (JGridLabel) e.getSource();
            GridPosition clickedPosition = clickedLabel.getGridPosition();
            ChessPiece clickedPiece = game.getChessBoard().getPieceInPosition(clickedPosition);
            ChessPiece selectedPiece = game.getSelectedChessPiece();

            if (selectedPiece == null)
            {
                if (clickedPiece != null)
                {
                    if (clickedPiece.getColor() == game.getActualTurn())
                    {
                        game.setSelectedChessPiece(clickedPiece);
                    }
                }
            }
            else 
            {
                if (clickedPiece == null)
                {
                    if (selectedPiece.haveAsAvailableMove(clickedPosition, true))
                    {
                        selectedPiece.movePiece(clickedPosition);
                    }
                    else 
                    {
                        game.setSelectedChessPiece(null);
                    }
                }
                else if (clickedPiece.equals(selectedPiece))
                {
                    game.setSelectedChessPiece(null);
                }
                else if (clickedPiece.getColor() == game.getActualTurn())
                {
                    game.setSelectedChessPiece(clickedPiece);
                }
                else
                {
                    if (selectedPiece.haveAsAvailableMove(clickedPosition, true))
                    {
                        selectedPiece.movePiece(clickedPosition);
                    }
                }
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) 
        {
        }

        @Override
        public void mouseExited(MouseEvent e) 
        {
        }
        
    }

    private class MyGameListener implements GameActionListener
    {

        @Override
        public void turnChangedActionEvent(Game sender, ColorEnum actualTurn, int actualTurnNr) 
        {
            if (sender.getChessBoard().isCheck(actualTurn))
            {
                if (sender.getChessBoard().isCheckMate(actualTurn))
                {
                    IChessPiece.ColorEnum winnerColor = IChessPiece.getOppositeColor(actualTurn);
                    String winnerName = game.getPlayerName(winnerColor);
                    CheckmateWinnerDialog diag = new CheckmateWinnerDialog(winnerName, winnerColor);
                    diag.setVisible(true);
                    CheckmateWinnerDialog.DialogResultEnum result = diag.getDialogResult();
                    if (result == CheckmateWinnerDialog.DialogResultEnum.GAME_OVER)
                    {
                        game.setGameOver(true);
                    }
                    else 
                    {
                        restartGame();
                    }
                }
                else
                {
                    infoLabel.setLanguageReference(16);
                    infoLabel.setArgs(sender.getPlayerName(actualTurn));
                    GlobalMain.sRegion.translateITranslatableElement(infoLabel);
                }
            }
            else
            {
                infoLabel.setLanguageReference(15);
                infoLabel.setArgs(sender.getPlayerName(actualTurn));
                GlobalMain.sRegion.translateITranslatableElement(infoLabel);
            }
        }

        @Override
        public void selectedChessPieceChangedActionEvent(Game sender, ChessPiece selectedPiece) 
        {
            ChessHotSeatGameInternalFrame.this.printAvailableMoves(selectedPiece);
        }

        @Override
        public void gameOverChangedActionEvent(Game sender, boolean value) 
        {
            if (value)
            {
                infoLabel.setLanguageReference(20);
                infoLabel.setArgs();
                GlobalMain.sRegion.translateITranslatableElement(infoLabel);
            }
        }

    }

    private class MyChessBoardActionListener implements ChessBoardActionListener
    {

        @Override
        public void pieceMovedActionEvent(ChessBoard sender, ChessPiece piece, GridPosition newGridPosition) 
        {
            updateGrid();
            clearSelectableCells();
        }
    
    }

    private class MyInternalFrameListener implements InternalFrameListener
    {

        @Override
        public void internalFrameOpened(InternalFrameEvent e) 
        {
        }

        @Override
        public void internalFrameClosing(InternalFrameEvent e) 
        {
            UDialogs.DialogResultEnum res = UDialogs.showConfirmDialogTranslated(13, 14, DialogTypeEnum.YesNo, IconTypeEnum.Question);

            if (res == UDialogs.DialogResultEnum.YesOrOk)
            {
                GlobalMain.sRegion.removeRegionListener(myRegionListener);
                ChessHotSeatGameInternalFrame.this.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
            }
        }

        @Override
        public void internalFrameClosed(InternalFrameEvent e) 
        {
        }

        @Override
        public void internalFrameIconified(InternalFrameEvent e) 
        {
        }

        @Override
        public void internalFrameDeiconified(InternalFrameEvent e) 
        {
        }

        @Override
        public void internalFrameActivated(InternalFrameEvent e) 
        {
        }

        @Override
        public void internalFrameDeactivated(InternalFrameEvent e) 
        {
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
