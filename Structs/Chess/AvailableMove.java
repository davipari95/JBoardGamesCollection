package Structs.Chess;

import Classes.Objects.*;
import Classes.Objects.Chess.*;

public class AvailableMove 
{
    public enum SpecialMoveEnum
    {
        NONE,
        EN_PASSANT,
        CASTLING,
        PROMOTE,
    }

    GridPosition gridPosition;
    ChessPiece eatedPiece;
    SpecialMoveEnum specialMove;
    Object[] tags;

    public AvailableMove(GridPosition gridPosition, ChessPiece eatedPiece, SpecialMoveEnum specialMove, Object... tags)
    {
        this.gridPosition = gridPosition;
        this.eatedPiece = eatedPiece;
        this.specialMove = specialMove;
        this.tags = tags;
    }

    public GridPosition getGridPosition()
    {
        return gridPosition;
    }

    public ChessPiece getEatedChessPiece()
    {
        return eatedPiece;
    }

    public SpecialMoveEnum getSpecialMove()
    {
        return specialMove;
    }

    public boolean isSamePosition(GridPosition position)
    {
        return position.equals(gridPosition);
    }

    public boolean isSamePosition(AvailableMove move)
    {
        return isSamePosition(move.getGridPosition());
    }

    public Object[] getTags()
    {
        return tags;
    }

    @Override
    public boolean equals(Object availableMoveObject)
    {
        if (availableMoveObject == this)
        {
            return true;
        }

        if (!(availableMoveObject instanceof AvailableMove))
        {
            return false;
        }

        AvailableMove availableMove = (AvailableMove)availableMoveObject;

        return
            getGridPosition().equals(availableMove.getGridPosition()) &&
            getEatedChessPiece().equals(availableMove.getEatedChessPiece()) &&
            getSpecialMove().equals(availableMove.getSpecialMove());
    }

    public static AvailableMove getWhereGridPosition(AvailableMove[] collection, GridPosition position)
    {
        for (AvailableMove move : collection)
        {
            if (move.isSamePosition(position))
            {
                return move;
            }
        }

        return null;
    }
}
