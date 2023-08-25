package Classes.Frames.Dialogs.Chess;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import Classes.Global.GlobalMain;
import Classes.Objects.CustomComponents.*;
import Classes.Utils.UImages;
import Interfaces.Chess.*;
import Interfaces.Chess.IChessPiece.*;

public class PawnPromotionDialog extends JDialog
{
    private enum SelectablePiecesEnum
    {
        NONE(null),
        QUEEN(KindEnum.QUEEN),
        ROOK(KindEnum.ROOK),
        BISHOP(KindEnum.BISHOP),
        KNIGHT(KindEnum.KNIGHT);

        IChessPiece.KindEnum pieceKind;

        SelectablePiecesEnum(IChessPiece.KindEnum pieceKind)
        {
            this.pieceKind = pieceKind;
        }

        IChessPiece.KindEnum getOfficialKind()
        {
            return pieceKind;
        }
    }

    JLabel
        queenSelectedLed,
        rookSelectedLed,
        knightSelectedLed,
        bishobSelectedLed;
    JButton
        selectQueenJButton,
        selectRookJButton,
        selectKnightJButton,
        selectBishopJButton;
    JTranslatableButton
        confirmJTranslatableButton;
    String
        preColor = "";
    SelectablePiecesEnum
        selectedPiece = SelectablePiecesEnum.NONE;
    MyActionListener
        myActionListener = new MyActionListener();

    public PawnPromotionDialog(IChessPiece.ColorEnum piecesColor)
    {
        preColor = piecesColor == ColorEnum.WHITE ? "White" : "Black";

        initializeComponents();

        GlobalMain.sRegion.transltateComponentsInContainer(this);
    }

    public IChessPiece.KindEnum getDialogResult()
    {
        return selectedPiece.getOfficialKind();
    }

    private void initializeComponents()
    {
        //#region this
        getContentPane().setPreferredSize(new Dimension(480, 150));
        setModal(true);
        setUndecorated(true);
        setLayout(null);
        //#endregion

        //#region queenSelectedLed
        queenSelectedLed = new JLabel();
        queenSelectedLed.setSize(120, 120);
        queenSelectedLed.setLocation(0, 0);
        queenSelectedLed.setLayout(null);
        queenSelectedLed.setBackground(Color.GREEN);
        queenSelectedLed.setOpaque(false);
        add(queenSelectedLed);
        //#endregion

        //#region selectQueenJButton
        selectQueenJButton = new JButton();
        selectQueenJButton.setSize(100, 100);
        selectQueenJButton.setLocation(10, 10);
        selectQueenJButton.setIcon(getPiecesImage("Queen"));
        selectQueenJButton.addActionListener(myActionListener);
        queenSelectedLed.add(selectQueenJButton);
        //#endregion

        //#region rookSelectedLed
        rookSelectedLed = new JLabel();
        rookSelectedLed.setSize(120, 120);
        rookSelectedLed.setLocation(120, 0);
        rookSelectedLed.setLayout(null);
        rookSelectedLed.setBackground(Color.GREEN);
        rookSelectedLed.setOpaque(false);
        add(rookSelectedLed);
        //#endregion

        //#region selectRookJButton
        selectRookJButton = new JButton();
        selectRookJButton.setSize(100, 100);
        selectRookJButton.setLocation(10, 10);
        selectRookJButton.setIcon(getPiecesImage("Rook"));
        selectRookJButton.addActionListener(myActionListener);
        rookSelectedLed.add(selectRookJButton);
        //#endregion
    
        //#region knightSelectedLed
        knightSelectedLed = new JLabel();
        knightSelectedLed.setSize(120, 120);
        knightSelectedLed.setLocation(240, 0);
        knightSelectedLed.setLayout(null);
        knightSelectedLed.setBackground(Color.GREEN);
        knightSelectedLed.setOpaque(false);
        add(knightSelectedLed);
        //#endregion

        //#region selectKnightJButton
        selectKnightJButton = new JButton();
        selectKnightJButton.setSize(100, 100);
        selectKnightJButton.setLocation(10, 10);
        selectKnightJButton.setIcon(getPiecesImage("Knight"));
        selectKnightJButton.addActionListener(myActionListener);
        knightSelectedLed.add(selectKnightJButton);
        //#endregion
    
        //#region bishobSelectedLed
        bishobSelectedLed = new JLabel();
        bishobSelectedLed.setSize(120, 120);
        bishobSelectedLed.setLocation(360, 0);
        bishobSelectedLed.setLayout(null);
        bishobSelectedLed.setBackground(Color.GREEN);
        bishobSelectedLed.setOpaque(false);
        add(bishobSelectedLed);
        //#endregion

        //#region selectBishopJButton
        selectBishopJButton = new JButton();
        selectBishopJButton.setSize(100, 100);
        selectBishopJButton.setLocation(10, 10);
        selectBishopJButton.setIcon(getPiecesImage("Bishop"));
        selectBishopJButton.addActionListener(myActionListener);
        bishobSelectedLed.add(selectBishopJButton);
        //#endregion
    
        //#region confirmJTranslatableButton
        confirmJTranslatableButton = new JTranslatableButton(17);
        confirmJTranslatableButton.setSize(480, 30);
        confirmJTranslatableButton.setLocation(0, 120);
        confirmJTranslatableButton.setEnabled(selectedPiece != SelectablePiecesEnum.NONE);
        confirmJTranslatableButton.addActionListener(myActionListener);
        add(confirmJTranslatableButton);
        //#endregion

        pack();
        setLocationRelativeTo(null);
    }

    private ImageIcon getPiecesImage(String piece)
    {
        String path = String.format("Data/Chess/Assets/Pictures/ChessPieces/%s%s.png", preColor, piece);

        return UImages.getImageIconResized(path, 100, 100);
    }

    private void setSelectedPiece(SelectablePiecesEnum piece)
    {
        if (piece != selectedPiece)
        {
            selectedPiece = piece;
            showSelectedPiece(selectedPiece);
            confirmJTranslatableButton.setEnabled(selectedPiece.getOfficialKind() != null);
        }
    }

    private void resetOpaque()
    {
        queenSelectedLed.setOpaque(false);
        rookSelectedLed.setOpaque(false);
        knightSelectedLed.setOpaque(false);
        bishobSelectedLed.setOpaque(false);
    }

    private void showSelectedPiece(SelectablePiecesEnum piece)
    {
        resetOpaque();

        switch (piece)
        {
            case BISHOP:
                bishobSelectedLed.setOpaque(true);
                break;
            case KNIGHT:
                knightSelectedLed.setOpaque(true);
                break;
            case NONE:
                break;
            case QUEEN:
                queenSelectedLed.setOpaque(true);
                break;
            case ROOK:
                rookSelectedLed.setOpaque(true);
                break;
            default:
                break;
            
        }

        repaint();
    }

    private class MyActionListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e) 
        {
            if (e.getSource() == selectQueenJButton)
            {
                setSelectedPiece(SelectablePiecesEnum.QUEEN);
            }

            if (e.getSource() == selectRookJButton)
            {
                setSelectedPiece(SelectablePiecesEnum.ROOK);
            }

            if (e.getSource() == selectKnightJButton)
            {
                setSelectedPiece(SelectablePiecesEnum.KNIGHT);
            }

            if (e.getSource() == selectBishopJButton)
            {
                setSelectedPiece(SelectablePiecesEnum.BISHOP);
            }

            if (e.getSource() == confirmJTranslatableButton)
            {
                dispose();
            }
        }
        
    }
}
