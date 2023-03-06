package e1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.function.BiPredicate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class LogicsTest {


    public static final int CHESSBOARD_SIZE = 9;
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
                .filter(position -> Math.abs(knightPosition.getY() - position.getY()) <= 2)
                .filter(position -> Math.abs(knightPosition.getX()) - position.getX() <= 2)
                .filter(position -> isAKnightLegalMove(knightPosition, position));
    }

    private boolean isAKnightLegalMove(Pair<Integer, Integer> from, Pair<Integer, Integer> to) {
        return to.getX() < CHESSBOARD_SIZE && to.getX() >= 0 && to.getY() < CHESSBOARD_SIZE && to.getY() >= 0
                && Math.abs(from.getX() - to.getX()) == 2 && Math.abs(from.getY() - to.getY()) == 1
                || Math.abs(from.getX() - to.getX()) == 1 && Math.abs(from.getY() - to.getY()) == 2;
    }

    private Pair<Integer, Integer> getPawnPosition() {
        return getPieceWith(this.logics::hasPawn);
    }

    private Pair<Integer, Integer> getKnightPosition() {
        return getPieceWith(this.logics::hasKnight);
    }

    private Pair<Integer, Integer> getPieceWith(BiPredicate<Integer, Integer> positionChecker) {
        return this.boardPositions()
                .filter(position -> positionChecker.test(position.getY(), position.getX())).findFirst().get();
    }

    private long countPiecesOnBoardWith(BiPredicate<Integer, Integer> positionChecker) {
        return this.boardPositions().filter(position -> positionChecker.test(position.getY(), position.getX())).count();
    }

    private Stream<Pair<Integer, Integer>> boardPositions() {
        return Stream.iterate(0, i -> i+1)
                .limit(CHESSBOARD_SIZE)
                .flatMap(i -> Stream.iterate(0, j -> j+1)
                        .limit(CHESSBOARD_SIZE)
                        .map(j -> new Pair<>(i, j))
                );
    }
}