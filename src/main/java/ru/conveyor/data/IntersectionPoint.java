package ru.conveyor.data;

public class IntersectionPoint {

    private int indexA;
    private int indexB;

    public IntersectionPoint(int indexA, int indexB) throws IllegalArgumentException {
        if (indexA > 0 && indexB > 0) {
            this.indexA = indexA;
            this.indexB = indexB;
        } else {
            throw new IllegalArgumentException("You can make only positive intersection point");
        }
    }

    public int getIndexA() {
        return indexA;
    }

    public int getIndexB() {
        return indexB;
    }

    @Override
    public String toString() {
        return "IntersectionPoint{" +
            "indexA=" + indexA +
            ", indexB=" + indexB +
            '}';
    }
}
