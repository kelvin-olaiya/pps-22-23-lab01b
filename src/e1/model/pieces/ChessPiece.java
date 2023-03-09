package e1.model.pieces;

import e1.model.Position;

@FunctionalInterface
public interface ChessPiece {
    boolean canMoveToPositionFrom(Position to, Position from);
}
