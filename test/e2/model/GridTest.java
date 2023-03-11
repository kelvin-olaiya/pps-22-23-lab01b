package e2.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class GridTest {


    private static final int NUM_BOMBS = 7;
    private static final int GRID_SIZE = 5;
    private static final int CORNER_ADJACENT_CELLS = 3;
    private static final int SIDE_ADJACENT_CELLS = 5;
    private static final int MIDDLE_ADJACENT_CELLS = 8;

    private Grid grid;

    @BeforeEach
    void setUp() {
        this.grid = new GridImpl(GRID_SIZE, NUM_BOMBS);
    }

    @Test
    void testBombsCorrectlyPlaced() {
        assertEquals(NUM_BOMBS, this.grid.bombsCount());
    }

    @Test
    void testGridCellHasBombs() {
        assertEquals(NUM_BOMBS, gridCellsStream().filter(this.grid::hasBomb).count());
    }

    @Test
    void testAdjacentCells() {
        assertEquals(CORNER_ADJACENT_CELLS, this.grid.adjacentCells(new Cell(0, 0)).size());
        assertEquals(SIDE_ADJACENT_CELLS, this.grid.adjacentCells(new Cell(0, GRID_SIZE / 2)).size());
        assertEquals(MIDDLE_ADJACENT_CELLS, this.grid.adjacentCells(new Cell(GRID_SIZE / 2, GRID_SIZE / 2)).size());
    }

    private Stream<Cell> gridCellsStream() {
        return this.grid.cells().stream();
    }
}
