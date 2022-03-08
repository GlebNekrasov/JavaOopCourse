package ru.academits.nekrasovgleb.singlylinkedlist.main;

import ru.academits.nekrasovgleb.singlylinkedlist.SinglyLinkedList;

public class Main {
    public static void main(String[] args) {
        SinglyLinkedList<Integer> integerList = new SinglyLinkedList<>();
        System.out.println("Создан пустой список. Размер списка равен: " + integerList.getSize());
        System.out.println();

        integerList.addItem(1);
        integerList.addItem(2);
        integerList.addItem(3);
        integerList.addItem(4);
        System.out.println("В список добавлены элементы. Размер списка равен: " + integerList.getSize());
        System.out.println("Элементы списка: " + integerList);
        System.out.println("Элемент списка с индексом 0 равен: " + integerList.getFirstItem());
        System.out.println();

        integerList.setItem(3, 10);
        System.out.println("В списке изменен элемент с индексом 3. Размер списка равен: " + integerList.getSize());
        System.out.println("Элементы списка: " + integerList);
        System.out.println();

        integerList.removeItem(3);
        System.out.println("В списке удален элемент с индексом 3. Размер списка равен: " + integerList.getSize());
        System.out.println("Элементы списка: " + integerList);
        System.out.println();

        integerList.addItem(20, 2);
        System.out.println("В список добавлен элемент по индексу 2. Размер списка равен: " + integerList.getSize());
        System.out.println("Элементы списка: " + integerList);
        System.out.println();

        integerList.removeFirstItem();
        System.out.println("В списке удален первый элемент. Размер списка равен: " + integerList.getSize());
        System.out.println("Элементы списка: " + integerList);
        System.out.println();

        boolean isDeleted = integerList.removeItem(Integer.valueOf(20));
        System.out.println("Элемент со значением \"20\" удален из списка? - " + isDeleted);
        System.out.println("Размер списка равен: " + integerList.getSize());
        System.out.println("Элементы списка: " + integerList);
        System.out.println();

        integerList.addItem(4);
        integerList.addItem(5);
        System.out.println("В список добавлено два элемента. Размер списка равен: " + integerList.getSize());
        System.out.println("Элементы списка: " + integerList);
        System.out.println();

        integerList.reverse();
        System.out.println("Сделан разворот списка. Размер списка равен: " + integerList.getSize());
        System.out.println("Элементы списка: " + integerList);
        System.out.println();

        SinglyLinkedList<Integer> integerList2 = new SinglyLinkedList<>(integerList);
        System.out.println("Список скопирован. Размер списка-копии равен: " + integerList2.getSize());
        System.out.println("Элементы списка-копии: " + integerList2);
        System.out.println();

        integerList.setItem(1, 500);
        System.out.println("В списке-источнике, который скопировали, заменили элемент с индексом 1.");
        System.out.println("Элементы списка-источника: " + integerList);
        System.out.println();

        System.out.println("Проверяем, что элементы списка-копии не изменились: " + integerList2);
        System.out.println();
    }
}
