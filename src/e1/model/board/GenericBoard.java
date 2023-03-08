package e1.model.board;

import e1.model.Position;
import e1.model.pieces.ChessPiece;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class GenericBoard implements ChessBoard {

    private final Map<ChessPiece, Position> piecesPositions = new HashMap<>();

    @Override
    public int pieceCount() {
        return 0;
    }

    @Override
    public Position getPiecePosition(ChessPiece piece) {
        if (this.piecesPositions.containsKey(piece)) {
           return this.piecesPositions.get(piece);
        }
        throw new NoSuchElementException(piece + " was never added to this chessboard");
    }
}
