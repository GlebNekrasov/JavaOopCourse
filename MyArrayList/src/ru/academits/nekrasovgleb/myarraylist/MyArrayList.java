package ru.academits.nekrasovgleb.myarraylist;

import java.util.*;

public class MyArrayList<T> implements List<T> {
    private T[] items;
    private int size;
    private int modCount;

    public MyArrayList() {
        //noinspection unchecked
        items = (T[]) new Object[10];
    }

    public MyArrayList(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("При создании списка указано отрицательное значение вместимости списка." +
                    "Вместимость списка должна быть >= 0");
        }

        //noinspection unchecked
        items = (T[]) new Object[capacity];
    }

    private void checkIndexNotOutOfBounds(int index) {
        if (size == 0) {
            throw new IndexOutOfBoundsException("Была попытка обращения по индексу к элементу пустого списка.");
        }

        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("При операции над элементом списка передан недопустимый индекс элемента:" +
                    " {" + index + "}. Индекс элемента должен быть в интервале от 0 до " + (size - 1));
        }
    }

    @Override
    public T get(int index) {
        checkIndexNotOutOfBounds(index);
        return items[index];
    }

    @Override
    public T set(int index, T element) {
        checkIndexNotOutOfBounds(index);
        T oldItem = items[index];
        items[index] = element;
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
            items = (T[]) new Object[10];
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

    private class MyArrayListIterator implements Iterator<T> {
        private int currentIndex = -1;
        private final int currentModCount = modCount;

        @Override
        public boolean hasNext() {
            return currentIndex + 1 < size;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException("В списке нет элемента с индексом " + (currentIndex + 1));
            }

            if (currentModCount != modCount) {
                throw new ConcurrentModificationException("В процессе обхода списка изменилась длина списка.");
            }

            ++currentIndex;
            return items[currentIndex];
        }
    }

    public Iterator<T> iterator() {
        return new MyArrayListIterator();
    }

    @Override
    public boolean add(T element) {
        if (size >= items.length) {
            increaseCapacity();
        }

        items[size] = element;
        ++size;
        ++modCount;
        return true;
    }

    @Override
    public void add(int index, T element) {
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

        items[index] = element;
        ++size;
        ++modCount;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        if (items.length < size + c.size()) {
            ensureCapacity(size + c.size());
        }

        for (T item : c) {
            items[size] = item;
            ++size;
            ++modCount;
        }

        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("При добавлении коллекции в список передан недопустимый индекс элемента:" +
                    " {" + index + "}. Индекс элемента должен быть в интервале от 0 до " + size);
        }

        if (items.length < size + c.size()) {
            ensureCapacity(size + c.size());
        }

        if (index < size) {
            System.arraycopy(items, index, items, index + c.size(), size - index);

            for (T item : c) {
                items[index] = item;
                ++index;
                ++size;
                ++modCount;
            }
        } else {
            for (T item : c) {
                items[size] = item;
                ++size;
                ++modCount;
            }
        }

        return true;
    }

    @Override
    public void clear() {
        size = 0;
        ++modCount;
    }

    @Override
    public T remove(int index) {
        checkIndexNotOutOfBounds(index);
        T removedItem = items[index];

        if (index < size - 1) {
            System.arraycopy(items, index + 1, items, index, size - index - 1);
        }

        --size;
        ++modCount;
        return removedItem;
    }

    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < size; ++i) {
            if (items[i].equals(o)) {
                if (i < size - 1) {
                    System.arraycopy(items, i + 1, items, i, size - i - 1);
                }

                --size;
                ++modCount;
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean isRemovedItem = false;

        for (int index = size - 1; index >= 0; --index) {
            for (Object item : c) {
                if (items[index].equals(item)) {
                    if (index < size - 1) {
                        System.arraycopy(items, index + 1, items, index, size - index - 1);
                    }

                    --size;
                    ++modCount;
                    isRemovedItem = true;
                    break;
                }
            }
        }

        return isRemovedItem;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean isRemovedItem = false;

        for (int i = size - 1; i >= 0; --i) {
            boolean needToRemove = true;

            for (Object item : c) {
                if (items[i].equals(item)) {
                    needToRemove = false;
                    break;
                }
            }

            if (needToRemove) {
                if (i < size - 1) {
                    System.arraycopy(items, i + 1, items, i, size - i - 1);
                }

                --size;
                ++modCount;
                isRemovedItem = true;
            }
        }

        return isRemovedItem;
    }

    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < size; ++i) {
            if (items[i].equals(o)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        for (Object o : collection) {
            boolean isOInList = false;

            for (int i = 0; i < size; ++i) {
                if (items[i].equals(o)) {
                    isOInList = true;
                    break;
                }
            }

            if (!isOInList) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size; ++i) {
            if (items[i].equals(o)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = size - 1; i >= 0; --i) {
            if (items[i].equals(o)) {
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
    public <S> S[] toArray(S[] array) {
        //noinspection unchecked
        return (S[]) Arrays.copyOf(items, size, array.getClass());
    }

    @Override
    public MyArrayList<T> subList(int fromIndex, int toIndex) {
        checkIndexNotOutOfBounds(fromIndex);
        checkIndexNotOutOfBounds(toIndex);

        if (fromIndex >= toIndex) {
            throw new IllegalArgumentException("При получении части списка передан начальный индекс копирования" +
                    " не меньше, чем конечный индекс копирования.");
        }

        MyArrayList<T> newList = new MyArrayList<>(toIndex - fromIndex);
        System.arraycopy(items, fromIndex, newList.items, 0, toIndex - fromIndex);
        newList.size = toIndex - fromIndex;
        return newList;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int hash = 1;

        for (int i = 0; i < size; ++i) {
            hash = prime * hash + items[i].hashCode();
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

        if (list.get(0).getClass() != get(0).getClass()) {
            return false;
        }

        if (list.size != size) {
            return false;
        }

        for (int i = 0; i < size; ++i) {
            if (items[i] != list.get(i)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public String toString() {
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
    public ListIterator<T> listIterator() {
        Iterator<T> iterator = iterator();
        return (ListIterator<T>) iterator;
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        Iterator<T> iterator = iterator();

        for (int i = 0; i < index; ++i) {
            iterator.next();
        }

        return (ListIterator<T>) iterator;
    }
}