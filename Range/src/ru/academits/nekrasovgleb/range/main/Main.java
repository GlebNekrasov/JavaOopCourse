package ru.academits.nekrasovgleb.range.main;

import ru.academits.nekrasovgleb.range.Range;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите начало первого диапазона:");
        double from1 = scanner.nextDouble();

        System.out.println("Введите конец первого диапазона:");
        double to1 = scanner.nextDouble();

        System.out.println("Введите начало второго диапазона:");
        double from2 = scanner.nextDouble();

        System.out.println("Введите конец второго диапазона:");
        double to2 = scanner.nextDouble();

        // далее проверяем, если начало диапазона больше, чем конец, то меняем местами начало и конец диапазона
        if (from1 > to1) {
            double temp = from1;
            from1 = to1;
            to1 = temp;
        }

        if (from2 > to2) {
            double temp = from2;
            from2 = to2;
            to2 = temp;
        }

        Range range1 = new Range(from1, to1);
        Range range2 = new Range(from2, to2);

        System.out.println("Длина первого диапазона = " + range1.getLength());
        System.out.println("Длина второго диапазона = " + range2.getLength());

        System.out.println("Введите число, чтобы проверить, находится ли оно внутри первого диапазона");
        double number = scanner.nextDouble();

        if (range1.isInside(number)) {
            System.out.println("Введенное число находится внутри первого диапазона");
        } else if (Math.abs(number - range1.getFrom()) < Math.abs(number - range1.getTo())) {
            System.out.println("Введенное число лежит левее первого диапазона на " + (range1.getFrom() - number));
        } else {
            System.out.println("Введенное число лежит правее первого диапазона на " + (number - range1.getTo()));
        }

        Range intersection = range1.getIntersection(range2);
        System.out.print("Пересечение диапазонов: ");

        if (intersection == null) {
            System.out.print("отсутствует");
        } else {
            System.out.print(intersection);
        }

        System.out.println();
        Range[] union = range1.getUnion(range2);
        System.out.print("Объединение диапазонов: ");

        for (Range e : union) {
            System.out.print(e + "; ");
        }

        System.out.println();
        Range[] difference = range1.getDifference(range2);
        System.out.print("Разность диапазонов: ");

        for (Range e : difference) {
            System.out.print(e + "; ");
        }
    }
}
