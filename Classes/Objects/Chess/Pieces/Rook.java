package Classes.Objects.Chess.Pieces;

import java.util.ArrayList;

import Classes.Objects.*;
import Classes.Objects.Chess.*;
import Interfaces.Chess.*;
import Structs.Chess.*;

public class Rook extends ChessPiece 
{

    public Rook(ColorEnum color, GridPosition startingPosition, ChessBoard chessBoard) 
    {
        super(color, IChessPiece.KindEnum.ROOK, startingPosition, chessBoard);
    }

    @Override
    public ArrayList<AvailableMove> getAvailableMovesList(boolean filter)
    {
        ArrayList<AvailableMove> moves = new ArrayList<>(0);

        String[] straightDirections = new String[]
        {
            "T",
            "R",
            "B",
            "L",
        };

        for (String dir : straightDirections)
        {
            moves.addAll(getStraightAvailableMoves(dir));
        }

        if (filter) filterMovesThatCauseOwnCheck(moves);

        return moves;
    }
        
}
