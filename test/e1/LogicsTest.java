package e1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.function.BiPredicate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class LogicsTest {


    private static final int CHESSBOARD_SIZE = 9;
    private static final int KNIGHT_MAX_MOVE = 2;
    private static final int KNIGHT_MIN_MOVE = 1;

    private Logics logics;

    @BeforeEach
    void setUp() {
        this.logics = new LogicsImpl(CHESSBOARD_SIZE);
    }

    @Test
    void testBoardHasOnlyOneKnight() {
        assertEquals(1, countPiecesOnBoardWith(this.logics::hasKnight));
    }

    @Test
    void testBoardHasOnlyOnePawn() {
        assertEquals(1, countPiecesOnBoardWith(this.logics::hasPawn));
    }

    @Test
    void testKnightCanDoAllLegalMoves() {
         assertTrue(knightLegalMovesFrom(getKnightPosition()).allMatch(this::testKnightMove));
    }

    private boolean testKnightMove(Pair<Integer, Integer> to) {
        Pair<Integer, Integer> originalPosition = getKnightPosition();
        assertDoesNotThrow(() -> {
            this.logics.hit(to.getX(), to.getY());
        });
        this.logics.hit(originalPosition.getX(), originalPosition.getY());
        return true;
    }

    private Stream<Pair<Integer, Integer>> knightLegalMovesFrom(Pair<Integer, Integer> knightPosition) {
        return boardPositions()
                .filter(position -> Math.abs(knightPosition.getY() - position.getY()) <= KNIGHT_MAX_MOVE)
                .filter(position -> Math.abs(knightPosition.getX() - position.getX()) <= KNIGHT_MAX_MOVE)
                .filter(position -> isAKnightLegalMove(knightPosition, position));
    }

    private boolean isAKnightLegalMove(Pair<Integer, Integer> from, Pair<Integer, Integer> to) {
        final int horizontalDistance = Math.abs(from.getX() - to.getX());
        final int verticalDistance = Math.abs(from.getY() - to.getY());
        return !isPositionOutOfBounds(to) && Math.min(horizontalDistance, verticalDistance) != 0
                && horizontalDistance + verticalDistance == KNIGHT_MAX_MOVE + KNIGHT_MIN_MOVE;
    }

    private boolean isPositionOutOfBounds(Pair<Integer, Integer> position) {
        return position.getX() < CHESSBOARD_SIZE && position.getX() >= 0
                && position.getY() < CHESSBOARD_SIZE && position.getY() >= 0;
    }

    private Pair<Integer, Integer> getPawnPosition() {
        return getPiecePositionWith(this.logics::hasPawn);
    }

    private Pair<Integer, Integer> getKnightPosition() {
        return getPiecePositionWith(this.logics::hasKnight);
    }

    private Pair<Integer, Integer> getPiecePositionWith(BiPredicate<Integer, Integer> positionChecker) {
        return this.boardPositions()
                .filter(position -> positionChecker.test(position.getY(), position.getX())).findFirst().get();
    }

    private long countPiecesOnBoardWith(BiPredicate<Integer, Integer> positionChecker) {
        return this.boardPositions().filter(position -> positionChecker.test(position.getY(), position.getX())).count();
    }

    private Stream<Pair<Integer, Integer>> boardPositions() {
        return Stream.iterate(0, i -> i+1).limit(CHESSBOARD_SIZE)
                .flatMap(i -> Stream.iterate(0, j -> j+1).limit(CHESSBOARD_SIZE).map(j -> new Pair<>(j, i)));
    }
}