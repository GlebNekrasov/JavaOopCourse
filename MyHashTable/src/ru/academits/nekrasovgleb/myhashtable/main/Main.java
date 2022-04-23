package ru.academits.nekrasovgleb.myhashtable.main;

import ru.academits.nekrasovgleb.myhashtable.MyHashTable;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        ArrayList<String> stringsList1 = new ArrayList<>(Arrays.asList("Один", "Два", "Три", "Четыре", "Пять", "Три"));
        System.out.println("Создан список на массиве: " + stringsList1);
        System.out.println();

        MyHashTable<String> hashTable1 = new MyHashTable<>(stringsList1);
        System.out.println("Создана хэш-таблица для списка на массиве. Проверяем, что все элементы списка есть в хэш-таблице:");
        System.out.println(hashTable1);
        System.out.println();

        System.out.println("Хэш-таблица пустая? - " + hashTable1.isEmpty());
        System.out.println();

        System.out.println("Количество элементов в хэш-таблице: " + hashTable1.size());
        System.out.println();

        hashTable1.add("Семь");
        hashTable1.add("Шесть");
        hashTable1.add("Семь");
        hashTable1.add("Восемь");
        hashTable1.add("Пять");
        hashTable1.add("Три");
        System.out.println("В хэш-таблицу добавили еще 6 элементов:");
        System.out.println(hashTable1);
        System.out.println("Количество элементов в хэш-таблице: " + hashTable1.size());
        System.out.println();

        boolean isDeleted1 = hashTable1.remove("Три");
        System.out.println("Из хэш-таблицы удален элемент \"Три\"? - " + isDeleted1);
        System.out.println("Количество элементов в хэш-таблице после удаления: " + hashTable1.size());
        System.out.println("Элементы хэш-таблицы после удаления элемента:");
        System.out.println(hashTable1);
        System.out.println();

        ArrayList<String> stringsList2 = new ArrayList<>(Arrays.asList("Три", "Четыре"));
        boolean isDeleted2 = hashTable1.removeAll(stringsList2);
        System.out.println("Из хэш-таблицы удалена коллекция  " + stringsList2 + "? - " + isDeleted2);
        System.out.println("Количество элементов в хэш-таблице после удаления: " + hashTable1.size());
        System.out.println("Элементы хэш-таблицы после удаления коллекции:");
        System.out.println(hashTable1);
        System.out.println();

        boolean contains1 = hashTable1.contains("Шесть");
        System.out.println("Хэш-таблица содержит элемент \"Шесть\"? - " + contains1);
        System.out.println();

        ArrayList<String> stringsList3 = new ArrayList<>(Arrays.asList("Два", "Пять"));
        boolean contains2 = hashTable1.containsAll(stringsList3);
        System.out.println("Хэш-таблица содержит коллекцию " + stringsList3 + "? - " + contains2);
        System.out.println();

        ArrayList<String> stringsList4 = new ArrayList<>(Arrays.asList("Два", "Три"));
        boolean contains3 = hashTable1.containsAll(stringsList4);
        System.out.println("Хэш-таблица содержит коллекцию " + stringsList4 + "? - " + contains3);
        System.out.println();

        ArrayList<String> stringsList5 = new ArrayList<>(Arrays.asList("Два", "Три", "Четыре", "Пять", "Три"));
        hashTable1.addAll(stringsList5);
        System.out.println("В хэш-таблицу добавили еще 5 элементов:");
        System.out.println(hashTable1);
        System.out.println("Количество элементов в хэш-таблице после добавления элементов: " + hashTable1.size());
        System.out.println();

        ArrayList<String> stringsList6 = new ArrayList<>(Arrays.asList("Один", "Три", "Семь", "Сто", "Пять"));
        boolean isRetained1 = hashTable1.retainAll(stringsList6);
        System.out.println("В хэш-таблице оставлены только элементы коллекции  " + stringsList6 + "? - " + isRetained1);
        System.out.println("Количество элементов в хэш-таблице: " + hashTable1.size());
        System.out.println("Элементы хэш-таблицы:");
        System.out.println(hashTable1);
        System.out.println();

        Object[] array1 = new Object[hashTable1.size()];
        System.arraycopy(hashTable1.toArray(), 0, array1, 0, array1.length);
        System.out.println("Создан массив объектов из хэш-таблицы с помощью метода toArray():");
        System.out.println(Arrays.toString(array1));
        System.out.println();

        String[] array2 = hashTable1.toArray(new String[10]);
        System.out.println("Создан массив строк размером 10 из хэш-таблицы с помощью метода toArray(T[] array):");
        System.out.println(Arrays.toString(array2));
        System.out.println();

        MyHashTable<Integer> hashTable2 = new MyHashTable<>(1, 2);
        hashTable2.add(1);
        hashTable2.add(2);
        System.out.println(hashTable2);
        System.out.println();

        hashTable2.add(3);
        System.out.println(hashTable2);
        System.out.println();

        hashTable2.add(4);
        System.out.println(hashTable2);
        System.out.println();

        hashTable1.clear();
        System.out.println("Хэш-таблица очищена. Элементы хэш-таблицы:");
        System.out.println(hashTable1);
        System.out.println("Количество элементов в хэш-таблице: " + hashTable1.size());
        System.out.println();

        Object[] array3 = new Object[hashTable1.size()];
        System.arraycopy(hashTable1.toArray(), 0, array3, 0, array3.length);
        System.out.println("Создан массив объектов из хэш-таблицы с помощью метода toArray():");
        System.out.println(Arrays.toString(array3));
        System.out.println();

        String[] array4 = hashTable1.toArray(new String[10]);
        System.out.println("Создан массив строк размером 10 из хэш-таблицы с помощью метода toArray(T[] array):");
        System.out.println(Arrays.toString(array4));
        System.out.println();
    }
}