package e1.model.board;

import e1.model.Position;
import e1.model.pieces.ChessPiece;

import java.util.Optional;

public interface ChessBoard {
    int pieceCount();

    Position getPiecePosition(ChessPiece piece);

    Position addPieceInRandomPosition(ChessPiece piece);

    Position addPieceIntoPosition(ChessPiece piece, Position position);

    Optional<ChessPiece> movePieceToPosition(ChessPiece piece, Position position);
}
