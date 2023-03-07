package e1.model.pieces;

import e1.model.Position;

public class StaticPawn implements ChessPiece {
    @Override
    public boolean canMoveToPositionFrom(Position to, Position from) {
        return false;
    }
}
