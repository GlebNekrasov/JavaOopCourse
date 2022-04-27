package ru.academits.nekrasovgleb.binarytree;

import java.util.Comparator;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Consumer;

public class BinaryTree<T> {
    private TreeNode<T> root;
    private int size;
    private Comparator<T> treeComparator;

    public BinaryTree() {
    }

    public BinaryTree(Comparator<T> comparator) {
        treeComparator = comparator;
    }

    public int dataCompare(T data1, T data2) {
        if (treeComparator != null) {
            return treeComparator.compare(data1, data2);
        }

        if (data1 != null && data2 != null) {
            //noinspection unchecked
            return ((Comparable<T>) data1).compareTo(data2);
        } else if (data1 == null && data2 != null) {
            return -1;
        } else if (data1 != null) {
            return 1;
        } else {
            return 0;
        }
    }

    public int getSize() {
        return size;
    }

    public void add(T data) {
        if (size == 0) {
            root = new TreeNode<>(data);
            ++size;
            return;
        }

        TreeNode<T> comparedNode = root;

        while (true) {
            int comparisonResult = dataCompare(data, comparedNode.getData());

            if (comparisonResult < 0) {
                if (comparedNode.getLeft() != null) {
                    comparedNode = comparedNode.getLeft();
                    continue;
                }

                comparedNode.setLeft(new TreeNode<>(data));
                ++size;
                return;
            }

            if (comparedNode.getRight() != null) {
                comparedNode = comparedNode.getRight();
                continue;
            }

            comparedNode.setRight(new TreeNode<>(data));
            ++size;
            return;
        }
    }

    public boolean contains(T data) {
        if (size == 0) {
            return false;
        }

        TreeNode<T> comparedNode = root;

        while (true) {
            int comparisonResult = dataCompare(data, comparedNode.getData());

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

        traverseQueue.add(root);

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

        traverseStack.add(root);

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

        visit(root, consumer);
    }

    private enum ChildDirection {LEFT, RIGHT}

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

        TreeNode<T> removedNode = root;
        TreeNode<T> removedNodeParent = null;
        ChildDirection removedNodeDirection = ChildDirection.LEFT;

        while (true) {
            int comparisonResult = dataCompare(data, removedNode.getData());

            if (comparisonResult == 0) {
                break;
            }

            if (comparisonResult < 0) {
                if (removedNode.getLeft() != null) {
                    removedNodeParent = removedNode;
                    removedNode = removedNode.getLeft();
                    removedNodeDirection = ChildDirection.LEFT;
                    continue;
                }

                return false;
            }

            if (removedNode.getRight() != null) {
                removedNodeParent = removedNode;
                removedNode = removedNode.getRight();
                removedNodeDirection = ChildDirection.RIGHT;
                continue;
            }

            return false;
        }

        if (removedNode.getLeft() == null && removedNode.getRight() == null) {
            if (removedNodeParent == null) {
                root = null;
                --size;
                return true;
            }

            changeChild(removedNodeParent, removedNodeDirection, null);
            --size;
            return true;
        }

        if (removedNode.getLeft() != null && removedNode.getRight() == null) {
            if (removedNodeParent == null) {
                root = root.getLeft();
                --size;
                return true;
            }

            changeChild(removedNodeParent, removedNodeDirection, removedNode.getLeft());
            --size;
            return true;
        }

        if (removedNode.getLeft() == null && removedNode.getRight() != null) {
            if (removedNodeParent == null) {
                root = root.getRight();
                --size;
                return true;
            }

            changeChild(removedNodeParent, removedNodeDirection, removedNode.getRight());
            --size;
            return true;
        }

        // если у удаляемого узла два ребенка, то ищем узел, который поставим взамен удаляемого узла - replacedNode
        if (removedNode.getLeft() != null && removedNode.getRight() != null) {
            TreeNode<T> replacedNode = removedNode.getRight();
            TreeNode<T> replacedNodeParent = removedNode;
            ChildDirection replacedNodeDirection = ChildDirection.RIGHT;

            while (replacedNode.getLeft() != null) {
                replacedNodeParent = replacedNode;
                replacedNode = replacedNode.getLeft();
                replacedNodeDirection = ChildDirection.LEFT;
            }

            changeChild(replacedNodeParent, replacedNodeDirection, replacedNode.getRight());

            if (removedNodeParent == null) {
                root = replacedNode;
            } else {
                changeChild(removedNodeParent, removedNodeDirection, replacedNode);
            }

            replacedNode.setLeft(removedNode.getLeft());
            replacedNode.setRight(removedNode.getRight());
            --size;
            return true;
        }

        return false;
    }
}