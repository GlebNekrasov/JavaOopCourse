package ru.academits.nekrasovgleb.singlylinkedlist.main;

import ru.academits.nekrasovgleb.singlylinkedlist.SinglyLinkedList;

public class Main {
    public static void main(String[] args) {
        SinglyLinkedList<Integer> integerList1 = new SinglyLinkedList<>();
        System.out.println("Создан пустой список. Размер списка равен: " + integerList1.getSize());
        System.out.println();

        integerList1.addFirst(1);
        integerList1.addFirst(2);
        integerList1.addFirst(null);
        integerList1.addFirst(3);
        integerList1.addFirst(null);
        integerList1.addFirst(4);
        integerList1.addFirst(5);
        System.out.println("В список добавлены элементы. Размер списка равен: " + integerList1.getSize());
        System.out.println("Элементы списка: " + integerList1);
        System.out.println("Элемент списка с индексом 0 равен: " + integerList1.getFirst());
        System.out.println();

        Integer oldData1 = integerList1.set(3, 10);
        System.out.println("В списке изменен элемент с индексом 3. Старое значение элемента: " + oldData1);
        System.out.println("Элементы списка: " + integerList1);
        System.out.println();

        Integer removedData1 = integerList1.remove(3);
        System.out.println("В списке удален элемент с индексом 3. Значение удаленного элемента: " + removedData1);
        System.out.println("Элементы списка: " + integerList1);
        System.out.println();

        integerList1.add(3, 2000);
        System.out.println("В список добавлен элемент по индексу 3. Размер списка равен: " + integerList1.getSize());
        System.out.println("Элементы списка: " + integerList1);
        System.out.println();

        Integer removedData2 = integerList1.removeFirst();
        System.out.println("В списке удален первый элемент. Значение удаленного элемента: " + removedData2);
        System.out.println("Элементы списка: " + integerList1);
        System.out.println();

        boolean isDeleted1 = integerList1.remove(Integer.valueOf(2000));
        System.out.println("Элемент со значением \"2000\" удален из списка? - " + isDeleted1);
        System.out.println("Размер списка равен: " + integerList1.getSize());
        System.out.println("Элементы списка: " + integerList1);
        System.out.println();

        boolean isDeleted2 = integerList1.remove(null);
        System.out.println("Элемент со значением \"null\" удален из списка? - " + isDeleted2);
        System.out.println("Размер списка равен: " + integerList1.getSize());
        System.out.println("Элементы списка: " + integerList1);
        System.out.println();

        integerList1.addFirst(4);
        integerList1.addFirst(5);
        System.out.println("В список добавлено два элемента. Размер списка равен: " + integerList1.getSize());
        System.out.println("Элементы списка: " + integerList1);
        System.out.println();

        integerList1.reverse();
        System.out.println("Сделан разворот списка. Размер списка равен: " + integerList1.getSize());
        System.out.println("Элементы списка: " + integerList1);
        System.out.println();

        SinglyLinkedList<Integer> integerList2 = new SinglyLinkedList<>(integerList1);
        System.out.println("Список скопирован. Размер списка-копии равен: " + integerList2.getSize());
        System.out.println("Элементы списка-копии: " + integerList2);
        System.out.println();

        integerList1.set(1, 500);
        System.out.println("В списке-источнике, который скопировали, заменили элемент с индексом 1.");
        System.out.println("Элементы списка-источника: " + integerList1);
        System.out.println();

        System.out.println("Проверяем, что элементы списка-копии не изменились: " + integerList2);
        System.out.println();
    }
}