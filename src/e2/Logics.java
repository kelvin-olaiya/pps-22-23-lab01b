package e2;

import java.util.Optional;

public interface Logics {

    boolean hasBomb(int row, int column);

    boolean hit(int row, int column);

    void toggleFlag(int row, int column);

    boolean hasFlag(int row, int column);

    Optional<Integer> adjacentBombs(int row, int column);

    boolean hasWon();
}
