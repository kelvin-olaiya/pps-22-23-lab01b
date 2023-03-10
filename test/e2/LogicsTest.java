package e2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class LogicsTest {

    private static final int GRID_SIZE = 6;
    private static final int NUM_OF_BOMBS = 5;
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

    private Stream<Pair<Integer, Integer>> grid() {
        return Stream.iterate(0, i -> i + 1)
                .limit(GRID_SIZE)
                .flatMap(i -> Stream.iterate(0, j -> j +1).limit(GRID_SIZE).map(j -> new Pair<>(i, j)));
    }

}