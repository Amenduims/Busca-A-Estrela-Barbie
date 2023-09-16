package com.example.buscas;

import java.util.Objects;


// Classe Node serve para armazenar a posição de uma matriz e o peso da célula

public class Node {
    public int x, y;
    public int g; // custo atual
    public int h; // custo estimado até o destino
    public int weight; // peso da célula
    public Node parent;

    public Node(int x, int y, int weight) {
        this.x = x;
        this.y = y;
        this.g = 0;
        this.h = 0;
        this.weight = weight;
        this.parent = null;
    }

    public int getF() {
        return g + h;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return x == node.x && y == node.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
