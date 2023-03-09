package e1.model.board;

import e1.model.Position;
import e1.model.pieces.ChessPiece;

import java.util.*;

public class ChessBoardImpl implements ChessBoard {

    private final Random random = new Random();
    private final Map<ChessPiece, Position> piecesPositions = new HashMap<>();
    private final int size;

    public ChessBoardImpl(final int size) {
        this.size = size;
    }

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
        final Position randomPosition = this.randomEmptyPosition();
        this.addPieceIntoPosition(piece, randomPosition);
        return randomPosition;
    }

    @Override
    public Position addPieceIntoPosition(ChessPiece piece, Position position) {
        checkIfOutOfBounds(position);
        if (this.piecesPositions.containsKey(piece)) {
            throw new IllegalArgumentException(piece + " was already added to this board");
        } else if (!isPositionEmpty(position)) {
            throw new IllegalArgumentException("Another piece was already places in " + position);
        }
        this.piecesPositions.put(piece, position);
        return position;
    }

    @Override
    public Optional<ChessPiece> movePieceToPosition(ChessPiece piece, Position position) {
        checkIfOutOfBounds(position);
        if (!this.piecesPositions.containsKey(piece)) {
            throw new IllegalArgumentException(piece + " is not on this board");
        } else if (piece.canMoveToPositionFrom(position, this.getPiecePosition(piece))) {
            Optional<ChessPiece> eatenPiece = getPieceInPosition(position);
            piecesPositions.put(piece, position);
            eatenPiece.ifPresent(this::eatPiece);
            return eatenPiece;
        }
        throw new IllegalArgumentException(
                piece + " is not allowed to move to " + position + " from " + getPiecePosition(piece)
        );
    }

    private void eatPiece(ChessPiece eatenPiece) {
        this.piecesPositions.remove(eatenPiece);
    }

    private Optional<ChessPiece> getPieceInPosition(Position position) {
        checkIfOutOfBounds(position);
        return this.piecesPositions.entrySet().stream()
                .filter(entry -> entry.getValue().equals(position))
                .map(Map.Entry::getKey).findFirst();
    }

    private Position randomEmptyPosition(){
        Position position = new Position(this.random.nextInt(size),this.random.nextInt(size));
        return this.isPositionEmpty(position) ? position :  randomEmptyPosition();
    }

    private boolean isPositionEmpty(Position position) {
        return !this.piecesPositions.containsValue(position);
    }

    private boolean isPositionOutOfBounds(Position position) {
        return Math.min(position.getX(), position.getY()) < 0
                || Math.max(position.getX(), position.getY()) >= size;
    }

    private void checkIfOutOfBounds(Position position) {
        if (isPositionOutOfBounds(position)) {
            throw new IndexOutOfBoundsException();
        }
    }
}
