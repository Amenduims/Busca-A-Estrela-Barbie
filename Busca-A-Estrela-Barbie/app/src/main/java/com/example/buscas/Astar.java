package com.example.buscas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class Astar {
    public static List<Node> findPath(int[][] map, Node start, Node end) {
        PriorityQueue<Node> openSet = new PriorityQueue<>((a, b) -> Integer.compare(a.getF(), b.getF()));
        List<Node> closedSet = new ArrayList<>();

        openSet.add(start);

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();

            if (current.equals(end)) {
                return reconstructPath(current);
            }

            closedSet.add(current);

            for (Node neighbor : getNeighbors(map, current)) {
                if (closedSet.contains(neighbor)) {
                    continue;
                }

                int tentativeG = current.g + neighbor.weight; // Use o peso da célula como custo de movimento

                if (!openSet.contains(neighbor) || tentativeG < neighbor.g) {
                    neighbor.g = tentativeG;
                    neighbor.h = heuristic(neighbor, end);
                    neighbor.parent = current;

                    if (!openSet.contains(neighbor)) {
                        openSet.add(neighbor);
                    }
                }
            }
        }

        return Collections.emptyList(); // Nenhum caminho encontrado
    }

    private static List<Node> getNeighbors(int[][] map, Node node) {
        List<Node> neighbors = new ArrayList<>();
        int x = node.x;
        int y = node.y;

        // restringir movimento na diagonal
        int[] dx = { -1, 1, 0, 0 }; // eixo X
        int[] dy = { 0, 0, -1, 1 }; // eixo Y

        for (int i = 0; i < 4; i++) {
            int newX = x + dx[i];
            int newY = y + dy[i];

            // Verifique se a célula está dentro dos limites do mapa
            if (newX >= 0 && newX < map.length && newY >= 0 && newY < map[0].length) {
                int weight = map[newX][newY]; // Obtenha o peso da célula

                // Verifique se o movimento é possível (não colide com obstáculos)
                if (weight > 0) {
                    neighbors.add(new Node(newX, newY, weight));
                }
            }
        }

        return neighbors;
    }

    private static int heuristic(Node a, Node b) {
        // distância de manhattan
        int dx = Math.abs(a.x - b.x);
        int dy = Math.abs(a.y - b.y);
        return dx + dy;
    }

    private static List<Node> reconstructPath(Node node) {
        List<Node> path = new ArrayList<>();
        while (node != null) {
            path.add(node);
            node = node.parent;
        }
        Collections.reverse(path);
        return path;
    }
}
