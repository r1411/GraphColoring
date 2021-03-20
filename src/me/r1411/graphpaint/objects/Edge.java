package me.r1411.graphpaint.objects;

import java.util.Objects;

public class Edge {
    private Vertex vertexA;
    private Vertex vertexB;

    public Edge(Vertex vertexA, Vertex vertexB) {
        this.vertexA = vertexA;
        this.vertexB = vertexB;
    }

    public Vertex getVertexA() {
        return vertexA;
    }

    public void setVertexA(Vertex vertexA) {
        this.vertexA = vertexA;
    }

    public Vertex getVertexB() {
        return vertexB;
    }

    public void setVertexB(Vertex vertexB) {
        this.vertexB = vertexB;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return getVertexA().equals(edge.getVertexA()) && getVertexB().equals(edge.getVertexB());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getVertexA(), getVertexB());
    }

    @Override
    public String toString() {
        return "Edge{" + vertexA + " -> " + vertexB + "}";
    }
}
