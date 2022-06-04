package ru.academits.nekrasovgleb.mygraph;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Consumer;

public class MyGraph<T> {
    private final T[] verticesData;
    private final Integer[][] edges;
    private final boolean[] visitedVertices;

    public MyGraph (T[] verticesData, Integer[][] edges) {
        if (verticesData == null || verticesData.length == 0) {
            throw new IllegalArgumentException("При создании графа в качестве аргумента передан пустой массив данных, " +
                    "которые содержат вершины графа. Массив данных для вершин графа должен содержать данные как минимум для одной вершины.");
        }

        if (edges.length != verticesData.length) {
            throw new IllegalArgumentException("При создании графа передан массив массивов, описывающий наличие ребер между вершинами, " +
                    "у которого длина не совпадает с количеством вершин в графе. Длина массива массивов должна быть равна количеству вершин.");
        }

        if (!isEdgesMatrixSquare(edges)) {
            throw new IllegalArgumentException("При создании графа был передан массив массивов, описывающий наличие ребер между вершинами," +
                    " с разным количеством строк и столбцов. Количество строк и столбцов в массиве массивов должно совпадать.");
        }

        this.verticesData = Arrays.copyOf(verticesData, verticesData.length);
        this.edges = Arrays.copyOf(edges, edges.length);
        visitedVertices = new boolean[edges.length];
    }

    private boolean isEdgesMatrixSquare(Integer[][] edgesMatrix) {
        for (Integer[] row : edgesMatrix) {
            if (row == null || row.length != edgesMatrix.length) {
                return false;
            }
        }

        return true;
    }

    public void traverseByWidth(Consumer<T> consumer) {
        Arrays.fill(visitedVertices, false);

        for (int i = 0; i < edges.length; ++i) {
            if (!visitedVertices[i]) {
                traverseVertexByWidth(i, consumer);
            }
        }
    }

    private void traverseVertexByWidth(Integer vertexNumber, Consumer<T> consumer) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(vertexNumber);

        while (!queue.isEmpty()) {
            Integer currentVertex = queue.remove();
            consumer.accept(verticesData[currentVertex]);
            visitedVertices[currentVertex] = true;

            for (int i = 0; i < edges.length; ++i) {
                if (i != currentVertex && edges[currentVertex][i] != 0 && !visitedVertices[i] && !queue.contains(i)) {
                    queue.add(i);
                }
            }
        }
    }

    public void traverseByDepth(Consumer<T> consumer) {
        Arrays.fill(visitedVertices, false);

        for (int i = 0; i < edges.length; ++i) {
            if (!visitedVertices[i]) {
                traverseVertexByDepth(i, consumer);
            }
        }
    }

    private void traverseVertexByDepth(Integer vertexNumber, Consumer<T> consumer) {
        Deque<Integer> stack = new LinkedList<>();
        stack.add(vertexNumber);

        while (!stack.isEmpty()) {
            Integer currentVertex = stack.removeLast();
            consumer.accept(verticesData[currentVertex]);
            visitedVertices[currentVertex] = true;

            for (int i = 0; i < edges.length; ++i) {
                if (i != currentVertex && edges[currentVertex][i] != 0 && !visitedVertices[i] && !stack.contains(i)) {
                    stack.addLast(i);
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        for (Integer[] array : edges) {
            stringBuilder
                    .append(Arrays.toString(array))
                    .append(System.lineSeparator());
        }

        return stringBuilder.toString();
    }
}