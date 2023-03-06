package e1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.BiPredicate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class LogicsTest {


    private static final int CHESSBOARD_SIZE = 9;
    private static final int KNIGHT_MAX_MOVE = 2;
    private static final int KNIGHT_MIN_MOVE = 1;
    private static final Pair<Integer, Integer> INITIAL_PAWN_POSITION = new Pair<>(3, 5);
    private static final Pair<Integer, Integer> INITIAL_KNIGHT_POSITION = new Pair<>(6, 2);
    private static final List<Pair<Integer, Integer>> NON_WINNING_LEGAL_HITS = List.of(
            new Pair<>(5, 4), new Pair<>(4, 2),
            new Pair<>(3, 4), new Pair<>(2, 6),
            new Pair<>(3, 8), new Pair<>(4, 6),
            new Pair<>(6, 5), new Pair<>(8, 6)
    );
    private static final List<Pair<Integer, Integer>> ILLEGAL_HITS = List.of(new Pair<>(0, 0));
    private static final Pair<Integer, Integer> WINNING_HIT = new Pair<>(0, 0);

    private Logics logics;

    @BeforeEach
    void setUp() {
        this.logics = new LogicsImpl(CHESSBOARD_SIZE, INITIAL_PAWN_POSITION, INITIAL_KNIGHT_POSITION);
    }

    @Test
    void testPiecesCorrectlyPlaced() {
        assertEquals(INITIAL_KNIGHT_POSITION, getKnightPosition());
        assertEquals(INITIAL_PAWN_POSITION, getPawnPosition());
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
    @Test
    void testKnightCanMoveWithoutWinning() {
        NON_WINNING_LEGAL_HITS.stream()
                .peek(hitPosition -> assertFalse(this.logics.hit(hitPosition.getX(), hitPosition.getY())))
                .forEach(hitPosition -> assertEquals(hitPosition, getKnightPosition()));
    }

    @Test
    void testCannotMoveToIllegalPositions() {
        ILLEGAL_HITS.stream().peek(hitPosition -> assertFalse(this.logics.hit(hitPosition.getX(), hitPosition.getY())))
                .forEach(hitPosition -> assertEquals(INITIAL_PAWN_POSITION, getKnightPosition()));
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
        final int verticalDistance = Math.abs(from.getX() - to.getX());
        final int horizontalDistance = Math.abs(from.getY() - to.getY());
        return !isPositionOutOfBounds(to) && Math.min(verticalDistance, horizontalDistance) != 0
                && verticalDistance + horizontalDistance == KNIGHT_MAX_MOVE + KNIGHT_MIN_MOVE;
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
                .filter(position -> positionChecker.test(position.getX(), position.getY())).findFirst().get();
    }

    private long countPiecesOnBoardWith(BiPredicate<Integer, Integer> positionChecker) {
        return this.boardPositions().filter(position -> positionChecker.test(position.getX(), position.getY())).count();
    }

    private Stream<Pair<Integer, Integer>> boardPositions() {
        return Stream.iterate(0, i -> i+1).limit(CHESSBOARD_SIZE)
                .flatMap(i -> Stream.iterate(0, j -> j+1).limit(CHESSBOARD_SIZE).map(j -> new Pair<>(i, j)));
    }
}