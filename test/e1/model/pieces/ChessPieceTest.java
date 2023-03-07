package e1.model.pieces;

import e1.model.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ChessPieceTest {

    protected ChessPiece piece;

    static class TestStaticPawn extends ChessPieceTest {

        private static final Position STARTING_POSITION = new Position(3, 4);
        private static final List<Position> TEST_POSITIONS = List.of(
                new Position(3, 5), new Position(3, 5), new Position(2, 6)
        );

        @BeforeEach
        void setUp() {
            this.piece = new StaticPawn();
        }

        @Test
        void testCanMoveToPositionFrom() {
            TEST_POSITIONS.forEach(position ->
                    assertFalse(this.piece.canMoveToPositionFrom(position, STARTING_POSITION))
            );
        }
    }

    static class TestKnight extends ChessPieceTest {

        private static final Position STARTING_POSITION = new Position(5, 5);
        private static final List<Position> LEGAL_MOVES = List.of(
                new Position(3, 6), new Position(3, 4),
                new Position(6, 7), new Position(4, 7),
                new Position(7, 4), new Position(7, 6),
                new Position(6, 3), new Position(4, 3)
        );
        private static final List<Position> ILLEGAL_MOVES = List.of(
                new Position(0, 0), new Position(5, 5), new Position(2, 3), new Position(4, 5)
        );

        @BeforeEach
        void setUp() {
            this.piece = new Knight();
        }

        @Test
        void testCanMoveToLegalPositions() {
                LEGAL_MOVES.forEach(position ->
                        assertTrue(this.piece.canMoveToPositionFrom(position, STARTING_POSITION))
                );
        }

        @Test
        void testCannotMoveToIllegalPositions() {
            ILLEGAL_MOVES.forEach(position ->
                    assertFalse(this.piece.canMoveToPositionFrom(position, STARTING_POSITION))
            );
        }
    }

}