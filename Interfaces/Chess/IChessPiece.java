package Interfaces.Chess;

import Classes.Objects.GridPosition;

public interface IChessPiece
{
    public enum ColorEnum
    {
        WHITE(0b10000000, "White"),
        BLACK(0b01000000, "Black");

        int value;
        String name;

        ColorEnum(int value, String name)
        {
            this.value = value;
            this.name = name;
        }

        public int getValue() { return value; }

        public String getName() { return name; }
    }

    public enum KindEnum
    {
        KING(0b00000001, "King"),
        QUEEN(0b00000010, "Queen"),
        KNIGHT(0b00000100, "Knight"),
        BISHOP(0b00001000, "Bishop"),
        ROOK(0b00010000, "Rook"),
        PAWN(0b00100000, "Pawn");

        int value;
        String name;

        KindEnum(int value, String name)
        {
            this.value = value;
            this.name = name;
        }

        public int getValue() { return value; }

        public String getName() { return name; }
    }

    ColorEnum getColor();

    KindEnum getKind();

    void movePiece(GridPosition newPosition);

    public static ColorEnum getOppositeColor(ColorEnum color)
    {
        return color == ColorEnum.WHITE ? ColorEnum.BLACK : ColorEnum.WHITE;
    }
}
