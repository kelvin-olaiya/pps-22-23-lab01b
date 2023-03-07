package e1.model;

import e1.model.pieces.ChessPiece;
import e1.model.pieces.StaticPawn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ChessPieceTest {

    protected ChessPiece piece;

    static class TestStaticPawn extends ChessPieceTest {
        @BeforeEach
        void setUp() {
            this.piece = new StaticPawn();
        }

        @Test
        void canMoveToPositionFrom() {

        }
    }

}