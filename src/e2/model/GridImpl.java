package e2.model;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GridImpl implements Grid {

    private final Set<Cell> cellWithBombs = new HashSet<>();
    private final Random random = new Random();
    private final int size;

    public GridImpl(int gridSize, int numberOfBombs) {
        this.size = gridSize;
        this.placeBombs(numberOfBombs);
    }

    private void placeBombs(int numberOfBombs) {
        for (int i = 0; i < numberOfBombs; i++) {
            cellWithBombs.add(getEmptyCell());
        }
    }

    private Cell getEmptyCell() {
        var cell = new Cell(random.nextInt(this.size), random.nextInt(this.size));
        return cellWithBombs.contains(cell) ? getEmptyCell() : cell;
    }

    @Override
    public Collection<Cell> cells() {
        return Stream.iterate(0, i -> i + 1)
                .limit(this.size)
                .flatMap(i -> Stream.iterate(0, j -> j + 1).limit(this.size).map(j -> new Cell(i, j)))
                .collect(Collectors.toList());
    }

    @Override
    public int bombsCount() {
        return this.cellWithBombs.size();
    }

    @Override
    public boolean hasBomb(Cell cell) {
        return this.cellWithBombs.contains(cell);
    }

    @Override
    public Collection<Cell> adjacentCells(Cell cell) {
        return this.cells().stream().filter(cell::isAdjacentTo).toList();
    }

    @Override
    public int adjacentBombs(Cell cell) {
        return (int) this.adjacentCells(cell).stream().filter(this::hasBomb).count();
    }

    @Override
    public int size() {
        return this.size * this.size;
    }
}
