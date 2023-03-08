package e1.model.board;

import e1.model.Position;
import e1.model.pieces.ChessPiece;
import e1.model.pieces.PieceFactory;
import e1.model.pieces.PieceFactoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ChessBoardTest {

    protected ChessBoard chessBoard;
    protected PieceFactory pieceFactory;

    @BeforeEach
    void setUp() {
        this.pieceFactory = new PieceFactoryImpl();
    }

    static class GenericBoardTest extends ChessBoardTest {

        @BeforeEach
        void setUp() {
            super.setUp();
            this.chessBoard = new GenericBoard();
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
            final Position pawnPosition = new Position(1, 2);
            this.chessBoard.addPieceIntoPosition(pawn, pawnPosition);
            assertEquals(pawnPosition, this.chessBoard.getPiecePosition(pawn));
        }

        @Test
        void testCantAddDifferentPiecesInTheSamePosition() {
            final Position position = new Position(1, 2);
            this.chessBoard.addPieceIntoPosition(this.pieceFactory.newStaticPawn(), position);
            assertThrows(IllegalArgumentException.class, () -> {
               this.chessBoard.addPieceIntoPosition(this.pieceFactory.newStaticPawn(), position);
            });
        }

        @Test
        void testMovePieceToLegalPositions() {
            final ChessPiece knight = this.pieceFactory.newKnight();
            this.chessBoard.addPieceIntoPosition(knight, new Position(3, 3));
            assertDoesNotThrow(() -> {
                this.chessBoard.movePieceToPosition(knight, new Position(1, 4));
                this.chessBoard.movePieceToPosition(knight, new Position(3, 3));
                this.chessBoard.movePieceToPosition(knight, new Position(5, 2));
            });
        }

        @Test
        void testCantMoveToIllegalPositions() {
            final ChessPiece knight = this.pieceFactory.newKnight();
            this.chessBoard.addPieceIntoPosition(knight, new Position(3, 3));
            assertThrows(IllegalArgumentException.class, () -> {
               this.chessBoard.movePieceToPosition(knight, new Position(3, 3));
            });
            assertThrows(IllegalArgumentException.class, () -> {
                this.chessBoard.movePieceToPosition(knight, new Position(6, 3));
            });
        }

        @Test
        void testMovePieceAndEat() {
            final ChessPiece pawn = this.pieceFactory.newStaticPawn();
            final Position pawnPosition = new Position(5, 2);
            final ChessPiece knight = this.pieceFactory.newKnight();
            this.chessBoard.addPieceIntoPosition(pawn, pawnPosition);
            this.chessBoard.addPieceIntoPosition(knight, new Position(3, 3));
            Optional<ChessPiece> eatenPiece = this.chessBoard.movePieceToPosition(knight, pawnPosition);
            assertTrue(eatenPiece.isPresent());
            assertEquals(pawn, eatenPiece.get());
            assertEquals(1, this.chessBoard.pieceCount());
        }
    }
}