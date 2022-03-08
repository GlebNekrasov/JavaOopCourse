package ru.academits.nekrasovgleb.singlylinkedlist;

public class SinglyLinkedList<T> {
    private listItem<T> head;
    private int count;

    public SinglyLinkedList() {
    }

    public SinglyLinkedList(SinglyLinkedList<T> sourceList) {
        if (sourceList.count == 0) {
            throw new NullPointerException("При копировании списка в качестве аргумента был передан пустой список.");
        }

        listItem<T> sourceListItem = sourceList.head;
        listItem<T> currentItem = new listItem<>(sourceList.head.getData());
        head = currentItem;
        count = 0;

        for (int i = 0; i < sourceList.count - 1; ++i) {
            sourceListItem = sourceListItem.getNext();
            listItem<T> nextItem = new listItem<>(sourceListItem.getData());
            currentItem.setNext(nextItem);
            currentItem = nextItem;
            ++count;
        }

        currentItem.setNext(null);
        ++count;
    }

    public int getSize() {
        return count;
    }

    private void checkListNotEmpty() {
        if (count == 0) {
            throw new NullPointerException("Была попытка сделать операцию над пустым списком.");
        }
    }

    private void checkIndexNotOutOfBounds(int index) {
        if (index < 0 || index >= count) {
            throw new IndexOutOfBoundsException("При операции над элементом списка передан недопустимый индекс элемента:" +
                    " {" + index + "}. Индекс элемента должен быть в интервале от 0 до " + (count - 1));
        }
    }

    public T getFirstItem() {
        checkListNotEmpty();
        return head.getData();
    }

    public T getItem(int index) {
        checkListNotEmpty();
        checkIndexNotOutOfBounds(index);
        listItem<T> item = head;

        for (int i = 0; i < index; ++i) {
            item = item.getNext();
        }

        return item.getData();
    }

    public void setItem(int index, T data) {
        checkListNotEmpty();
        checkIndexNotOutOfBounds(index);
        listItem<T> item = head;

        for (int i = 0; i < index; ++i) {
            item = item.getNext();
        }

        System.out.println("Старое значение элемента списка с индексом " + index + " равно: " + item.getData());
        item.setData(data);
    }

    public void removeItem(int index) {
        checkListNotEmpty();
        checkIndexNotOutOfBounds(index);
        listItem<T> item = head;
        listItem<T> previousItem = null;

        for (int i = 0; i < index; ++i) {
            previousItem = item;
            item = item.getNext();
        }

        System.out.println("Значение удаленного элемента списка с индексом " + index + " равно: " + item.getData());

        if (previousItem == null) {
            head = head.getNext();
        } else {
            if (item.getNext() != null) {
                previousItem.setNext(item.getNext());
            } else {
                previousItem.setNext(null);
            }
        }

        --count;
    }

    public void addItem(T data) {
        listItem<T> newItem = new listItem<>(data);
        newItem.setNext(head);
        head = newItem;
        ++count;
    }

    public void addItem(T data, int index) {
        if (index < 0 || index > count) {
            throw new IndexOutOfBoundsException("При вставке элемента в список передан недопустимый индекс элемента: " +
                    "{" + index + "}. Индекс элемента должен быть в интервале от 0 до " + count);
        }

        listItem<T> item = head;
        listItem<T> previousItem = null;

        for (int i = 0; i < index; ++i) {
            previousItem = item;
            item = item.getNext();
        }

        if (previousItem == null) {
            head = new listItem<>(data, head);
        } else {
            listItem<T> newItem = new listItem<>(data, item);
            previousItem.setNext(newItem);
        }

        ++count;
    }

    public void removeFirstItem() {
        checkListNotEmpty();
        System.out.println("Значение удаленного первого элемента списка равно: " + head.getData());
        head = head.getNext();
        --count;
    }

    public boolean removeItem(T data) {
        checkListNotEmpty();
        listItem<T> item = head;
        listItem<T> previousItem = null;

        for (int i = 0; i < count; ++i) {
            if (item.getData() == data) {
                if (previousItem == null) {
                    head = head.getNext();
                } else {
                    if (item.getNext() != null) {
                        previousItem.setNext(item.getNext());
                    } else {
                        previousItem.setNext(null);
                    }
                }

                --count;
                return true;
            }

            previousItem = item;
            item = item.getNext();
        }

        return false;
    }

    public void reverse() {
        checkListNotEmpty();

        if (count == 1) {
            return;
        }

        listItem<T> previousItem = null;
        listItem<T> currentItem = head;
        listItem<T> nextItem;

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
        checkListNotEmpty();
        StringBuilder stringBuilder = new StringBuilder("[");
        listItem<T> item = head;

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