package ru.academits.nekrasovgleb.myhashtable;

import java.util.*;

public class MyHashTable<E> implements Collection<E> {
    /**
     * в переменной initialLength хранится размер массива хэш-таблицы по умолчанию
     */
    public static final int INITIAL_LENGTH = 16;

    private LinkedList<E>[] lists;

    /**
     * в переменной size хранится количество объектов, находящихся в хэш-таблице
     */
    private int size;

    /**
     * по аналогии с классом HashSet используется переменная loadFactor для управления отношением между количеством
     * объектов в хэш-таблице и размером массива хэш-таблицы - чтобы повысить вероятность наличия в конкретном элементе
     * массива хэш-таблицы только одного объекта, тем самым ускорив поиск объектов в хэш-таблице
     */
    private double loadFactor = 0.75;

    /**
     * переменная modCount используется в итераторе хэш-таблицы для событий удаления/добавления элементов массива хэш-таблицы
     */
    private int modCount;

    public MyHashTable() {
        //noinspection unchecked
        lists = (LinkedList<E>[]) new LinkedList[INITIAL_LENGTH];
    }

    public MyHashTable(int initialLength) {
        if (initialLength <= 0) {
            throw new IllegalArgumentException("При создании хэш-таблицы указано не положительное значение размера массива" +
                    "хэш-таблицы {" + initialLength + "}. Размер массива хэш-таблицы должен быть > 0");
        }

        //noinspection unchecked
        lists = (LinkedList<E>[]) new LinkedList[initialLength];
    }

    public MyHashTable(int initialLength, double loadFactor) {
        if (initialLength <= 0) {
            throw new IllegalArgumentException("При создании хэш-таблицы указано не положительное значение размера массива" +
                    "хэш-таблицы {" + initialLength + "}. Размер массива хэш-таблицы должен быть > 0");
        }

        if (loadFactor <= 0) {
            throw new IllegalArgumentException("При создании хэш-таблицы указано не положительное значение коэффициента " +
                    "заполненности {" + loadFactor + "}. Коэффициент заполненности хэш-таблицы должен быть > 0");
        }

        //noinspection unchecked
        lists = (LinkedList<E>[]) new LinkedList[initialLength];
        this.loadFactor = loadFactor;
    }

    public MyHashTable(Collection<? extends E> c) {
        if (c.isEmpty()) {
            //noinspection unchecked
            lists = (LinkedList<E>[]) new LinkedList[INITIAL_LENGTH];
            return;
        }

        int initialLength = getMinNeedLength(c.size(), loadFactor);
        //noinspection unchecked
        lists = (LinkedList<E>[]) new LinkedList[initialLength];

        for (E element : c) {
            int index = getIndex(element);
            createListIfNull(index);
            lists[index].add(element);
        }

        size = c.size();
    }

    /**
     * вспомогательный метод getIndex вычисляет индекс в массиве хэш-таблицы размера length, по которому лежит объект
     */
    private static int getIndex(Object o, int length) {
        return o != null ? Math.abs(o.hashCode() % length) : 0;
    }

    /**
     * вспомогательный метод getIndex вычисляет индекс в массиве текущей хэш-таблицы, по которому лежит объект
     */
    private int getIndex(Object o) {
        return o != null ? Math.abs(o.hashCode() % lists.length) : 0;
    }

    /**
     * вспомогательный метод getMinNeedLength вычисляет минимальный необходимый размер массива хэш-таблицы
     */
    private int getMinNeedLength(int size, double loadFactor) {
        return (int) Math.ceil(size / loadFactor);
    }

    /**
     * вспомогательный метод createListIfNull создает список по индексу в массиве хэш-таблицы, если он не был создан ранее
     */
    private void createListIfNull(int index) {
        if (lists[index] == null) {
            lists[index] = new LinkedList<>();
        }
    }

    private void increaseLength(int newLength) {
        //noinspection unchecked
        LinkedList<E>[] newLists = (LinkedList<E>[]) new LinkedList[newLength];

        for (E element : this) {
            int index = getIndex(element, newLength);

            if (newLists[index] == null) {
                newLists[index] = new LinkedList<>();
            }

            newLists[index].add(element);
        }

        lists = newLists;
        ++modCount;
    }

    private class MyHashTableIterator implements Iterator<E> {
        private int currentListIndex;
        private Iterator<E> currentListIterator;

