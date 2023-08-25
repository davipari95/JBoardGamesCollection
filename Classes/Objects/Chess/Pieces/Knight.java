package Classes.Objects.Chess.Pieces;

import java.util.*;
import Classes.Objects.*;
import Classes.Objects.Chess.*;
import Interfaces.Chess.*;
import Structs.Chess.*;
import Structs.Chess.AvailableMove.*;

public class Knight extends ChessPiece
{

    public Knight(ColorEnum color, GridPosition startingPosition, ChessBoard chessBoard) 
    {
        super(color, IChessPiece.KindEnum.KNIGHT, startingPosition, chessBoard);
    }

    @Override
    public ArrayList<AvailableMove> getAvailableMovesList(boolean filter)
    {
        ArrayList<AvailableMove> moves = new ArrayList<>(0);

        GridPosition[] availableMoves = new GridPosition[]
        {
            new GridPosition(getGridPosition().getRow() - 2, getGridPosition().getColumn() - 1),
            new GridPosition(getGridPosition().getRow() - 2, getGridPosition().getColumn() + 1),
            new GridPosition(getGridPosition().getRow() - 1, getGridPosition().getColumn() + 2),
            new GridPosition(getGridPosition().getRow() + 1, getGridPosition().getColumn() + 2),
            new GridPosition(getGridPosition().getRow() + 2, getGridPosition().getColumn() + 1),
            new GridPosition(getGridPosition().getRow() + 2, getGridPosition().getColumn() - 1),
            new GridPosition(getGridPosition().getRow() + 1, getGridPosition().getColumn() - 2),
            new GridPosition(getGridPosition().getRow() - 1, getGridPosition().getColumn() - 2),
        };

        for (GridPosition gp : availableMoves)
        {
            if (ChessBoard.isInGrid(gp))
            {
                ChessPiece pieceInSelectedPosition = getChessBoard().getPieceInPosition(gp);

                if (pieceInSelectedPosition == null)
                {
                    moves.add(new AvailableMove(gp, null, SpecialMoveEnum.NONE));
                }
                else if (pieceInSelectedPosition.getColor() != getColor())
                {
                    moves.add(new AvailableMove(gp, pieceInSelectedPosition, SpecialMoveEnum.NONE));
                }
            }
        }

        if (filter) filterMovesThatCauseOwnCheck(moves);

        return moves;
    }
    
}
