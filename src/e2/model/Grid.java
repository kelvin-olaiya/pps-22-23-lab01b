package e2.model;

import java.util.Collection;

public interface Grid {

    Collection<Cell> cells();

    int bombsCount();

    boolean isFlagged(Cell cell);

    void toggleFlag(Cell cell);

    boolean hasBomb(Cell cell);

    Collection<Cell> adjacentCells(Cell cell);

    int adjacentBombs(Cell cell);

    int size();
}
