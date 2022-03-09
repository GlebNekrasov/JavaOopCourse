package ru.academits.nekrasovgleb.arraylisthome;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class ArrayListHome {
    public static void main(String[] args) throws FileNotFoundException {
        // 1. Чтение в список всех строк файла
        try (Scanner scanner = new Scanner(new FileInputStream("Strings.txt"))) {
            ArrayList<String> stringsList = new ArrayList<>();

            while (scanner.hasNextLine()) {
                stringsList.add(scanner.nextLine());
            }

            System.out.println("Проверяем, что строки файла добавились в список:");
            System.out.println(stringsList);
            System.out.println();
        }

        // 2. Удаление четных чисел из списка целых чисел
        ArrayList<Integer> integersList = new ArrayList<>(Arrays.asList(2, 7, 4, 2, 5, 5, 6, 9, 4, 7, 8, 13, 14, 15, 2));
        System.out.println("Исходный список: " + integersList);

        for (int i = integersList.size() - 1; i >= 0; --i) {
            if (integersList.get(i) % 2 == 0) {
                integersList.remove(i);
            }
        }

        System.out.println("Список после удаления четных чисел: " + integersList);
        System.out.println();

        // 3. Создание нового списка без повторений
        ArrayList<Integer> integersList2 = new ArrayList<>(Arrays.asList(2, 7, 4, 2, 5, 5, 6, 9, 4, 7, 8, 13, 14, 15, 2));
        System.out.println("Исходный список: " + integersList2);

        for (int i = integersList2.size() - 1; i >= 0; --i) {
            if (integersList2.indexOf(integersList2.get(i)) != i) {
                integersList2.remove(i);
            }
        }

        System.out.println("Список после удаления повторяющихся элементов: " + integersList2);
    }
}