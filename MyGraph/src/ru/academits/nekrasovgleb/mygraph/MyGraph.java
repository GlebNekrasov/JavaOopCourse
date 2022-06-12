package ru.academits.nekrasovgleb.mygraph;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Consumer;

public class MyGraph<T> {
    private final T[] vertices;
    private final int[][] edges;

    public MyGraph(T[] vertices, int[][] edges) {
        if (vertices == null) {
            throw new NullPointerException("При создании графа передан объект null в качестве аргумента, который должен " +
                    "содержать данные вершин графа. Этот аргумент должен содержать данные как минимум для одной вершины.");
        }

        if (vertices.length == 0) {
            throw new IllegalArgumentException("При создании графа передан массив длины 0 в качестве аргумента, " +
                    "который должен содержать данные вершин графа. Массив данных для вершин графа должен содержать данные " +
                    "как минимум для одной вершины.");
        }

        if (edges.length != vertices.length) {
            throw new IllegalArgumentException("При создании графа передан массив длины " + vertices.length +
                    ", который содержит данные вершин графа, и передан массив массивов длины " + edges.length +
                    ", который описывает наличие ребер между вершинами. Длины этих массивов должны быть равны.");
        }

        if (!isEdgesMatrixSquare(edges)) {
            throw new IllegalArgumentException("При создании графа был передан массив массивов, описывающий наличие ребер между вершинами," +
                    " с разным количеством строк и столбцов. Количество строк и столбцов в массиве массивов должно совпадать.");
        }

        this.vertices = Arrays.copyOf(vertices, vertices.length);
        this.edges = Arrays.copyOf(edges, edges.length);
    }

    private boolean isEdgesMatrixSquare(int[][] edgesMatrix) {
        for (int[] row : edgesMatrix) {
            if (row == null || row.length != edgesMatrix.length) {
                return false;
            }
        }

        return true;
    }

    public void traverseByWidth(Consumer<T> consumer) {
        final boolean[] visited = new boolean[edges.length];

        for (int i = 0; i < edges.length; ++i) {
            if (!visited[i]) {
                Queue<Integer> queue = new LinkedList<>();
                queue.add(i);

                while (!queue.isEmpty()) {
                    Integer vertexIndex = queue.remove();

                    if (!visited[vertexIndex]) {
                        consumer.accept(vertices[vertexIndex]);
                        visited[vertexIndex] = true;
                    }

                    for (int j = 0; j < edges.length; ++j) {
                        if (j != vertexIndex && edges[vertexIndex][j] != 0 && !visited[j]) {
                            queue.add(j);
                        }
                    }
                }
            }
        }
    }

    public void traverseByDepth(Consumer<T> consumer) {
        final boolean[] visited = new boolean[edges.length];

        for (int i = 0; i < edges.length; ++i) {
            if (!visited[i]) {
                Deque<Integer> stack = new LinkedList<>();
                stack.add(i);

                while (!stack.isEmpty()) {
                    Integer vertexIndex = stack.removeLast();

                    if (!visited[vertexIndex]) {
                        consumer.accept(vertices[vertexIndex]);
                        visited[vertexIndex] = true;
                    }

                    for (int j = edges.length - 1; j >= 0; --j) {
                        if (j != vertexIndex && edges[vertexIndex][j] != 0 && !visited[j]) {
                            stack.addLast(j);
                        }
                    }
                }
            }
        }
    }

    // visit - вспомогательная функция для функции обхода графа в глубину с рекурсией
    private void visit(int vertexIndex, boolean[] visited, Consumer<T> consumer) {
        if (!visited[vertexIndex]) {
            consumer.accept(vertices[vertexIndex]);
            visited[vertexIndex] = true;
        }

        for (int i = 0; i < edges.length; ++i) {
            if (i != vertexIndex && edges[vertexIndex][i] != 0 && !visited[i]) {
                visit(i, visited, consumer);
            }
        }
    }

    public void traverseByDepthRecursion(Consumer<T> consumer) {
        final boolean[] visited = new boolean[edges.length];

        for (int i = 0; i < vertices.length; ++i) {
            if (!visited[i]) {
                visit(i, visited, consumer);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        for (int[] array : edges) {
            stringBuilder
                    .append(Arrays.toString(array))
                    .append(System.lineSeparator());
        }

        return stringBuilder.toString();
    }
}