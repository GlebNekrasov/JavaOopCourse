package ru.academits.nekrasovgleb.myarraylist.main;

import ru.academits.nekrasovgleb.myarraylist.MyArrayList;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        MyArrayList<Integer> numbersList1 = new MyArrayList<>();
        System.out.println("Создан пустой список: " + numbersList1);
        numbersList1.add(3);
        numbersList1.add(4);
        numbersList1.add(7);
        System.out.println("В список добавлены элементы: " + numbersList1);
        System.out.println("Размер списка равен " + numbersList1.size());
        System.out.println();

        Integer oldElement1 = numbersList1.set(1, 5);
        System.out.println("В списке изменен элемент с индексом 1. Список после изменения: " + numbersList1);
        System.out.println("Старое значение элемента с индексом 1 равно " + oldElement1);
        System.out.println("Новое значение элемента с индексом 1 равно " + numbersList1.get(1));
        System.out.println("Размер списка не изменился и равен " + numbersList1.size());
        System.out.println();

        numbersList1.add(2, 6);
        System.out.println("Добавлен элемент по индексу 2. Список после изменения: " + numbersList1);
        System.out.println();

        MyArrayList<Integer> numbersList2 = new MyArrayList<>();
        numbersList2.add(8);
        numbersList2.add(9);
        System.out.println("Создан второй список: " + numbersList2);
        System.out.println();


        numbersList1.addAll(numbersList2);
        System.out.println("В первый список добавлены элементы второго списка.");
        System.out.println("Первый список после изменения: " + numbersList1);
        System.out.println();

        numbersList1.addAll(1, numbersList2);
        System.out.println("В первый список еще раз добавлены элементы второго списка, но теперь они вставлены начиная " +
                "с индекса 1 в первом списке.");
        System.out.println("Первый список после изменения: " + numbersList1);
        System.out.println();

        Integer removedItem = numbersList1.remove(3);
        System.out.println("В первом списке удален элемент с индексом 3.");
        System.out.println("Значение удаленного элемента с индексом 3 равно " + removedItem);
        System.out.println("Первый список после изменения: " + numbersList1);
        System.out.println();

        boolean isDeleted1 = numbersList1.remove(Integer.valueOf(8));
        System.out.println("Результат удаления из первого списка элемента со значением 8: " + isDeleted1);
        System.out.println("Первый список после изменения: " + numbersList1);
        System.out.println();

        boolean isDeleted2 = numbersList1.removeAll(numbersList2);
        System.out.println("Из первого списка удалены элементы, входящие во второй список. В результате этой операции " +
                "первый список изменился? - " + isDeleted2);
        System.out.println("Первый список после изменения: " + numbersList1);
        System.out.println();

        MyArrayList<Integer> numbersList3 = new MyArrayList<>();
        numbersList3.add(7);
        numbersList3.add(3);
        System.out.println("Создан третий список: " + numbersList3);
        System.out.println();

        boolean isDeleted3 = numbersList1.retainAll(numbersList3);
        System.out.println("В первом списке оставлены только элементы, входящие в третий список. В результате этой " +
                "операции первый список изменился? - " + isDeleted3);
        System.out.println("Первый список после изменения: " + numbersList1);
        System.out.println();

        boolean hasElement = numbersList1.contains(3);
        System.out.println("Первый список содержит элемент со значением 3? - " + hasElement);
        System.out.println();

        boolean hasAllElements = numbersList1.containsAll(numbersList3);
        System.out.println("Первый список содержит все элементы третьего списка? - " + hasAllElements);
        System.out.println();

        numbersList1.addAll(numbersList3);
        numbersList1.addAll(numbersList3);
        System.out.println("В первый список два раза добавили элементы третьего списка. Первый список после изменения: " +
                numbersList1);
        System.out.println("В первом списке число 7 первый раз встречается в элементе с индексом " +
                numbersList1.indexOf(7));
        System.out.println("В первом списке число 7 последний раз встречается в элементе с индексом "
                + numbersList1.lastIndexOf(7));
        System.out.println();

        Object[] objectsArray = numbersList1.toArray();
        System.out.println("Из первого списка создали массив объектов типа Object: " + Arrays.toString(objectsArray));

        Integer[] numbersArray = new Integer[numbersList1.size()];
        numbersArray = numbersList1.toArray(numbersArray);
        System.out.println("Из первого списка создали массив объектов типа Integer: " + Arrays.toString(numbersArray));
    }
}
