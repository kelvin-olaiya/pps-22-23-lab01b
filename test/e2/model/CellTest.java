package e2.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CellTest {

    private static final Cell CELL = new Cell(3, 3);
    private static final List<Cell> ADJACENT_TO_CELL = List.of(
            new Cell(2, 3),
            new Cell(2, 2),
            new Cell(2, 4),
            new Cell(4, 4)
    );

    private static final List<Cell> NON_ADJACENT_TO_CELL = List.of(
            new Cell(4, 5),
            new Cell(6, 2),
            new Cell(5, 1),
            new Cell(3, 5)
    );

    @Test
    void testAdjacency() {
        ADJACENT_TO_CELL.forEach(cell -> assertTrue(CELL.isAdjacentTo(cell)));
        NON_ADJACENT_TO_CELL.forEach(cell -> assertFalse(CELL.isAdjacentTo(cell)));
    }
}