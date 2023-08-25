package Classes.Objects;

import java.util.*;
import Interfaces.*;

public class GridPosition implements ICopyable<GridPosition>
{
    private int 
        row = 0,
        column = 0;
    private ArrayList<IGridPositionListener> 
        gridPositionListeners = new ArrayList<>(0);

    public GridPosition(int row, int column)
    {
        this.row = row;
        this.column = column;
    }

    public int getRow()
    {
        return row;
    }

    public void setRow(int value)
    {
        if (value != row)
        {
            row = value;
            invokeRowChangedEvent(this, value);
        }
    }

    public int getColumn()
    {
        return column;
    }

    public void setColumn(int value)
    {
        if (value != column)
        {
            column = value;
            invokeColumnChangedEvent(this, value);
        }
    }

    public void setPosition(int row, int column)
    {
        setRow(row);
        setColumn(column);
    }

    public void setPosition(GridPosition p)
    {
        setPosition(p.getRow(), p.getColumn());
    }
    
    @Override
    public String toString()
    {
        return String.format("[%d, %d]", row, column);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == this)
        {
            return true;
        }

        if (!(obj instanceof GridPosition))
        {
            return false;
        }

        GridPosition cmp = (GridPosition)obj;

        return getRow() == cmp.getRow() && getColumn() == cmp.getColumn();
    }

    public GridPosition add(int row, int col)
    {
        return new GridPosition(this.row + row, this.column + col);
    }

    public GridPosition add(GridPosition position)
    {
        return new GridPosition(row + position.getRow(), column + position.getColumn());
    }

    //#region Invoker
    private void invokeRowChangedEvent(Object source, int value)
    {
        for (IGridPositionListener l : gridPositionListeners)
        {
            l.columnChangedEvent(source, value);
        }
    }

    private void invokeColumnChangedEvent(Object source, int value)
    {
        for (IGridPositionListener l : gridPositionListeners)
        {
            l.columnChangedEvent(source, value);
        }
    }
    //#endregion

    private interface IGridPositionListener
    {
        public void rowChangedEvent(Object source, int value);
        public void columnChangedEvent(Object source, int value);
    }

    @Override
    public GridPosition copy() 
    {
        return new GridPosition(row, column);
    }
}
