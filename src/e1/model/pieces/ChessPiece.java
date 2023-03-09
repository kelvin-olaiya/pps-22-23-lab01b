package e1.model.pieces;

import e1.model.Position;

@FunctionalInterface
public interface ChessPiece {

    /**
     *
     * @param to the destination position.
     * @param from the starting position.
     * @return true if the destination position is reachable from the starting position.
     */
    boolean canMoveToPositionFrom(Position to, Position from);
}
