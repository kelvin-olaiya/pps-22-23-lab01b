package e2.model;

import java.util.Collection;

public interface Grid {

    Collection<Cell> cells();

    int bombsCount();

    boolean hasBomb(Cell cell);

    Collection<Cell> adjacentCells(Cell cell);

    int adjacentBombs(Cell cell);

    int size();
}
