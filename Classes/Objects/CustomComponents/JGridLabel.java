package Classes.Objects.CustomComponents;

import javax.swing.*;
import Classes.Objects.*;
import Interfaces.*;

public class JGridLabel extends JLabel implements IGridPositionable
{
    private GridPosition
        gridPosition = new GridPosition(0, 0);

    public JGridLabel(int row, int column)
    {
        gridPosition = new GridPosition(row, column);
    }

    @Override
    public GridPosition getGridPosition() 
    {
        return gridPosition;
    }
}
