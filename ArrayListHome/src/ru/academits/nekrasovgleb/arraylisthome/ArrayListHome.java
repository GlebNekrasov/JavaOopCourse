package ru.academits.nekrasovgleb.arraylisthome;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class ArrayListHome {
    public static void main(String[] args) {
        // 1. Чтение в список всех строк файла
        try (BufferedReader reader = new BufferedReader(new FileReader("Strings.txt"))) {
            ArrayList<String> linesList = new ArrayList<>();

            for (String line = reader.readLine(); line != null; ) {
                linesList.add(line);
                line = reader.readLine();
            }

            System.out.println("Проверяем, что строки файла добавились в список:");
            System.out.println(linesList);
            System.out.println();
        } catch (IOException e) {
            System.out.println("При попытке прочитать файл произошла ошибка: " + e.getMessage());
        }

        // 2. Удаление четных чисел из списка целых чисел
        ArrayList<Integer> numbersList1 = new ArrayList<>(Arrays.asList(2, 7, 4, 2, 5, 5, 6, 9, 4, 7, 8, 13, 14, 15, 2));
        System.out.println("Исходный список: " + numbersList1);

        for (int i = numbersList1.size() - 1; i >= 0; --i) {
            if (numbersList1.get(i) % 2 == 0) {
                numbersList1.remove(i);
            }
        }

        System.out.println("Список после удаления четных чисел: " + numbersList1);
        System.out.println();

        // 3. Создание нового списка без повторений
        ArrayList<Integer> numbersList2 = new ArrayList<>(Arrays.asList(2, 7, 4, 2, 5, 5, 6, 9, 4, 7, 8, 13, 14, 15, 2));
        System.out.println("Исходный список: " + numbersList2);

        ArrayList<Integer> numbersList3 = new ArrayList<>();

        for (int i = 0; i < numbersList2.size(); ++i) {
            if (numbersList2.indexOf(numbersList2.get(i)) == i) {
                numbersList3.add(numbersList2.get(i));
            }
        }

        System.out.println("Список после удаления повторяющихся элементов: " + numbersList3);
    }
}