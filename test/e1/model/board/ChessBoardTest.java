package e1.model.board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChessBoardTest {

    protected ChessBoard chessBoard;

    static class GenericBoardTest extends ChessBoardTest {

        @BeforeEach
        void setUp() {
            this.chessBoard = new GenericBoard();
        }

        @Test
        void testBoardIsInitiallyEmpty() {
            assertEquals(0, this.chessBoard.pieceCount());
        }
    }
}