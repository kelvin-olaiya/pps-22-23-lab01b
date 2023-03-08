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
        void testCannotMovePieceIfNotAdded() {
            assertThrows(IllegalArgumentException.class, () -> {
                this.chessBoard.movePieceToPosition(this.pieceFactory.newKnight(), new Position(0, 0));
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

        static class SquaredBoardTest extends ChessBoardTest {

            public static final int BOARD_SIZE = 3;

            @BeforeEach
            void setUp() {
                super.setUp();
                this.chessBoard = new SquareBoard(BOARD_SIZE);
            }

            @Test
            void testCantAddPiecesOutOfBounds() {
                assertThrows(IndexOutOfBoundsException.class, () -> {
                   this.chessBoard.addPieceIntoPosition(this.pieceFactory.newKnight(), new Position(-1, 0));
                });
                assertThrows(IndexOutOfBoundsException.class, () -> {
                    this.chessBoard.addPieceIntoPosition(this.pieceFactory.newKnight(), new Position(BOARD_SIZE + 1, 4));
                });
            }
        }
    }
}