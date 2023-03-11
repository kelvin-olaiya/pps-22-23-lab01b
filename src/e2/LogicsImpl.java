package e2;

import e2.model.Cell;
import e2.model.Grid;
import e2.model.GridImpl;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class LogicsImpl implements Logics {

    private final Grid grid;
    private final Set<Cell> hitCells = new HashSet<>();
    private final Set<Cell> flaggedCells = new HashSet<>();

    public LogicsImpl(int size, int numberOfBombs) {
        this.grid = new GridImpl(size, numberOfBombs);
    }

    @Override
    public boolean hasBomb(int row, int column) {
        return this.grid.hasBomb(new Cell(row, column));
    }

    @Override
    public boolean hit(int row, int column) {
        var hitCell = new Cell(row, column);
        this.hitCells.add(hitCell);
        if (this.grid.hasBomb(hitCell)) {
            return true;
        } else if (this.grid.adjacentBombs(hitCell) == 0) {
            this.grid.adjacentCells(hitCell).stream()
                    .filter(cell -> !this.hitCells.contains(cell))
                    .forEach(cell -> this.hit(cell.getX(), cell.getY()));
        }
        return false;
    }

    @Override
    public void toggleFlag(int row, int column) {
        var cell = new Cell(row, column);
        if (this.hasFlag(row, column)) {
            this.flaggedCells.remove(cell);
        } else {
            this.flaggedCells.add(cell);
        }
    }

    @Override
    public boolean hasFlag(int row, int column) {
        return this.flaggedCells.contains(new Cell(row, column));
    }

    @Override
    public Optional<Integer> adjacentBombs(int row, int column) {
        var cell = new Cell(row, column);
        return this.hitCells.contains(cell) ? Optional.of(this.grid.adjacentBombs(cell)) : Optional.empty();
    }

    @Override
    public boolean hasWon() {
        return this.hitCells.size() == (this.grid.size() - this.grid.bombsCount());
    }
}
