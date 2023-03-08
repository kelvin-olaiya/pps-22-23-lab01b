package e1.model.board;

import e1.model.pieces.ChessPiece;
import e1.model.pieces.PieceFactory;
import e1.model.pieces.PieceFactoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

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
        void testCannotGetPositionBeforeAdding() {
            ChessPiece pawn = this.pieceFactory.newStaticPawn();
            assertThrows(NoSuchElementException.class, () -> {
                this.chessBoard.getPiecePosition(pawn);
            });
        }
    }
}