package e1.model.board;

import e1.model.Position;
import e1.model.pieces.ChessPiece;
import e1.model.pieces.PieceFactory;
import e1.model.pieces.PieceFactoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ChessBoardTest {

    private static final int BOARD_SIZE = 9;
    private static final Position PAWN_POSITION = new Position(1, 2);
    private static final Position KNIGHT_POSITION = new Position(3, 3);
    protected ChessBoard chessBoard;
    protected PieceFactory pieceFactory;

    @BeforeEach
    void setUp() {
        this.pieceFactory = new PieceFactoryImpl();
        this.chessBoard = new ChessBoardImpl(BOARD_SIZE);
    }

    @Test
    void testBoardIsInitiallyEmpty() {
        assertEquals(0, this.chessBoard.pieceCount());
    }

    @Test
    void testCantGetPositionBeforeAdding() {
        ChessPiece pawn = this.pieceFactory.newStaticPawn();
        assertThrows(NoSuchElementException.class, () -> {
            this.chessBoard.getPiecePosition(pawn);
        });
    }

    @Test
    void testCanAddPieces() {
        int piecesToAdd = 6;
        for (var i = 0; i < piecesToAdd; i++) {
            this.chessBoard.addPieceInRandomPosition(this.pieceFactory.newStaticPawn());
        }
        assertEquals(piecesToAdd, this.chessBoard.pieceCount());
    }

    @Test
    void testCantAddTheSamePieceTwice() {
        final ChessPiece pawn = this.pieceFactory.newStaticPawn();
        this.chessBoard.addPieceInRandomPosition(pawn);
        assertThrows(IllegalArgumentException.class, () -> {
            this.chessBoard.addPieceInRandomPosition(pawn);
        });
    }

    @Test
    void testAddPieceToSpecificPosition() {
        final ChessPiece pawn = this.pieceFactory.newStaticPawn();
        this.chessBoard.addPieceIntoPosition(pawn, PAWN_POSITION);
        assertEquals(PAWN_POSITION, this.chessBoard.getPiecePosition(pawn));
    }

    @Test
    void testCantAddDifferentPiecesInTheSamePosition() {
        this.chessBoard.addPieceIntoPosition(this.pieceFactory.newStaticPawn(), PAWN_POSITION);
        assertThrows(IllegalArgumentException.class, () -> {
            this.chessBoard.addPieceIntoPosition(this.pieceFactory.newStaticPawn(), PAWN_POSITION);
        });
    }

    @Test
    void testMovePieceToLegalPositions() {
        final ChessPiece knight = this.pieceFactory.newKnight();
        this.chessBoard.addPieceIntoPosition(knight, KNIGHT_POSITION);
        final List<Position> legalMoves = List.of(new Position(1, 4), KNIGHT_POSITION, new Position(5, 2));
        legalMoves.forEach(position -> assertDoesNotThrow(() -> this.chessBoard.movePieceToPosition(knight, position)));
    }

    @Test
    void testCannotMovePieceIfNotAdded() {
        assertThrows(
                IllegalArgumentException.class,
                () -> this.chessBoard.movePieceToPosition(this.pieceFactory.newKnight(), new Position(0, 0))
        );
    }

    @Test
    void testCantMoveToIllegalPositions() {
        final ChessPiece knight = this.pieceFactory.newKnight();
        final List<Position> illegalMoves = List.of(KNIGHT_POSITION, new Position(6, 3));
        this.chessBoard.addPieceIntoPosition(knight, KNIGHT_POSITION);
        illegalMoves.forEach(position ->
                assertThrows(
                        IllegalArgumentException.class,
                        () -> this.chessBoard.movePieceToPosition(knight, position)
                )
        );
    }

    @Test
    void testMovePieceAndEat() {
        final ChessPiece pawn = this.pieceFactory.newStaticPawn();
        final ChessPiece knight = this.pieceFactory.newKnight();
        this.chessBoard.addPieceIntoPosition(pawn, PAWN_POSITION);
        this.chessBoard.addPieceIntoPosition(knight, KNIGHT_POSITION);
        Optional<ChessPiece> eatenPiece = this.chessBoard.movePieceToPosition(knight, PAWN_POSITION);
        assertTrue(eatenPiece.isPresent());
        assertEquals(pawn, eatenPiece.get());
        assertEquals(1, this.chessBoard.pieceCount());
    }

    @Test
    void testCantAddPiecesOutOfBounds() {
        final List<Position> outOfBoundsPositions = List.of(
                new Position(-1, 0), new Position(BOARD_SIZE + 1, 4)
        );
        outOfBoundsPositions.forEach(position ->
            assertThrows(
                    IndexOutOfBoundsException.class,
                    () -> this.chessBoard.addPieceIntoPosition(this.pieceFactory.newKnight(), position)
            )
        );
    }
}
