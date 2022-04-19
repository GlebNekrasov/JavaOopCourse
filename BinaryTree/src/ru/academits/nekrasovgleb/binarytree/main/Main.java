package ru.academits.nekrasovgleb.binarytree.main;

import ru.academits.nekrasovgleb.binarytree.BinaryTree;

import java.util.function.Consumer;

public class Main {
    public static void main(String[] args) {
        BinaryTree<Integer> tree1 = new BinaryTree<>();
        tree1.add(16);
        tree1.add(10);
        tree1.add(20);
        tree1.add(null);
        tree1.add(8);
        tree1.add(9);
        tree1.add(7);
        tree1.add(12);
        tree1.add(27);
        tree1.add(23);
        tree1.add(32);
        tree1.add(21);
        tree1.add(18);

        System.out.println("Создали новое дерево и добавили элементы. Количество элементов в дереве: " + tree1.getSize());
        System.out.println();

        Consumer<Integer> printToConsole = x -> System.out.print(x + ", ");

        System.out.println("Список элементов дерева при обходе дерева в ширину:");
        tree1.traverseByWidth(printToConsole);
        System.out.println();
        System.out.println();

        System.out.println("Список элементов дерева при обходе дерева в глубину:");
        tree1.traverseByDepth(printToConsole);
        System.out.println();
        System.out.println();

        System.out.println("Список элементов дерева при обходе дерева в глубину с рекурсией:");
        tree1.traverseByDepthRecursion(printToConsole);
        System.out.println();
        System.out.println();

        System.out.println("Дерево содержит элемент \"11?\" - " + tree1.contains(11));
        System.out.println();

        boolean isRemoved = tree1.remove(20);
        System.out.println("Из дерева удален узел со значением 20? - " + isRemoved);
        System.out.println("Количество элементов в дереве после удаления элемента: " + tree1.getSize());
        System.out.println();

        System.out.println("Список элементов дерева при обходе дерева в ширину:");
        tree1.traverseByWidth(printToConsole);
        System.out.println();

    }
}
