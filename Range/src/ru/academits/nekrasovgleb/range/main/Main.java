package ru.academits.nekrasovgleb.range.main;

import ru.academits.nekrasovgleb.range.methods.Range;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите начало диапазона:");
        double from = scanner.nextDouble();

        System.out.println("Введите конец диапазона:");
        double to = scanner.nextDouble();

        // далее проверяем, если начало диапазона больше, чем конец, то меняем местами начало и конец диапазона
        if (from > to) {
            double temp = from;
            from = to;
            to = temp;
        }

        Range range1 = new Range(from, to);

        System.out.println("Длина указанного диапазона = " + range1.getLength());

        System.out.println("Введите число, чтобы проверить, находится ли оно внутри диапазона");
        double number = scanner.nextDouble();

        if (range1.isInside(number)) {
            System.out.println("Введеное число находится внутри указанного диапазона");
        } else if (Math.abs(number - range1.getFrom()) < Math.abs(number - range1.getTo())) {
            System.out.println("Введенное число лежит левее указанного диапазана на " + (range1.getFrom() - number));
            range1.setFrom(number);
            System.out.printf("Диапазон расширен до (%f; %f), чтобы он включал введеное число", range1.getFrom(), range1.getTo());
        } else {
            System.out.println("Введенное число лежит правее указанного диапазана на " + (number - range1.getTo()));
            range1.setTo(number);
            System.out.printf("Диапазон расширен до (%f; %f), чтобы он включал введеное число", range1.getFrom(), range1.getTo());
        }
    }
}
