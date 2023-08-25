package Classes.Objects.Chess.Pieces;

import java.util.*;
import Classes.Objects.*;
import Classes.Objects.Chess.*;
import Interfaces.Chess.*;
import Structs.Chess.*;

public class Bishop extends ChessPiece
{

    public Bishop(ColorEnum color, GridPosition startingPosition, ChessBoard chessBoard) 
    {
        super(color, IChessPiece.KindEnum.BISHOP, startingPosition, chessBoard);
    }

    @Override
    public ArrayList<AvailableMove> getAvailableMovesList(boolean filter)
    {
        ArrayList<AvailableMove> availableMoves = new ArrayList<>(0);

        String[] movements = new String[] {"TL", "TR", "BR", "BL"};

        for (String movement : movements)
        {
            availableMoves.addAll(getStraightAvailableMoves(movement));
        }

        if (filter) filterMovesThatCauseOwnCheck(availableMoves);

        return availableMoves;
    }
    
}
