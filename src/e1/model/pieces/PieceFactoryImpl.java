package e1.model.pieces;

import e1.model.Position;

public class PieceFactoryImpl implements PieceFactory {
    @Override
    public ChessPiece newStaticPawn() {
        return new ChessPiece() {
            @Override
            public boolean canMoveToPositionFrom(Position to, Position from) {
                return false;
            }
        };
    }

    @Override
    public ChessPiece newKnight() {
        return new ChessPiece() {
            private static final int KNIGHT_MAX_MOVE = 2;
            private static final int KNIGHT_MIN_MOVE = 1;

            @Override
            public boolean canMoveToPositionFrom(Position to, Position from) {
                int x = Math.abs(to.getX() - from.getX());
                int y = Math.abs(to.getY() - from.getY());
                return Math.min(x, y) != 0 && x + y == KNIGHT_MAX_MOVE + KNIGHT_MIN_MOVE;
            }
        };
    }
}
