package Classes.Objects.Chess.Pieces;

import java.util.*;
import Classes.Objects.*;
import Classes.Objects.Chess.*;
import Interfaces.Chess.*;
import Structs.Chess.*;

public class Queen extends ChessPiece
{

    public Queen(ColorEnum color, GridPosition startingPosition, ChessBoard chessBoard) 
    {
        super(color, IChessPiece.KindEnum.QUEEN, startingPosition, chessBoard);
    }

    @Override
    public ArrayList<AvailableMove> getAvailableMovesList(boolean filter)
    {
        ArrayList<AvailableMove> moves = new ArrayList<>(0);

        String[] straightDirections = new String[]
        {
            "TL",
            "T",
            "TR",
            "R",
            "BR",
            "B",
            "BL",
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
