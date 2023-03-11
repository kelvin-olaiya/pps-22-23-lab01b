package e2;

import e2.model.Cell;
import e2.model.Grid;
import e2.model.GridImpl;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class LogicsImpl implements Logics {

    private final Grid grid;
    private final Set<Cell> hittedCells = new HashSet<>();

    public LogicsImpl(int size, int numberOfBombs) {
        this.grid = new GridImpl(size, numberOfBombs);
    }

    @Override
    public boolean hasBomb(int row, int column) {
        return this.grid.hasBomb(new Cell(row, column));
    }

    @Override
    public boolean hit(int row, int column) {
        var hittedCell = new Cell(row, column);
        this.hittedCells.add(hittedCell);
        if (this.grid.hasBomb(hittedCell)) {
            return true;
        } else if (this.grid.adjacentBombs(hittedCell) == 0) {
            this.grid.adjacentCells(hittedCell).stream()
                    .filter(cell -> !this.hittedCells.contains(cell))
                    .forEach(cell -> this.hit(cell.getX(), cell.getY()));
        }
        return false;
    }

    @Override
    public void toggleFlag(int row, int column) {
        this.grid.toggleFlag(new Cell(row, column));
    }

    @Override
    public boolean hasFlag(int row, int column) {
        return this.grid.isFlagged(new Cell(row, column));
    }

    @Override
    public Optional<Integer> adjacentBombs(int row, int column) {
        var cell = new Cell(row, column);
        return this.hittedCells.contains(cell) ? Optional.of(this.grid.adjacentBombs(cell)) : Optional.empty();
    }

    @Override
    public boolean hasWon() {
        return this.hittedCells.size() == (this.grid.size() - this.grid.bombsCount());
    }
}
