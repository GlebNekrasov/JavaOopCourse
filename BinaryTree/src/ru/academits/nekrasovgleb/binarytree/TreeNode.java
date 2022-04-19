package ru.academits.nekrasovgleb.binarytree;

class TreeNode<T extends Comparable<T>> {
    private T data;
    private TreeNode<T> left;
    private TreeNode<T> right;

    public TreeNode(T data) {
        this.data = data;
    }

    public TreeNode(T data, TreeNode<T> left, TreeNode<T> right) {
        this.data = data;
        this.left = left;
        this.right = right;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public TreeNode<T> getLeft() {
        return left;
    }

    public void setLeft(TreeNode<T> left) {
        this.left = left;
    }

    public TreeNode<T> getRight() {
        return right;
    }

    public void setRight(TreeNode<T> right) {
        this.right = right;
    }

    public int compare(T data) {
        if (data != null && this.data != null) {
            return data.compareTo(this.data);
        } else if (data == null && this.data != null) {
            return -1;
        } else if (data != null) {
            return 1;
        } else {
            return 0;
        }
    }
}
