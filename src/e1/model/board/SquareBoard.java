package e1.model.board;

import e1.model.Position;
import e1.model.pieces.ChessPiece;

import java.util.Optional;

public class SquareBoard implements ChessBoard {

    private final ChessBoard board = new GenericBoard();
    private final int boardSize;

    public SquareBoard(int boardSize) {
        this.boardSize = boardSize;
    }

    @Override
    public int pieceCount() {
        return board.pieceCount();
    }

    @Override
    public Position getPiecePosition(ChessPiece piece) {
        return board.getPiecePosition(piece);
    }

    @Override
    public Position addPieceInRandomPosition(ChessPiece piece) {
        return board.addPieceInRandomPosition(piece);
    }

    @Override
    public Position addPieceIntoPosition(ChessPiece piece, Position position) {
        checkIsInBounds(position);
        return board.addPieceIntoPosition(piece, position);
    }

    @Override
    public Optional<ChessPiece> movePieceToPosition(ChessPiece piece, Position position) {
        checkIsInBounds(position);
        return board.movePieceToPosition(piece, position);
    }

    private void checkIsInBounds(Position position) {
        if (!isWithinBounds(position)) {
            throw new IndexOutOfBoundsException();
        }
    }

    private boolean isWithinBounds(Position position) {
        return position.getX() >= 0 && position.getX() < boardSize
                && position.getY() >= 0 && position.getY() < boardSize;
    }
}
