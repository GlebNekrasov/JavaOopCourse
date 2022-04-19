package ru.academits.nekrasovgleb.binarytree;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Consumer;

public class BinaryTree<T extends Comparable<T>> {
    private TreeNode<T> head;
    private int size;

    public BinaryTree() {
    }

    public int getSize() {
        return size;
    }

    public void add(T data) {
        if (size == 0) {
            head = new TreeNode<>(data);
            ++size;
            return;
        }

        TreeNode<T> comparedNode = head;

        while (true) {
            int comparisonResult = comparedNode.compare(data);

            if (comparisonResult < 0) {
                if (comparedNode.getLeft() != null) {
                    comparedNode = comparedNode.getLeft();
                    continue;
                }

                comparedNode.setLeft(new TreeNode<>(data));
                ++size;
                break;
            }

            if (comparedNode.getRight() != null) {
                comparedNode = comparedNode.getRight();
                continue;
            }

            comparedNode.setRight(new TreeNode<>(data));
            ++size;
            break;
        }
    }

    public boolean contains(T data) {
        if (size == 0) {
            return false;
        }

        TreeNode<T> comparedNode = head;

        while (true) {
            int comparisonResult = comparedNode.compare(data);

            if (comparisonResult == 0) {
                return true;
            }

            if (comparisonResult < 0) {
                if (comparedNode.getLeft() != null) {
                    comparedNode = comparedNode.getLeft();
                    continue;
                }

                return false;
            }

            if (comparedNode.getRight() != null) {
                comparedNode = comparedNode.getRight();
                continue;
            }

            return false;
        }
    }

    public void traverseByWidth(Consumer<T> consumer) {
        if (size == 0) {
            return;
        }

        Queue<TreeNode<T>> traverseQueue = new LinkedList<>();

        traverseQueue.add(head);

        while (!traverseQueue.isEmpty()) {
            TreeNode<T> currentNode = traverseQueue.remove();
            consumer.accept(currentNode.getData());

            if (currentNode.getLeft() != null) {
                traverseQueue.add(currentNode.getLeft());
            }

            if (currentNode.getRight() != null) {
                traverseQueue.add(currentNode.getRight());
            }
        }
    }

    public void traverseByDepth(Consumer<T> consumer) {
        if (size == 0) {
            return;
        }

        Deque<TreeNode<T>> traverseStack = new LinkedList<>();

        traverseStack.add(head);

        while (!traverseStack.isEmpty()) {
            TreeNode<T> currentNode = traverseStack.removeLast();
            consumer.accept(currentNode.getData());

            if (currentNode.getRight() != null) {
                traverseStack.addLast(currentNode.getRight());
            }

            if (currentNode.getLeft() != null) {
                traverseStack.addLast(currentNode.getLeft());
            }
        }
    }

    // visit - вспомогательная функция для функции обхода дерева в глубину с рекурсией
    private void visit(TreeNode<T> node, Consumer<T> consumer) {
        consumer.accept(node.getData());

        if (node.getLeft() != null) {
            visit(node.getLeft(), consumer);
        }

        if (node.getRight() != null) {
            visit(node.getRight(), consumer);
        }
    }

    public void traverseByDepthRecursion(Consumer<T> consumer) {
        if (size == 0) {
            return;
        }

        visit(head, consumer);
    }

    private enum ChildDirection {LEFT, RIGHT}

    private void removeChild(TreeNode<T> parentNode, ChildDirection direction) {
        if (direction == ChildDirection.LEFT) {
            parentNode.setLeft(null);
        } else {
            parentNode.setRight(null);
        }
    }

    private void changeChild(TreeNode<T> parent, ChildDirection direction, TreeNode<T> newChild) {
        if (direction == ChildDirection.LEFT) {
            parent.setLeft(newChild);
        } else {
            parent.setRight(newChild);
        }
    }

    public boolean remove(T data) {
        if (size == 0) {
            return false;
        }

        TreeNode<T> compared = head;
        TreeNode<T> comparedParent = null;
        ChildDirection comparedDirection = ChildDirection.LEFT;

        while (true) {
            int comparisonResult = compared.compare(data);

            if (comparisonResult == 0) {
                break;
            }

            if (comparisonResult < 0) {
                if (compared.getLeft() != null) {
                    comparedParent = compared;
                    compared = compared.getLeft();
                    comparedDirection = ChildDirection.LEFT;
                    continue;
                }

                return false;
            }

            if (compared.getRight() != null) {
                comparedParent = compared;
                compared = compared.getRight();
                comparedDirection = ChildDirection.RIGHT;
                continue;
            }

            return false;
        }

        if (compared.getLeft() == null && compared.getRight() == null) {
            if (comparedParent == null) {
                head = null;
                --size;
                return true;
            }

            removeChild(comparedParent, comparedDirection);
            compared.setData(null); //освобождаем память от ненужных данных
            --size;
            return true;
        }

        if (compared.getLeft() != null && compared.getRight() == null) {
            if (comparedParent == null) {
                head = head.getLeft();
                --size;
                return true;
            }

            changeChild(comparedParent, comparedDirection, compared.getLeft());
            compared.setData(null);
            --size;
            return true;
        }

        if (compared.getLeft() == null && compared.getRight() != null) {
            if (comparedParent == null) {
                head = head.getRight();
                --size;
                return true;
            }

            changeChild(comparedParent, comparedDirection, compared.getRight());
            compared.setData(null);
            --size;
            return true;
        }

        // если у удаляемого узла два ребенка, то ищем узел, который поставим взамен удаляемого узла - insteadCompared
        if (compared.getLeft() != null && compared.getRight() != null) {
            TreeNode<T> insteadCompared = compared.getRight();
            TreeNode<T> insteadComparedParent = compared;
            ChildDirection insteadComparedDirection = ChildDirection.RIGHT;

            while (insteadCompared.getLeft() != null) {
                insteadComparedParent = insteadCompared;
                insteadCompared = insteadCompared.getLeft();
                insteadComparedDirection = ChildDirection.LEFT;
            }

            removeChild(insteadComparedParent, insteadComparedDirection);

            if (insteadCompared.getRight() != null) {
                changeChild(insteadComparedParent, insteadComparedDirection, insteadCompared.getRight());
            }

            if (comparedParent == null) {
                head = insteadCompared;
            } else {
                changeChild(comparedParent, comparedDirection, insteadCompared);
            }

            insteadCompared.setLeft(compared.getLeft());
            insteadCompared.setRight(compared.getRight());
            --size;
            return true;
        }

        return false;
    }
}