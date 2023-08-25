package Classes.Objects.Chess;

import java.util.*;
import javax.swing.*;
import Classes.Frames.Dialogs.Chess.*;
import Classes.Global.GlobalMain;
import Classes.Objects.*;
import Classes.Objects.Chess.Pieces.*;
import Classes.Utils.*;
import Interfaces.*;
import Interfaces.Chess.*;
import Structs.Chess.*;
import Structs.Chess.AvailableMove.*;

public class ChessPiece implements IChessPiece, IGridPositionable, ICopyable<ChessPiece>
{

    IChessPiece.ColorEnum 
        color;
    IChessPiece.KindEnum 
        kind;
    GridPosition 
        actualPosition;
    ChessBoard 
        chessBoard;
    int 
        lastTurnMovement,
        movementCounter;
    ArrayList<ChessPieceActionListener>
        chessPieceActionListeners = new ArrayList<>(0);

    public ChessPiece(IChessPiece.ColorEnum color, IChessPiece.KindEnum kind, GridPosition startingPosition, ChessBoard chessBoard)
    {
        this.color = color;
        this.kind = kind;
        actualPosition = startingPosition;
        this.chessBoard = chessBoard;
        lastTurnMovement = 0;
        movementCounter = 0;
    }

    public ImageIcon getIcon()
    {
        String imagePath = String.format("Data/Chess/Assets/Pictures/ChessPieces/%s%s.png", color.getName(), kind.getName());
        return new ImageIcon(imagePath);
    }

    public ImageIcon getIcon(int width, int height)
    {
        return UImages.resizeImageIcon(getIcon(), width, height);
    }

    public AvailableMove[] getAvailableMoves()
    {
        return getAvailableMovesList().toArray(new AvailableMove[0]);
    }

    public AvailableMove[] getAvailableMoves(boolean filter)
    {
        return getAvailableMovesList(filter).toArray(new AvailableMove[0]);
    }

    public ArrayList<AvailableMove> getAvailableMovesList()
    {
        return getAvailableMovesList(true);
    }

    public ArrayList<AvailableMove> getAvailableMovesList(boolean filter)
    {
        throw new RuntimeException("Function is not implemented");
    }

    public int getLastTurnMovement()
    {
        return lastTurnMovement;
    }

    public ChessBoard getChessBoard()
    {
        return chessBoard;
    }

    public int getMovementCounter()
    {
        return movementCounter;
    }

    protected ArrayList<AvailableMove> getStraightAvailableMoves(String direction)
    {
        ArrayList<AvailableMove> availableMoves = new ArrayList<>(0);

        int hMovement = direction.contains("T") ? -1 : direction.contains("B") ? 1 : 0;
        int vMovement = direction.contains("L") ? -1 : direction.contains("R") ? 1 : 0;

        for 
        (
            int i = getGridPosition().getRow() + vMovement, j = getGridPosition().getColumn() + hMovement; 
            UMathemathic.isBetween(i, 0, 7) && UMathemathic.isBetween(j, 0, 7); 
            i += vMovement, j += hMovement
        )
        {
            ChessPiece p = chessBoard.getPieceInPosition(new GridPosition(i, j));

            if (p == null)
            {
                availableMoves.add(new AvailableMove(new GridPosition(i, j), null, SpecialMoveEnum.NONE));
            }
            else 
            {
                if (p.getColor() != getColor())
                {
                    availableMoves.add(new AvailableMove(new GridPosition(i, j), p, SpecialMoveEnum.NONE));
                }

                break;
            }
        }

        return availableMoves;
    }

    @Override
    public ColorEnum getColor() 
    {
        return color;
    }

    @Override
    public KindEnum getKind() 
    {
        return kind;
    }

    @Override
    public GridPosition getGridPosition() 
    {
        return actualPosition;
    }

    @Override
    public void movePiece(GridPosition newPosition) 
    {
        if (!actualPosition.equals(newPosition))
        {
            AvailableMove move = AvailableMove.getWhereGridPosition(getAvailableMoves(), newPosition);

            if (move != null)
            {
                switch (move.getSpecialMove())
                {
                    case CASTLING:
                        Rook rook = (Rook)move.getTags()[0];
                        boolean longCastling = (boolean)move.getTags()[1];
                        rook.getGridPosition().setPosition(rook.getGridPosition().getRow(), longCastling ? 3 : 5);
                        break;
                    case EN_PASSANT:
                    case NONE:
                        if (move.getEatedChessPiece() != null)
                        {
                            getChessBoard().eatPiece(this, move.getEatedChessPiece());
                        }
                        break;
                    case PROMOTE:
                        PawnPromotionDialog promotionDialog = new PawnPromotionDialog(getColor());
                        GlobalMain.mdiPane.add(promotionDialog);
                        promotionDialog.setVisible(true);
                        IChessPiece.KindEnum promoteTo = promotionDialog.getDialogResult();
                        getChessBoard().promotePawn((Pawn)this, promoteTo, move);
                        break;
                    default:
                        break;
                }
            }

            actualPosition = newPosition;

            lastTurnMovement = getChessBoard().getGame().actualTurnNumber;
            movementCounter++;

            invokeChessPieceMovedActionEvent(this, newPosition);
        }
    }

    @Override
    public boolean equals(Object o)
    {
        if (o == this)
        {
            return true;
        }

        if (!(o instanceof ChessPiece))
        {
            return false;
        }

        ChessPiece p = (ChessPiece)o;

        return
            getKind() == p.getKind() &&
            getColor() == p.getColor() &&
            getGridPosition().equals(p.getGridPosition());
    }

    public boolean haveAsAvailableMove(GridPosition p, boolean filter)
    {
        AvailableMove[] availableMoves = getAvailableMoves(filter);

        for (AvailableMove m : availableMoves)
        {
            if (m.getGridPosition().equals(p))
            {
                return true;
            }
        }

        return false;
    }

    @Override
    public String toString()
    {
        return String.format("[%s, %s, %s]", getColor().getName(), getKind().getName(), getGridPosition());
    }

    //#region Event handlers

    public void addChessPieceActionListener(ChessPieceActionListener l)
    {
        chessPieceActionListeners.add(l);
    }

    public void removeChessPieceActionListener(ChessPieceActionListener l)
    {
        chessPieceActionListeners.remove(l);
    }

    protected void filterMovesThatCauseOwnCheck(ArrayList<AvailableMove> moves)
    {
        moves.removeIf(m -> getChessBoard().isThatMoveCauseAOwnCheck(this, m));
    }

    private void invokeChessPieceMovedActionEvent(ChessPiece sender, GridPosition newGridPosition)
    {
        for (ChessPieceActionListener l : chessPieceActionListeners)
        {
            l.chessPieceMovedActionEvent(sender, newGridPosition);
        }
    }

    public interface ChessPieceActionListener
    {
        public void chessPieceMovedActionEvent(ChessPiece sender, GridPosition newGridPosition);
    }

    @Override
    public ChessPiece copy() 
    {
        return new ChessPiece(color, kind, actualPosition, chessBoard);
    }
    
    //#endregion
}
