package ru.academits.nekrasovgleb.shapes.main;

import ru.academits.nekrasovgleb.shapes.Shape;
import ru.academits.nekrasovgleb.shapes.circle.Circle;
import ru.academits.nekrasovgleb.shapes.rectangle.Rectangle;
import ru.academits.nekrasovgleb.shapes.square.Square;
import ru.academits.nekrasovgleb.shapes.triangle.Triangle;

import java.util.Arrays;

public class Main {
    public static void printShape(Shape shape) {
        System.out.println("Тип фигуры - " + shape.getClass().getSimpleName());
        System.out.println("Параметры фигуры: " + shape);
        System.out.println("Ширина фигуры = " + shape.getWidth());
        System.out.println("Высота фигуры = " + shape.getHeight());
        System.out.println("Периметр фигуры = " + shape.getPerimeter());
        System.out.println("Площадь фигуры = " + shape.getArea());
    }

    public static void main(String[] args) {
        Shape rectangle1 = new Rectangle(4.5, 10.5);
        Shape rectangle2 = new Rectangle(2.5, 1.5);
        Shape square1 = new Square(1.5);
        Shape square2 = new Square(5.5);
        Shape circle1 = new Circle(2.5);
        Shape circle2 = new Circle(5.5);
        Shape triangle1 = new Triangle(1.5, 2.5, 4.5, 0.5, 3.5, 1.5);
        Shape triangle2 = new Triangle(0.5, 1, 7.5, 2, 5.5, 1.5);

        Shape[] shapes = {rectangle1, rectangle2, square1, square2, circle1, circle2, triangle1, triangle2};

        Arrays.sort(shapes, Shape.AreaComparator);
        Shape maxAreaShape = shapes[shapes.length - 1];
        System.out.println("Описание фигуры с максимальной площадью:");
        printShape(maxAreaShape);

        Arrays.sort(shapes, Shape.PerimeterComparator);
        Shape secondPerimeterShape = shapes[shapes.length - 2];
        System.out.println("Описание фигуры со вторым по величине периметром:");
        printShape(secondPerimeterShape);
    }
}
