package Classes.Objects.Chess.Pieces;

import java.util.*;
import Classes.Objects.*;
import Classes.Objects.Chess.*;
import Classes.Utils.*;
import Interfaces.Chess.*;
import Structs.Chess.*;
import Structs.Chess.AvailableMove.*;

public class King extends ChessPiece
{

    public King(ColorEnum color, GridPosition startingPosition, ChessBoard chessBoard) 
    {
        super(color, IChessPiece.KindEnum.KING, startingPosition, chessBoard);
    }

    @Override
    public ArrayList<AvailableMove> getAvailableMovesList(boolean filter)
    {
        ArrayList<AvailableMove> moves = new ArrayList<>(0);

        GridPosition[] availablePositions = new GridPosition[]
        {
            new GridPosition(getGridPosition().getRow() - 1, getGridPosition().getColumn() - 1),
            new GridPosition(getGridPosition().getRow() - 1, getGridPosition().getColumn()),
            new GridPosition(getGridPosition().getRow() - 1, getGridPosition().getColumn() + 1),
            new GridPosition(getGridPosition().getRow(), getGridPosition().getColumn() + 1),
            new GridPosition(getGridPosition().getRow() + 1, getGridPosition().getColumn() + 1),
            new GridPosition(getGridPosition().getRow() + 1, getGridPosition().getColumn()),
            new GridPosition(getGridPosition().getRow() + 1, getGridPosition().getColumn() - 1),
            new GridPosition(getGridPosition().getRow(), getGridPosition().getColumn() - 1),
        };

        for (GridPosition gp : availablePositions)
        {
            if (ChessBoard.isInGrid(gp))
            {
                ChessPiece pieceInPosition = getChessBoard().getPieceInPosition(gp);

                if (pieceInPosition == null)
                {
                    moves.add(new AvailableMove(gp, null, SpecialMoveEnum.NONE));
                }
                else if (pieceInPosition.getColor() != getColor())
                {
                    moves.add(new AvailableMove(gp, pieceInPosition, SpecialMoveEnum.NONE));
                }
            }
        }

        //Castling
        if (getLastTurnMovement() == 0 && filter)
        {
            ChessPiece[] rooks = getChessBoard().getPieces(IChessPiece.KindEnum.ROOK, getColor());

            for (ChessPiece r : rooks)
            {
                if (r.getLastTurnMovement() == 0)
                {
                    //Check that all cells are free
                    boolean availableForCastling = true;
                    int[] cols = UUtils.getRangeValues(r.getGridPosition().getColumn(), getGridPosition().getColumn());

                    for (int i=1; i<cols.length - 1; i++)
                    {
                        GridPosition positionToFind = new GridPosition(getGridPosition().getRow(), cols[i]);

                        if (getChessBoard().getPieceInPosition(positionToFind) != null)
                        {
                            availableForCastling = false;
                            break;
                        }
                    }
                    
                    if (availableForCastling)
                    {
                        for (int i=1; i<=3; i++)
                        {
                            GridPosition gridNextMove = new GridPosition(getGridPosition().getRow(), cols[i]);
                            AvailableMove nextMove = 
                                new AvailableMove(gridNextMove, null, AvailableMove.SpecialMoveEnum.NONE);

                            if (getChessBoard().isThatMoveCauseAOwnCheck(this, nextMove))
                            {
                                availableForCastling = false;
                                break;
                            }
                        }
                    }

                    if (availableForCastling)
                    {
                        boolean longCastling = r.getGridPosition().getColumn() == 0;
                        int castlingCol = longCastling ? 2 : 6;
                        GridPosition castlingPosition = new GridPosition(getGridPosition().getRow(), castlingCol);

                        AvailableMove castlingMove = 
                            new AvailableMove(castlingPosition, null, SpecialMoveEnum.CASTLING, r, longCastling);

                        moves.add(castlingMove);
                    }
                }
            }
        }

        if (filter) filterMovesThatCauseOwnCheck(moves);

        return moves;
    }
    
}
