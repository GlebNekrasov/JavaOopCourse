package ru.academits.nekrasovgleb.singlylinkedlist;

import java.util.NoSuchElementException;

public class SinglyLinkedList<T> {
    private ListItem<T> head;
    private int count;

    public SinglyLinkedList() {
    }

    public SinglyLinkedList(SinglyLinkedList<T> sourceList) {
        if (sourceList.count == 0) {
            throw new NullPointerException("При копировании списка в качестве аргумента был передан пустой список.");
        }

        ListItem<T> sourceListItem = sourceList.head;
        ListItem<T> currentItem = new ListItem<>(sourceList.head.getData());
        head = currentItem;

        for (int i = 1; i < sourceList.count; ++i) {
            sourceListItem = sourceListItem.getNext();
            ListItem<T> nextItem = new ListItem<>(sourceListItem.getData());
            currentItem.setNext(nextItem);
            currentItem = nextItem;
        }

        count = sourceList.getSize();
    }

    public int getSize() {
        return count;
    }

    private void checkListNotEmpty() {
        if (count == 0) {
            throw new NoSuchElementException("Список пустой.");
        }
    }

    private void checkIndexNotOutOfBounds(int index) {
        if (getSize() == 0) {
            throw new IndexOutOfBoundsException("Была попытка обращения по индексу к элементу пустого списка.");
        }

        if (index < 0 || index >= count) {
            throw new IndexOutOfBoundsException("При операции над элементом списка передан недопустимый индекс элемента:" +
                    " {" + index + "}. Индекс элемента должен быть в интервале от 0 до " + (count - 1));
        }
    }

    private ListItem<T> getItem(int index) {
        checkIndexNotOutOfBounds(index);
        ListItem<T> item = head;

        for (int i = 0; i < index; ++i) {
            item = item.getNext();
        }

        return item;
    }

    public T getFirst() {
        checkListNotEmpty();
        return head.getData();
    }

    public T get(int index) {
        return getItem(index).getData();
    }

    public T set(int index, T data) {
        ListItem<T> item = getItem(index);
        T oldData = item.getData();
        item.setData(data);
        return oldData;
    }

    public void addFirst(T data) {
        head = new ListItem<>(data, head);
        ++count;
    }

    public void add(int index, T data) {
        if (index < 0 || index > count) {
            throw new IndexOutOfBoundsException("При вставке элемента в список передан недопустимый индекс элемента: " +
                    "{" + index + "}. Индекс элемента должен быть в интервале от 0 до " + count);
        }

        if (index == 0) {
            addFirst(data);
            return;
        }

        ListItem<T> previousItem = getItem(index - 1);
        ListItem<T> currentItem = previousItem.getNext();
        ListItem<T> newItem = new ListItem<>(data, currentItem);
        previousItem.setNext(newItem);
        ++count;
    }

    public T removeFirst() {
        checkListNotEmpty();

        T oldData = head.getData();
        head = head.getNext();
        --count;

        return oldData;
    }

    public T remove(int index) {
        checkIndexNotOutOfBounds(index);

        if (index == 0) {
            return removeFirst();
        }

        ListItem<T> previousItem = getItem(index - 1);
        ListItem<T> currentItem = previousItem.getNext();
        T oldData = currentItem.getData();
        previousItem.setNext(currentItem.getNext());
        --count;

        return oldData;
    }

    public boolean remove(T data) {
        checkListNotEmpty();
        ListItem<T> currentItem = head;
        ListItem<T> previousItem = null;

        for (int i = 0; i < count; ++i) {
            if (currentItem.getData().equals(data)) {
                if (previousItem == null) {
                    head = head.getNext();
                } else {
                    previousItem.setNext(currentItem.getNext());
                }

                --count;
                return true;
            }

            previousItem = currentItem;
            currentItem = currentItem.getNext();
        }

        return false;
    }

    public void reverse() {
        if (count <= 1) {
            return;
        }

        ListItem<T> previousItem = null;
        ListItem<T> currentItem = head;
        ListItem<T> nextItem;

        for (int i = 0; i < count; ++i) {
            nextItem = currentItem.getNext();
            currentItem.setNext(previousItem);
            previousItem = currentItem;
            currentItem = nextItem;
        }

        head = previousItem;
    }

    @Override
    public String toString() {
        if (count == 0) {
            return "[]";
        }

        StringBuilder stringBuilder = new StringBuilder("[");
        ListItem<T> item = head;

        for (int i = 0; i < count; ++i) {
            stringBuilder
                    .append(item.getData())
                    .append(", ");
            item = item.getNext();
        }

        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        stringBuilder.append("]");

        return stringBuilder.toString();
    }
}