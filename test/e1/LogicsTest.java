package e1;

import e1.model.Position;
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
    private static final Position PAWN_POSITION = new Position(3, 5);
    private static final Position INITIAL_KNIGHT_POSITION = new Position(6, 2);
    private static final List<Position> NON_WINNING_LEGAL_HITS = List.of(
            new Position(5, 4), new Position(4, 2),
            new Position(3, 4), new Position(2, 6),
            new Position(3, 8), new Position(4, 6),
            new Position(6, 5), new Position(8, 6)
    );
    private static final List<Position> ILLEGAL_HITS = List.of(
            new Position(0, 0), new Position(3, 3), new Position(5, 7), new Position(3, 6)
    );
    private static final List<Position> WINNING_SEQUENCE = List.of(new Position(5, 4), PAWN_POSITION);

    private Logics logics;

    @BeforeEach
    void setUp() {
        this.logics = new LogicsImpl(CHESSBOARD_SIZE, PAWN_POSITION, INITIAL_KNIGHT_POSITION);
    }

    @Test
    void testPiecesCorrectlyPlaced() {
        assertEquals(INITIAL_KNIGHT_POSITION, getKnightPosition());
        assertEquals(PAWN_POSITION, getPawnPosition());
    }

    @Test
    void testBoardHasOnlyOneKnight() {
        assertEquals(1, countPiecesOnBoardWith(this.logics::hasKnight));
    }

    @Test
    void testBoardHasOnlyOnePawn() {
        assertEquals(1, countPiecesOnBoardWith(this.logics::hasPawn));
    }

    private long countPiecesOnBoardWith(BiPredicate<Integer, Integer> positionChecker) {
        return this.boardPositions().filter(position -> positionChecker.test(position.getX(), position.getY())).count();
    }

    private Stream<Position> boardPositions() {
        return Stream.iterate(0, i -> i+1).limit(CHESSBOARD_SIZE)
                .flatMap(i -> Stream.iterate(0, j -> j+1).limit(CHESSBOARD_SIZE).map(j -> new Position(i, j)));
    }

    @Test
    void testKnightCanDoAllLegalMoves() {
         assertTrue(knightLegalMovesFrom(getKnightPosition()).allMatch(this::testKnightMove));
    }

    private Stream<Position> knightLegalMovesFrom(Pair<Integer, Integer> knightPosition) {
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

    private boolean testKnightMove(Pair<Integer, Integer> to) {
        Pair<Integer, Integer> originalPosition = getKnightPosition();
        assertDoesNotThrow(() -> {
            this.logics.hit(to.getX(), to.getY());
        });
        this.logics.hit(originalPosition.getX(), originalPosition.getY());
        return true;
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
                .forEach(hitPosition -> assertEquals(INITIAL_KNIGHT_POSITION, getKnightPosition()));
    }

    @Test
    void testKnightCanWin() {
        WINNING_SEQUENCE.stream()
                .limit(WINNING_SEQUENCE.size() - 1)
                .forEach(hitPosition -> this.logics.hit(hitPosition.getX(), hitPosition.getY()));
        Pair<Integer, Integer> lastHitPosition = WINNING_SEQUENCE.get(WINNING_SEQUENCE.size()-1);
        assertTrue(this.logics.hit(lastHitPosition.getX(), lastHitPosition.getY()));
    }

    private Position getPawnPosition() {
        return getPiecePositionWith(this.logics::hasPawn);
    }

    private Position getKnightPosition() {
        return getPiecePositionWith(this.logics::hasKnight);
    }

    private Position getPiecePositionWith(BiPredicate<Integer, Integer> positionChecker) {
        return this.boardPositions()
                .filter(position -> positionChecker.test(position.getX(), position.getY())).findFirst().get();
    }
}