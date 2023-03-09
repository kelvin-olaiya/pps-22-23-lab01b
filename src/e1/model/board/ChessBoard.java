package e1.model.board;

import e1.model.Position;
import e1.model.pieces.ChessPiece;

import java.util.Optional;

public interface ChessBoard {

    /**
     *
     * @return the number of {@link ChessPiece}s added to this board.
     */
    int pieceCount();

    /**
     *
     * @param piece the {@link ChessPiece} of whom the position is retrieved.
     * @return the position of the {@link ChessPiece}.
     * @throws java.util.NoSuchElementException if the piece was not added on this board.
     */
    Position getPiecePosition(ChessPiece piece);

    /**
     * Adds {@param piece} in a random empty position on this board.
     * @param piece the {@link ChessPiece} to add.
     * @return the {@link Position} in which the piece is placed.
     */
    Position addPieceInRandomPosition(ChessPiece piece);

    /**
     *
     * @param piece the {@link ChessPiece} to add.
     * @param position the {@link Position} in which to place the {@param piece}.
     * @return the posion in which the piece is placed.
     * @throws IllegalArgumentException if the piece was already added to this board or if there's
     * already another piece in the position.
     * @throws IndexOutOfBoundsException if the position is outside the boundary of the chessboard.
     */
    Position addPieceIntoPosition(ChessPiece piece, Position position);

    /**
     * Moves the {@param piece} to {@param position} and eventually eat any {@link ChessPiece}.
     * @param piece the {@link ChessPiece} to move.
     * @param position the {@link Position} in which to move the piece.
     * @return an Optional filled with the {@link ChessPiece} eaten or an empty optional of the position was free
     * @throws IndexOutOfBoundsException if the position is outside the boundary of the chessboard.
     */
    Optional<ChessPiece> movePieceToPosition(ChessPiece piece, Position position);

    /**
     *
     * @param position the position to check.
     * @return An Optional filled with the {@link ChessPiece} in the position or empty if the position is free.
     * @throws IndexOutOfBoundsException if the position is outside the boundary of the chessboard.
     */
    Optional<ChessPiece> getPieceInPosition(Position position);
}
