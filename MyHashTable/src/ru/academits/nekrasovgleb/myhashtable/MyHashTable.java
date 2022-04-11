package ru.academits.nekrasovgleb.myhashtable;

import java.util.*;

public class MyHashTable<E> implements Collection<E> {
    private LinkedList<E>[] lists;
    // в переменной size хранится количество объектов, находящихся в хэш-таблице
    private int size;
    // в переменной initialLength хранится размер массива хэш-таблицы по умолчанию
    private final int initialLength = 16;
    /* по аналогии с классом HashSet используется переменная loadFactor для управления отношением между количеством
    объектов в хэш-таблице и размером массива хэш-таблицы - чтобы повысить вероятность наличия в конкретном элементе
    массива хэш-таблицы только одного объекта, тем самым ускорив поиск объектов в хэш-таблице
     */
    private double loadFactor = 0.75;
    // переменная modCount используется в итераторе хэш-таблицы для событий удаления/добавления элементов массива хэш-таблицы
    private int modCount;

    public MyHashTable() {
        //noinspection unchecked
        lists = (LinkedList<E>[]) new LinkedList[initialLength];
    }

    public MyHashTable(int initialLength) {
        if (initialLength < 0) {
            throw new IllegalArgumentException("При создании хэш-таблицы указано отрицательное значение размера массива" +
                    "хэш-таблицы {" + initialLength + "}. Размер массива хэш-таблицы должен быть >= 0");
        }

        //noinspection unchecked
        lists = (LinkedList<E>[]) new LinkedList[initialLength];
    }

    public MyHashTable(int initialLength, double loadFactor) {
        if (initialLength < 0) {
            throw new IllegalArgumentException("При создании хэш-таблицы указано отрицательное значение размера массива" +
                    "хэш-таблицы {" + initialLength + "}. Размер массива хэш-таблицы должен быть >= 0");
        }

        if (loadFactor <= 0) {
            throw new IllegalArgumentException("При создании хэш-таблицы указано не положительное значение коэффициента " +
                    "заполненности {" + loadFactor + "}. Коэффициент заполненности хэш-таблицы должна быть > 0");
        }

        //noinspection unchecked
        lists = (LinkedList<E>[]) new LinkedList[initialLength];
        this.loadFactor = loadFactor;
    }

    public MyHashTable(Collection<? extends E> c) {
        if (c.isEmpty()) {
            throw new NullPointerException("При создании хэш-таблицы передана пустая коллекция. Коллекция должна " +
                    "содержать минимум один объект.");
        }

        int initialLength = getMinNeedLength(c.size(), loadFactor);
        //noinspection unchecked
        lists = (LinkedList<E>[]) new LinkedList[initialLength];

        for (E element : c) {
            int index = getIndex(element, lists.length);
            createListIfNull(index);
            lists[index].add(element);
            ++size;
        }
    }

    // вспомогательный метод getIndex вычисляет индекс в массиве хэш-таблицы, по которому лежит объект
    private int getIndex(Object o, int length) {
        return o != null ? Math.abs(o.hashCode() % length) : 0;
    }

    // вспомогательный метод getMinNeedLength вычисляет минимальный необходимый размер массива хэш-таблицы
    private int getMinNeedLength(int size, double loadFactor) {
        return (int) Math.ceil(size / loadFactor);
    }

    // вспомогательный метод createListIfNull создает список по индексу в массиве хэш-таблицы, если он не был создан ранее
    private void createListIfNull(int index) {
        if (lists[index] == null) {
            lists[index] = new LinkedList<>();
        }
    }

    private void increaseLength(int newLength) {
        //noinspection unchecked
        LinkedList<E>[] tempLists = (LinkedList<E>[]) new LinkedList[newLength];

        for (E element : this) {
            int index = getIndex(element, newLength);

            if (tempLists[index] == null) {
                tempLists[index] = new LinkedList<>();
            }

            tempLists[index].add(element);
        }

        lists = Arrays.copyOf(tempLists, newLength);
        ++modCount;
    }

    private class MyHashTableIterator implements Iterator<E> {
        private int currentIndex;
        /* поле gotElementsCount хранит количество элементов в списке, по которым итератор уже прошелся
        за счет этого при следующем вызове итератор продолжает идти в этом списке с того места, где он остановился
        при прошлом вызовет
         */
        private int gotElementsCount;
        private final int initialModCount = modCount;

        @Override
        public boolean hasNext() {
            if (lists[currentIndex] != null) {
                Iterator<E> listIterator = lists[currentIndex].iterator();

                for (int i = 0; i < gotElementsCount; ++i) {
                    listIterator.next();
                }

                if (listIterator.hasNext()) {
                    return true;
                }
            }

            for (int i = currentIndex + 1; i < lists.length; ++i) {
                if (lists[i] != null) {
                    Iterator<E> listIterator = lists[i].iterator();

                    if (listIterator.hasNext()) {
                        return true;
                    }
                }
            }

            return false;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException("В хэш-таблице больше нет объектов.");
            }

            if (initialModCount != modCount) {
                throw new ConcurrentModificationException("В процессе обхода хэш-таблицы изменился массив " +
                        "хэш-таблицы.");
            }

