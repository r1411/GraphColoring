package me.r1411.graphpaint.objects;

import java.util.Objects;

public class Vertex {
    private int index;
    private int color;

    public Vertex(int index) {
        this.index = index;
        this.color = -1;
    }

    public Vertex(int index, int color) {
        this.index = index;
        this.color = color;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return getIndex() == vertex.getIndex();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIndex());
    }

    @Override
    public String toString() {
        return "v" + this.getIndex() + "[" + this.getColor() + "]";
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void clearColor() {
        this.setColor(-1);
    }

    public boolean isColorized() {
        return this.getColor() != -1;
    }
}
