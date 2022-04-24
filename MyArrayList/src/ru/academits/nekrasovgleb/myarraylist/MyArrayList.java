package ru.academits.nekrasovgleb.myarraylist;

import java.util.*;

public class MyArrayList<E> implements List<E> {
    public static final int DEFAULT_CAPACITY = 10;

    private E[] items;
    private int size;
    private int modCount;

    public MyArrayList() {
        //noinspection unchecked
        items = (E[]) new Object[DEFAULT_CAPACITY];
    }

    public MyArrayList(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("При создании списка указано отрицательное значение вместимости списка {" +
                    capacity + "}. Вместимость списка должна быть >= 0");
        }

        //noinspection unchecked
        items = (E[]) new Object[capacity];
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

    @Override
    public E get(int index) {
        checkIndex(index);

        return items[index];
    }

    @Override
    public E set(int index, E item) {
        checkIndex(index);

        E oldItem = items[index];
        items[index] = item;
        return oldItem;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void increaseCapacity() {
        if (items.length == 0) {
            //noinspection unchecked
            items = (E[]) new Object[DEFAULT_CAPACITY];
        } else {
            items = Arrays.copyOf(items, items.length * 2);
        }
    }

    public void ensureCapacity(int capacity) {
        if (items.length < capacity) {
            items = Arrays.copyOf(items, capacity);
        }
    }

    public void trimToSize() {
        if (items.length > size) {
            items = Arrays.copyOf(items, size);
        }
    }

    private class MyArrayListIterator implements Iterator<E> {
        private int currentIndex = -1;
        private final int initialModCount = modCount;

        @Override
        public boolean hasNext() {
            return currentIndex + 1 < size;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException("В списке больше нет элементов.");
            }

            if (initialModCount != modCount) {
                throw new ConcurrentModificationException("В процессе обхода списка список изменился.");
            }

            ++currentIndex;
            return items[currentIndex];
        }
    }

    public Iterator<E> iterator() {
        return new MyArrayListIterator();
    }

    @Override
    public boolean add(E item) {
        add(size, item);
        return true;
    }

    @Override
    public void add(int index, E item) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("При добавлении элемента в список передан недопустимый индекс элемента:" +
                    " {" + index + "}. Индекс элемента должен быть в интервале от 0 до " + size);
        }

        if (size >= items.length) {
            increaseCapacity();
        }

        if (index < size) {
            System.arraycopy(items, index, items, index + 1, size - index);
        }

        items[index] = item;
        ++size;
        ++modCount;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return addAll(size, c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("При добавлении коллекции в список передан недопустимый индекс элемента:" +
                    " {" + index + "}. Индекс элемента должен быть в интервале от 0 до " + size);
        }

        if (c.isEmpty()) {
            return false;
        }

        ensureCapacity(size + c.size());

        if (index < size) {
            System.arraycopy(items, index, items, index + c.size(), size - index);
        }

        int i = index;

        for (E item : c) {
            items[i] = item;
            ++i;
        }

        size += c.size();
        ++modCount;
        return true;
    }

    @Override
    public void clear() {
        if (size == 0) {
            return;
        }

        for (int i = 0; i < size; ++i) {
            items[i] = null;
        }

        size = 0;
        ++modCount;
    }

    @Override
    public E remove(int index) {
        checkIndex(index);

        E removedItem = items[index];

        if (index < size - 1) {
            System.arraycopy(items, index + 1, items, index, size - index - 1);
        }

        items[size - 1] = null;
        --size;
        ++modCount;
        return removedItem;
    }

    @Override
    public boolean remove(Object o) {
        int removedItemIndex = indexOf(o);

        if (removedItemIndex == -1) {
            return false;
        }

        remove(removedItemIndex);
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if (c.isEmpty()) {
            return false;
        }

        boolean isItemRemoved = false;

        for (int i = size - 1; i >= 0; --i) {
            if (c.contains(items[i])) {
                remove(i);
                isItemRemoved = true;
            }
        }

        return isItemRemoved;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean isItemRemoved = false;

        for (int i = size - 1; i >= 0; --i) {
            if (!c.contains(items[i])) {
                remove(i);
                isItemRemoved = true;
            }
        }

        return isItemRemoved;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) != -1;
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        for (Object o : collection) {
            if (!contains(o)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size; ++i) {
            if (Objects.equals(items[i], o)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = size - 1; i >= 0; --i) {
            if (Objects.equals(items[i], o)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(items, size);
    }

    @Override
    public <T> T[] toArray(T[] array) {
        if (array.length < size) {
            //noinspection unchecked
            return (T[]) Arrays.copyOf(items, size, array.getClass());
        }

        //noinspection SuspiciousSystemArraycopy
        System.arraycopy(items, 0, array, 0, size);

        if (array.length > size) {
            array[size] = null;
        }

        return array;
    }

    @Override
    public MyArrayList<E> subList(int fromIndex, int toIndex) {
        //noinspection ConstantConditions
        return null;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int hash = 1;

        for (int i = 0; i < size; ++i) {
            hash = prime * hash + (items[i] != null ? items[i].hashCode() : 0);
        }

        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || o.getClass() != getClass()) {
            return false;
        }

        MyArrayList<?> list = (MyArrayList<?>) o;

        if (list.size != size) {
            return false;
        }

        for (int i = 0; i < size; ++i) {
            if (!Objects.equals(items[i], list.items[i])) {
                return false;
            }
        }

        return true;
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "[]";
        }

        StringBuilder stringBuilder = new StringBuilder("[");

        for (int i = 0; i < size; ++i) {
            stringBuilder
                    .append(items[i])
                    .append(", ");
        }

        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        stringBuilder.append("]");

        return stringBuilder.toString();
    }

    @Override
    public ListIterator<E> listIterator() {
        //noinspection ConstantConditions
        return null;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        //noinspection ConstantConditions
        return null;
    }
}