            if (lists[currentIndex] != null) {
                Iterator<E> listIterator = lists[currentIndex].iterator();

                for (int i = 0; i < gotElementsCount; ++i) {
                    listIterator.next();
                }

                if (listIterator.hasNext()) {
                    E currentElement = listIterator.next();
                    ++gotElementsCount;
                    return currentElement;
                }
            }

            gotElementsCount = 0;
            ++currentIndex;

            for (; currentIndex < lists.length; ++currentIndex) {
                if (lists[currentIndex] != null) {
                    Iterator<E> listIterator = lists[currentIndex].iterator();

                    if (listIterator.hasNext()) {
                        E currentElement = listIterator.next();
                        ++gotElementsCount;
                        return currentElement;
                    }
                }
            }

            throw new NoSuchElementException("В хэш-таблице больше нет объектов.");
        }

        @Override
        public void remove() {
            if (!hasNext()) {
                throw new NoSuchElementException("В хэш-таблице больше нет объектов.");
            }

            if (lists[currentIndex] != null) {
                --gotElementsCount;
                Iterator<E> listIterator = lists[currentIndex].iterator();

                for (int i = 0; i < gotElementsCount; ++i) {
                    listIterator.next();
                }

                if (listIterator.hasNext()) {
                    listIterator.next();
                    listIterator.remove();
                    --size;
                    return;
                }
            }

            ++currentIndex;
            gotElementsCount = 0;

            for (; currentIndex < lists.length; ++currentIndex) {
                if (lists[currentIndex] != null) {
                    Iterator<E> listIterator = lists[currentIndex].iterator();

                    if (listIterator.hasNext()) {
                        listIterator.next();
                        listIterator.remove();
                        --size;
                        return;
                    }
                }
            }
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new MyHashTableIterator();
    }

    @Override
    public boolean isEmpty() {
        if (lists.length == 0) {
            return true;
        }

        return !iterator().hasNext();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean add(E element) {
        if (getMinNeedLength(size + 1, loadFactor) > lists.length) {
            if (lists.length == 0) {
                increaseLength(initialLength);
            } else {
                increaseLength(lists.length * 2);
            }
        }

        int index = getIndex(element, lists.length);
        createListIfNull(index);
        lists[index].add(element);
        ++size;
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        if (c.isEmpty()) {
            return false;
        }

        int minNeedLength = getMinNeedLength(size + c.size(), loadFactor);

        if (minNeedLength > lists.length) {
            increaseLength(minNeedLength);
        }

        for (E element : c) {
            add(element);
        }

        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (size == 0) {
            return false;
        }

        int index = getIndex(o, lists.length);

        if (lists[index] == null) {
            return false;
        } else {
            boolean isRemovedElement = lists[index].remove(o);

            if (isRemovedElement) {
                --size;
                return true;
            }

            return false;
        }
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if (size == 0) {
            return false;
        }

        if (c.isEmpty()) {
            return false;
        }

        int initialSize = size;

        for (Object element : c) {
            int index = getIndex(element, lists.length);

            if (lists[index] != null) {
                int initialListSize = lists[index].size();
                lists[index].removeAll(Collections.singleton(element));
                size -= initialListSize - lists[index].size();
            }
        }

        return initialSize != size;
    }

    @Override
    public void clear() {
        if (size == 0) {
            return;
        }

        while (iterator().hasNext()) {
            iterator().remove();
        }
    }

    @Override
    public boolean contains(Object o) {
        if (size == 0) {
            return false;
        }

        int index = getIndex(o, lists.length);

        if (lists[index] != null) {
            for (E element : lists[getIndex(o, lists.length)]) {
                if (Objects.equals(element, o)) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        if (size == 0) {
            return false;
        }

        if (c.isEmpty()) {
            return true;
        }

        for (Object collectionElement : c) {
            boolean hasCollectionElement = contains(collectionElement);

            if (!hasCollectionElement) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        if (size == 0) {
            return false;
        }

        if (c.isEmpty()) {
            throw new NullPointerException("При вызове метода retainAll в качестве аргумента передана пустая коллекция." +
                    " Коллекция должна содержать хотя бы один объект.");
        }

        boolean isRemovedElement = false;

        for (Iterator<E> i = iterator(); i.hasNext(); ) {
            if (!c.contains(i.next())) {
                i.remove();
                isRemovedElement = true;
            }
        }

        return isRemovedElement;
    }

    @Override
    public Object[] toArray() {
        if (size == 0) {
            return new Object[0];
        }

        Object[] destinationArray = new Object[size];

        int i = 0;

        for (E element : this) {
            destinationArray[i] = element;
            ++i;
        }

        return destinationArray;
    }

    public <T> T[] toArray(T[] array) {
        if (size == 0) {
            return array;
        }

        if (array.length >= size) {
            int i = 0;

            for (E element : this) {
                //noinspection unchecked
                array[i] = (T) element;
                ++i;
            }

            return array;
        }

        T[] destinationArray = Arrays.copyOf(array, size);

        int i = 0;

        for (E element : this) {
            //noinspection unchecked
            destinationArray[i] = (T) element;
            ++i;
        }

        return destinationArray;
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "[]";
        }

        StringBuilder stringBuilder = new StringBuilder("[");

        for (LinkedList<E> list : lists) {
            stringBuilder
                    .append(list)
                    .append(", ");
        }

        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        stringBuilder.append("]");

        return stringBuilder.toString();
    }
}