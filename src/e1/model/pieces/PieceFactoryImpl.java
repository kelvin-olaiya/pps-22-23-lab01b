package e1.model.pieces;

public class PieceFactoryImpl implements PieceFactory {
    @Override
    public ChessPiece newStaticPawn() {
        return new StaticPawn();
    }

    @Override
    public ChessPiece newKnight() {
        return new Knight();
    }
}
