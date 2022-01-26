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
            System.out.println("Введеное число находится внутри первого диапазона");
        } else if (Math.abs(number - range1.getFrom()) < Math.abs(number - range1.getTo())) {
            System.out.println("Введенное число лежит левее первого диапазана на " + (range1.getFrom() - number));
        } else {
            System.out.println("Введенное число лежит правее первого диапазана на " + (number - range1.getTo()));
        }

        Range rangeIntersection = range1.getIntersection(range2);

        if (rangeIntersection == null) {
            System.out.print("Диапазоны не пересекаются");
        } else {
            double rangeIntersectionFrom = rangeIntersection.getFrom();
            double rangeIntersectionTo = rangeIntersection.getTo();
            System.out.printf("Диапазаон пересечения: (%f; %f)", rangeIntersectionFrom, rangeIntersectionTo);
        }

        System.out.println();
        Range[] unionRange = range1.getUnion(range2);

        if (unionRange.length == 1) {
            System.out.printf("Объединение диапазаонов - это один диапазон: (%f; %f)",
                    unionRange[0].getFrom(), unionRange[0].getTo());
        } else {
            System.out.printf("Объединение диапазаонов - это два диапазона: (%f; %f) и (%f; %f)",
                    unionRange[0].getFrom(), unionRange[0].getTo(), unionRange[1].getFrom(), unionRange[1].getTo());
        }

        System.out.println();
        Range[] differenceRange = range1.getDifference(range2);

        if (differenceRange == null) {
            System.out.print("Разность первого и второго диапазона равна 0");
        } else if (differenceRange.length == 1) {
            System.out.printf("Разность первого и второго диапазаона - это один диапазон: (%f; %f)",
                    differenceRange[0].getFrom(), differenceRange[0].getTo());
        } else {
            System.out.printf("Разность первого и второго диапазона - это два диапазона: (%f; %f) и (%f; %f)",
                    differenceRange[0].getFrom(), differenceRange[0].getTo(),
                    differenceRange[1].getFrom(), differenceRange[1].getTo());
        }
    }
}
