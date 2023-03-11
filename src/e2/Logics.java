package e2;

import java.util.Optional;

public interface Logics {

    /**
     *
     * @param row
     * @param column
     * @return true if cell in position (row, column) contains a bomb.
     */
    boolean hasBomb(int row, int column);

    /**
     *
     * @param row
     * @param column
     * @return true if the hitted cell contained a bomb.
     */
    boolean hit(int row, int column);

    /**
     * Toggles a flag in the cell in position (row, column).
     * @param row
     * @param column
     */
    void toggleFlag(int row, int column);

    /**
     *
     * @param row
     * @param column
     * @return true if the cell in position (row, column) is flagged.
     */
    boolean hasFlag(int row, int column);

    /**
     *
     * @param row
     * @param column
     * @return an empty optional if the cell was not (auto)hit or filled with
     * the number of adjacent cell.
     */
    Optional<Integer> adjacentBombs(int row, int column);

    /**
     *
     * @return true if all cells except the bombs were clicked.
     */
    boolean hasWon();
}