        /**
         * Поле passedElementsCount хранит количество элементов в списке, по которым итератор уже прошелся.
         * За счет этого при следующем вызове итератор продолжает идти в этом списке с того места, где он остановился
         * при прошлом вызове.
         */
        private int passedElementsCount;
        private final int initialModCount = modCount;

        @Override
        public boolean hasNext() {
            return passedElementsCount < size;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException("В хэш-таблице больше нет объектов.");
            }

            if (initialModCount != modCount) {
                throw new ConcurrentModificationException("В процессе обхода хэш-таблицы изменился массив хэш-таблицы.");
            }

            if (passedElementsCount > 0) {
                if (currentListIterator.hasNext()) {
                    ++passedElementsCount;
                    return currentListIterator.next();
                }

                ++currentListIndex;

                while (lists[currentListIndex] == null || lists[currentListIndex].size() == 0) {
                    ++currentListIndex;
                }

                currentListIterator = lists[currentListIndex].iterator();
                ++passedElementsCount;
                return currentListIterator.next();
            }

            while (lists[currentListIndex] == null || lists[currentListIndex].size() == 0) {
                ++currentListIndex;
            }

            currentListIterator = lists[currentListIndex].iterator();
            ++passedElementsCount;
            return currentListIterator.next();
        }

        @Override
        public void remove() {
            if (!hasNext()) {
                throw new NoSuchElementException("В хэш-таблице больше нет объектов.");
            }

            if (passedElementsCount > 0) {
                currentListIterator.remove();
                --size;
                return;
            }

            while (lists[currentListIndex] == null || lists[currentListIndex].size() == 0) {
                ++currentListIndex;
            }

            currentListIterator = lists[currentListIndex].iterator();
            currentListIterator.next();
            currentListIterator.remove();
            --size;
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new MyHashTableIterator();
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean add(E element) {
        if (getMinNeedLength(size + 1, loadFactor) > lists.length) {
            if (lists.length == 0) {
                increaseLength(INITIAL_LENGTH);
            } else {
                increaseLength(lists.length * 2);
            }
        }

        int index = getIndex(element);
        createListIfNull(index);
        lists[index].add(element);
        ++size;
        ++modCount;
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

        int index = getIndex(o);

        if (lists[index] == null || lists[index].isEmpty()) {
            return false;
        }

        if (lists[index].remove(o)) {
            --size;
            ++modCount;
            return true;
        }

        return false;
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
            int index = getIndex(element);

            if (lists[index] != null && !lists[index].isEmpty()) {
                int initialListSize = lists[index].size();
                lists[index].removeAll(Collections.singleton(element));
                size -= initialListSize - lists[index].size();
            }
        }

        if (initialSize != size) {
            ++modCount;
            return true;
        }

        return false;
    }

    @Override
    public void clear() {
        if (size == 0) {
            return;
        }

        for (LinkedList<E> list : lists) {
            if (list != null && !list.isEmpty()) {
                list.clear();
            }
        }

        size = 0;
        ++modCount;
    }

    @Override
    public boolean contains(Object o) {
        if (size == 0) {
            return false;
        }

        int index = getIndex(o, lists.length);

        if (lists[index] == null || lists[index].isEmpty()) {
            return false;
        }

        return lists[index].contains(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object collectionElement : c) {
            if (!contains(collectionElement)) {
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
            clear();
            return true;
        }

        boolean isElementRemoved = false;

        for (Iterator<E> iterator = iterator(); iterator.hasNext(); ) {
            if (!c.contains(iterator.next())) {
                iterator.remove();
                isElementRemoved = true;
            }
        }

        if (isElementRemoved) {
            ++modCount;
            return true;
        }

        return false;
    }

    @Override
    public Object[] toArray() {
        Object[] resultingArray = new Object[size];

        int i = 0;

        for (E element : this) {
            resultingArray[i] = element;
            ++i;
        }

        return resultingArray;
    }

    public <T> T[] toArray(T[] array) {
        if (array.length < size) {
            //noinspection unchecked
            return (T[]) Arrays.copyOf(toArray(), size, array.getClass());
        }

        int i = 0;

        for (E element : this) {
            //noinspection unchecked
            array[i] = (T) element;
            ++i;
        }

        if (array.length > size) {
            array[size] = null;
        }

        return array;
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