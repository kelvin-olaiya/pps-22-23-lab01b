package e2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class LogicsTest {

    private static final int GRID_SIZE = 6;
    private static final int NUM_OF_BOMBS = 5;
    public static final int RANDOM_POSITIONS_TO_TEST = 10;
    private Logics logics;

    @BeforeEach
    void setUp() {
        this.logics = new LogicsImpl(GRID_SIZE, NUM_OF_BOMBS);
    }

    @Test
    void testBombsAreCorrectlyPlaced() {
        assertEquals(
                NUM_OF_BOMBS,
                grid().filter(position -> this.logics.hasBomb(position.getX(), position.getY())).count()
        );
    }

    @Test
    void testClickOnABomb() {
        Optional<Pair<Integer, Integer>> cellWithBomb = this.grid()
                .filter(position -> this.logics.hasBomb(position.getX(), position.getY())).findAny();
        assertTrue(cellWithBomb.isPresent());
        var position = cellWithBomb.get();
        assertTrue(this.logics.hit(position.getX(), position.getY()));
    }

    @Test
    void testCanFlag() {
        this.logics.toggleFlag(3, 3);
        assertTrue(this.logics.hasFlag(3, 3));
    }

    @Test
    void testHittedCellsAdjacentBombs() {
        randomClearPositions(RANDOM_POSITIONS_TO_TEST).forEach(position -> {
            assertFalse(this.logics.hit(position.getX(), position.getY()));
            var numberOfBombs = (int) this.adjacentPositions(position)
                    .filter(cell -> this.logics.hasBomb(cell.getX(), cell.getY()))
                    .count();
            var result = this.logics.adjacentBombs(position.getX(), position.getY());
            assertTrue(result.isPresent());
            assertEquals(numberOfBombs, result.get());
        });
    }

    @Test
    void testUnhittedCellsAdjacentBombs() {
        randomClearPositions(RANDOM_POSITIONS_TO_TEST).forEach(position -> {
            assertEquals(Optional.empty(), this.logics.adjacentBombs(position.getX(), position.getY()));
        });
    }

    @Test
    void testVictory() {
        allClearPositions().forEach(position -> this.logics.hit(position.getX(), position.getY()));
        assertTrue(this.logics.hasWon());
    }

    @Test
    void testAutoHit() {
        var cell = allClearPositions()
                .peek(position -> this.logics.hit(position.getX(), position.getY()))
                .filter(position -> this.logics.adjacentBombs(position.getX(), position.getY()).equals(Optional.of(0)))
                .findAny();
        assertTrue(cell.isPresent());
        this.adjacentPositions(cell.get())
                .forEach(position ->
                        assertTrue(this.logics.adjacentBombs(position.getX(), position.getY()).isPresent())
                );
    }

    private Stream<Pair<Integer, Integer>> allClearPositions() {
        return this.grid().filter(position -> !this.logics.hasBomb(position.getX(), position.getY()));
    }

    private Stream<Pair<Integer, Integer>> randomClearPositions(int numberOfPosition) {
        var random = new Random();
        var gridList = grid().toList();
        return Stream.generate(() -> random.nextInt(GRID_SIZE * GRID_SIZE))
                .map(gridList::get)
                .distinct()
                .filter(position -> !this.logics.hasBomb(position.getX(), position.getY()))
                .limit(numberOfPosition);
    }

    private Stream<Pair<Integer, Integer>> grid() {
        return Stream.iterate(0, i -> i + 1)
                .limit(GRID_SIZE)
                .flatMap(i -> Stream.iterate(0, j -> j +1).limit(GRID_SIZE).map(j -> new Pair<>(i, j)));
    }

    private Stream<Pair<Integer, Integer>> adjacentPositions(Pair<Integer, Integer> position) {
        return this.grid().filter(cell -> this.isAdjacent(cell, position));
    }

    private boolean isAdjacent(Pair<Integer, Integer> pos1, Pair<Integer, Integer> pos2) {
        int x = Math.abs(pos1.getX() - pos2.getX());
        int y = Math.abs(pos1.getY() - pos2.getY());
        return !(x == 0 && y == 0) && x <= 1 && y <= 1;
    }
}
