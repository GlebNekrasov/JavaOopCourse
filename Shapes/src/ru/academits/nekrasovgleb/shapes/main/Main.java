package ru.academits.nekrasovgleb.shapes.main;

import ru.academits.nekrasovgleb.shapes.Shape;
import ru.academits.nekrasovgleb.shapes.Circle;
import ru.academits.nekrasovgleb.shapes.Rectangle;
import ru.academits.nekrasovgleb.shapes.Square;
import ru.academits.nekrasovgleb.shapes.Triangle;

import java.util.Arrays;

public class Main {
    public static void printShape(Shape shape) {
        System.out.println(shape);
        System.out.println("Ширина фигуры = " + shape.getWidth());
        System.out.println("Высота фигуры = " + shape.getHeight());
        System.out.println("Периметр фигуры = " + shape.getPerimeter());
        System.out.println("Площадь фигуры = " + shape.getArea());
    }

    public static void main(String[] args) {
        Shape[] shapes = {
                new Rectangle(4.5, 10.5),
                new Rectangle(2.5, 1.5),
                new Square(1.5),
                new Square(5.5),
                new Circle(2.5),
                new Circle(5.5),
                new Triangle(1.5, 2.5, 4.5, 0.5, 3.5, 1.5),
                new Triangle(0.5, 1, 7.5, 2, 5.5, 1.5)
        };

        Arrays.sort(shapes, new AreaComparator());
        Shape maxAreaShape = shapes[shapes.length - 1];
        System.out.println("Описание фигуры с максимальной площадью:");
        printShape(maxAreaShape);

        System.out.println();

        Arrays.sort(shapes, new PerimeterComparator());
        Shape secondPerimeterShape = shapes[shapes.length - 2];
        System.out.println("Описание фигуры со вторым по величине периметром:");
        printShape(secondPerimeterShape);
    }
}