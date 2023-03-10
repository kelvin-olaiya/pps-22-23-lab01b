package e2;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class LogicsImpl implements Logics {

    private final Map<Pair<Integer, Integer>, Boolean> grid = new HashMap<>();
    private final Random random = new Random();
    private final int size;

    public LogicsImpl(int size, int numberOfBombs) {
        this.size = size;
        createGrid(size);
        placeBombs(numberOfBombs);
    }

    private void placeBombs(int numberOfBombs) {
        for (int i = 0; i < numberOfBombs; i++) {
            this.grid.put(newEmptyPosition(), true);
        }
    }

    private Pair<Integer, Integer> newEmptyPosition() {
        Pair<Integer, Integer> cell = new Pair<>(random.nextInt(size), random.nextInt(size));
        return this.grid.get(cell) ? newEmptyPosition() : cell;
    }

    private void createGrid(int size) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid.put(new Pair<>(i, j), false);
            }
        }
    }

    @Override
    public boolean hasBomb(int row, int column) {
        return grid.get(new Pair<>(row, column));
    }
}
