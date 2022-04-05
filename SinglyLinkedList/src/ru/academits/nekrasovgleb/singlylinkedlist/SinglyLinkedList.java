package ru.academits.nekrasovgleb.singlylinkedlist;

import java.util.NoSuchElementException;
import java.util.Objects;

public class SinglyLinkedList<T> {
    private ListItem<T> head;
    private int size;

    public SinglyLinkedList() {
    }

    public SinglyLinkedList(SinglyLinkedList<T> sourceList) {
        if (sourceList.size == 0) {
            return;
        }

        ListItem<T> sourceListItem = sourceList.head;
        ListItem<T> currentItem = new ListItem<>(sourceList.head.getData());
        head = currentItem;

        while (sourceListItem.getNext() != null) {
            sourceListItem = sourceListItem.getNext();
            ListItem<T> nextItem = new ListItem<>(sourceListItem.getData());
            currentItem.setNext(nextItem);
            currentItem = nextItem;
        }

        size = sourceList.size;
    }

    public int getSize() {
        return size;
    }

    private void checkIsNotEmpty() {
        if (size == 0) {
            throw new NoSuchElementException("Список пустой.");
        }
    }

    private void checkIndex(int index) {
        if (size == 0) {
            throw new IndexOutOfBoundsException("Была попытка обращения по индексу {" + index + "} к элементу пустого списка.");
        }

        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("При операции над элементом списка передан недопустимый индекс элемента:" +
                    " {" + index + "}. Индекс элемента должен быть в интервале от 0 до " + (size - 1));
        }
    }

    private ListItem<T> getItem(int index) {
        checkIndex(index);

        ListItem<T> item = head;

        for (int i = 0; i < index; ++i) {
            item = item.getNext();
        }

        return item;
    }

    public T getFirst() {
        checkIsNotEmpty();

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
        ++size;
    }

    public void add(int index, T data) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("При вставке элемента в список передан недопустимый индекс элемента: " +
                    "{" + index + "}. Индекс элемента должен быть в интервале от 0 до " + size);
        }

        if (index == 0) {
            addFirst(data);
            return;
        }

        ListItem<T> previousItem = getItem(index - 1);
        previousItem.setNext(new ListItem<>(data, previousItem.getNext()));
        ++size;
    }

    public T removeFirst() {
        checkIsNotEmpty();

        T removedData = head.getData();
        head = head.getNext();
        --size;

        return removedData;
    }

    public T remove(int index) {
        checkIndex(index);

        if (index == 0) {
            return removeFirst();
        }

        ListItem<T> previousItem = getItem(index - 1);
        ListItem<T> currentItem = previousItem.getNext();
        T removedData = currentItem.getData();
        previousItem.setNext(currentItem.getNext());
        --size;

        return removedData;
    }

    public boolean remove(T data) {
        checkIsNotEmpty();

        for (ListItem<T> currentItem = head, previousItem = null;
             currentItem != null;
             previousItem = currentItem, currentItem = currentItem.getNext()) {
            if (Objects.equals(currentItem.getData(), data)) {
                if (previousItem == null) {
                    head = head.getNext();
                } else {
                    previousItem.setNext(currentItem.getNext());
                }

                --size;
                return true;
            }
        }

        return false;
    }

    public void reverse() {
        if (size <= 1) {
            return;
        }

        ListItem<T> previousItem = null;
        ListItem<T> currentItem = head;

        for (int i = 0; i < size; ++i) {
            ListItem<T> nextItem = currentItem.getNext();
            currentItem.setNext(previousItem);
            previousItem = currentItem;
            currentItem = nextItem;
        }

        head = previousItem;
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "[]";
        }

        StringBuilder stringBuilder = new StringBuilder("[");

        for (ListItem<T> item = head; item != null; item = item.getNext()) {
            stringBuilder
                    .append(item.getData())
                    .append(", ");
        }

        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        stringBuilder.append("]");

        return stringBuilder.toString();
    }
}