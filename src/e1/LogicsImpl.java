package e1;

import e1.model.Position;
import e1.model.pieces.ChessPiece;
import e1.model.pieces.PieceFactory;
import e1.model.pieces.PieceFactoryImpl;

import java.util.*;

public class LogicsImpl implements Logics {
	
	private ChessPiece pawn;
	private ChessPiece knight;

	private final PieceFactory pieceFactory = new PieceFactoryImpl();
	private final Map<ChessPiece, Position> positions = new HashMap<>();
	private final Random random = new Random();
	private final int size;

    public LogicsImpl(int size) {
		this.size = size;
		placePieces(this.randomEmptyPosition(), this.randomEmptyPosition());

    }

	public LogicsImpl(int size, Position pawnPosition, Position knightPosition) {
		this.size = size;
		placePieces(pawnPosition, knightPosition);
	}

	private void placePieces(Position pawnPosition, Position knightPosition) {
		this.pawn = this.pieceFactory.newStaticPawn();
		this.knight = this.pieceFactory.newKnight();
		this.positions.put(this.pawn, pawnPosition);
		this.positions.put(this.knight, knightPosition);
	}

	private Position randomEmptyPosition(){
		Position position = new Position(this.random.nextInt(size),this.random.nextInt(size));
		// the recursive call below prevents clash with an existing pawn
		return this.isPositionEmpty(position) ? position :  randomEmptyPosition();
	}

	private boolean isPositionEmpty(final Position position) {
		return !this.positions.containsValue(position);
	}
    
	@Override
	public boolean hit(int row, int col) {
		if (row<0 || col<0 || row >= this.size || col >= this.size) {
			throw new IndexOutOfBoundsException();
		}
		final Position newPosition = new Position(row, col);
		// Below a compact way to express allowed moves for the knight
		if (canKnightMoveTo(newPosition)) {
			this.positions.put(this.knight, newPosition);
			return this.positions.get(this.pawn).equals(newPosition);
		}
		return false;
	}

	private boolean canKnightMoveTo(Position position) {
		return this.knight.canMoveToPositionFrom(this.positions.get(this.knight), position);
	}

	@Override
	public boolean hasKnight(int row, int col) {
		return this.positions.get(this.knight).equals(new Position(row, col));
	}

	@Override
	public boolean hasPawn(int row, int col) {
		return this.positions.get(this.pawn).equals(new Position(row, col));
	}
}
