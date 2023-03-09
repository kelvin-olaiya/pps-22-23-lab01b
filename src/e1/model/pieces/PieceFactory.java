package e1.model.pieces;

public interface PieceFactory {

    /**
     *
     * @return a {@link ChessPiece} representing a pawn that cannot move.
     */
    ChessPiece newStaticPawn();

    /**
     *
     * @return a {@link ChessPiece} representing a Knight.
     */
    ChessPiece newKnight();
}
