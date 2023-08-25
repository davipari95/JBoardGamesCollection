package Classes.Objects.Chess.Pieces;

import java.util.*;

import Classes.Objects.*;
import Classes.Objects.Chess.*;
import Interfaces.Chess.*;
import Structs.Chess.*;
import Structs.Chess.AvailableMove.*;

public class Pawn extends ChessPiece
{

    public Pawn(ColorEnum color, GridPosition startingPosition, ChessBoard chessBoard) 
    {
        super(color, IChessPiece.KindEnum.PAWN, startingPosition, chessBoard);
    }

    @Override
    public ArrayList<AvailableMove> getAvailableMovesList(boolean filter)
    {
        ArrayList<AvailableMove> moves = new ArrayList<>(0);

        int movementDirection = getColor() == IChessPiece.ColorEnum.WHITE ? -1 : 1;

        //Standard movement
        GridPosition nextPosition = 
            new GridPosition(getGridPosition().getRow() + movementDirection, getGridPosition().getColumn());

        if (ChessBoard.isInGrid(nextPosition) && getChessBoard().getPieceInPosition(nextPosition) == null)
        {
            SpecialMoveEnum special = 
                (getColor() == IChessPiece.ColorEnum.WHITE && nextPosition.getRow() == 0) || 
                (getColor() == IChessPiece.ColorEnum.BLACK && nextPosition.getRow() == 7) ? 
                SpecialMoveEnum.PROMOTE : SpecialMoveEnum.NONE;

            moves.add(new AvailableMove(nextPosition, null, special));
        }

        //If is the first movement, and the previous cell is free, we can try to check if 2-steps movement is available
        if (getMovementCounter() == 0 && moves.size() == 1)
        {
            nextPosition = new GridPosition(nextPosition.getRow() + movementDirection, nextPosition.getColumn());

            if (ChessBoard.isInGrid(nextPosition) && getChessBoard().getPieceInPosition(nextPosition) == null)
            {
                moves.add(new AvailableMove(nextPosition, null, SpecialMoveEnum.NONE));
            }
        }

        //Diagonals and en-passant
        int[] lrs = {-1, 1};
        
        for (int lr : lrs)
        {
            nextPosition = 
                new GridPosition(getGridPosition().getRow() + movementDirection, getGridPosition().getColumn() + lr);
            
            if (!ChessBoard.isInGrid(nextPosition))
            {
                continue;
            }
            
            //Diagonal
            ChessPiece p = getChessBoard().getPieceInPosition(nextPosition);
            if (p != null && p.getColor() != getColor())
            {
                moves.add(new AvailableMove(nextPosition, p, SpecialMoveEnum.NONE));
                continue;
            }

            //En-passant
            GridPosition sidePosition = 
                new GridPosition(getGridPosition().getRow(), getGridPosition().getColumn() + lr);
            p = getChessBoard().getPieceInPosition(sidePosition);
            if (isAvailableForEnPassant(p))
            {
                moves.add(new AvailableMove(nextPosition, p, SpecialMoveEnum.EN_PASSANT));
            }
        }

        if (filter) filterMovesThatCauseOwnCheck(moves);

        return moves;
    }

    public int enPassantRow()
    {
        return getColor() == IChessPiece.ColorEnum.WHITE ? 4 : 3;
    }
    
    private boolean isAvailableForEnPassant(ChessPiece eatablePiece)
    {
        return
            eatablePiece != null && 
            eatablePiece.getKind() == IChessPiece.KindEnum.PAWN &&
            ((Pawn)eatablePiece).enPassantRow() == getGridPosition().getRow() &&
            eatablePiece.getColor() != getColor() && 
            eatablePiece.getMovementCounter() == 1 && 
            eatablePiece.getLastTurnMovement() == getChessBoard().getGame().getActualTurnNumber() - 1;
    }
    
}
