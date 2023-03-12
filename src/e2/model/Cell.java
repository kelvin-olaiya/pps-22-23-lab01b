package e2.model;

import e2.Pair;

import java.util.Objects;

public class Cell {

    private final Pair<Integer, Integer> position;

    public Cell(Integer row, Integer column) {
        this.position = new Pair<>(row, column);
    }

    public int getRow() {
        return this.position.getX();
    }

    public int getColumn() {
        return this.position.getY();
    }

    public boolean isAdjacentTo(Cell cell) {
        int x = Math.abs(cell.getRow() - this.getRow());
        int y = Math.abs(cell.getColumn() - this.getColumn());
        return !(x == 0 && y == 0) && x <= 1 && y <= 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cell cell)) return false;
        return position.equals(cell.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position);
    }
}
