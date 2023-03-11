package e2.model;

import java.util.Collection;

public interface Grid {

    /**
     *
     * @return a collections of the grid cells.
     */
    Collection<Cell> cells();

    /**
     *
     * @return the number of bombs placed in the grid.
     */
    int bombsCount();

    /**
     *
     * @param cell
     * @return true if the cell contains a bombs.
     */
    boolean hasBomb(Cell cell);

    /**
     *
     * @param cell
     * @return a collection of cells adjacent to cell.
     */
    Collection<Cell> adjacentCells(Cell cell);

    /**
     *
     * @param cell
     * @return the number of adjacent bombs.
     */
    int adjacentBombs(Cell cell);

    /**
     *
     * @return the number of grid cells.
     */
    int size();
}
