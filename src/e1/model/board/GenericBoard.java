package e1.model.board;

import e1.model.Position;
import e1.model.pieces.ChessPiece;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Random;

public class GenericBoard implements ChessBoard {

    private final Random random = new Random();
    private final Map<ChessPiece, Position> piecesPositions = new HashMap<>();

    @Override
    public int pieceCount() {
        return this.piecesPositions.size();
    }

    @Override
    public Position getPiecePosition(ChessPiece piece) {
        if (this.piecesPositions.containsKey(piece)) {
           return this.piecesPositions.get(piece);
        }
        throw new NoSuchElementException(piece + " was never added to this chessboard");
    }

    @Override
    public Position addPieceInRandomPosition(ChessPiece piece) {
        if (this.piecesPositions.containsKey(piece)) {
            throw new IllegalArgumentException(piece + " was already added to this board");
        }
        return this.piecesPositions.put(piece, this.randomEmptyPosition());
    }

    private Position randomEmptyPosition(){
        Position position = new Position(this.random.nextInt(),this.random.nextInt());
        return this.isPositionEmpty(position) ? position :  randomEmptyPosition();
    }

    private boolean isPositionEmpty(Position position) {
        return !this.piecesPositions.containsValue(position);
    }
}
