package e1.model;

import e1.Pair;

public class Position extends Pair<Integer, Integer> {
    public Position(Integer x, Integer y) {
        super(x, y);
    }

    @Override
    public String toString() {
        return "Position [x=" + this.getX() + ", y=" + this.getY()+ "]";
    }
}
