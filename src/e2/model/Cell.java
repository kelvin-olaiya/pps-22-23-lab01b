package e2.model;

import e2.Pair;

public class Cell extends Pair<Integer, Integer> {
    public Cell(Integer row, Integer column) {
        super(row, column);
    }

    public boolean isAdjacentTo(Cell cell) {
        int x = Math.abs(cell.getX() - this.getX());
        int y = Math.abs(cell.getY() - this.getY());
        return !(x == 0 && y == 0) && x <= 1 && y <= 1;
    }
}
