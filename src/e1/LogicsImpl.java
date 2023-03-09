package e1;

import e1.model.Position;
import e1.model.board.ChessBoard;
import e1.model.board.ChessBoardImpl;
import e1.model.pieces.ChessPiece;
import e1.model.pieces.PieceFactory;
import e1.model.pieces.PieceFactoryImpl;

import java.util.*;

public class LogicsImpl implements Logics {
	
	private ChessPiece pawn;
	private ChessPiece knight;
	private final PieceFactory pieceFactory = new PieceFactoryImpl();
	private final ChessBoard chessboard;

	public LogicsImpl(int size) {
		this.chessboard = new ChessBoardImpl(size);
		createPieces();
		placePiecesRandomly();
    }

	public LogicsImpl(int size, Position pawnPosition, Position knightPosition) {
		this.chessboard = new ChessBoardImpl(size);
		createPieces();
		placePieces(pawnPosition, knightPosition);
	}

	private void createPieces() {
		this.pawn = this.pieceFactory.newStaticPawn();
		this.knight = this.pieceFactory.newKnight();
	}

	private void placePiecesRandomly() {
		this.chessboard.addPieceInRandomPosition(pawn);
		this.chessboard.addPieceInRandomPosition(knight);
	}

	private void placePieces(Position pawnPosition, Position knightPosition) {
		this.chessboard.addPieceIntoPosition(pawn, pawnPosition);
		this.chessboard.addPieceIntoPosition(knight, knightPosition);
	}
	@Override
	public boolean hit(int row, int col) {
		try {
			Optional<ChessPiece> eatenPiece = this.chessboard.movePieceToPosition(knight, new Position(row, col));
			return eatenPiece.isPresent() && eatenPiece.get().equals(pawn);
		} catch (IllegalArgumentException ignore) { }
		return false;
	}

	@Override
	public boolean hasKnight(int row, int col) {
		return hasPiece(new Position(row, col), this.knight);
	}

	@Override
	public boolean hasPawn(int row, int col) {
		return hasPiece(new Position(row, col), this.pawn);
	}

	private boolean hasPiece(final Position position, ChessPiece piece) {
		Optional<ChessPiece> pieceInPosition = this.chessboard.getPieceInPosition(position);
		return pieceInPosition.isPresent() && pieceInPosition.get().equals(piece);
	}
}
