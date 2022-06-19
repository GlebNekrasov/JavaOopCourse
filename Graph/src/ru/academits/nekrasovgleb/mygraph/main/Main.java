package ru.academits.nekrasovgleb.mygraph.main;

import ru.academits.nekrasovgleb.mygraph.Graph;

import java.util.function.Consumer;

public class Main {
    public static void main(String[] args) {
        String[] data = {"1-ый", "2-ой", "3-ий", "4-ый", "5-ый", "6-ой", "7-ой", "8-ой", "9-ый", "10-ый", "11-ый"};

        int[][] edges = {
                {0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {1, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0},
                {0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0},
                {1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        };

        Graph<String> graph1 = new Graph<>(data, edges);
        System.out.println("Создан граф со следующей матрицей ребер между вершинами:");
        System.out.println(graph1);

        Consumer<String> printToConsole = x -> System.out.print(x + ", ");

        System.out.println("Список элементов графа при обходе графа в ширину:");
        graph1.traverseByWidth(printToConsole);
        System.out.println();

        System.out.println("Список элементов графа при обходе графа в глубину:");
        graph1.traverseByDepth(printToConsole);
        System.out.println();

        System.out.println("Список элементов графа при обходе графа в глубину с рекурсией:");
        graph1.traverseByDepthRecursion(printToConsole);
    }
}