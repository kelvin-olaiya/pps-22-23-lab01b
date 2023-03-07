package e1.model.pieces;

import e1.model.Position;

public class Knight implements ChessPiece {
    protected static int MAX_MOVE = 2;
    protected static int MIN_MOVE = 1;

    @Override
    public boolean canMoveToPositionFrom(Position to, Position from) {
        int x = Math.abs(to.getX() - from.getX());
        int y = Math.abs(to.getY() - from.getY());
        return Math.min(x, y) != 0 && x + y == MAX_MOVE + MIN_MOVE;
    }
}
