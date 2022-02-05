package ru.academits.nekrasovgleb.shapes;

import java.util.Comparator;

public interface Shape {
    double getWidth();

    double getHeight();

    double getArea();

    double getPerimeter();

    boolean equals(Shape shape);

    Comparator<Shape> AreaComparator = new Comparator<>() {
        @Override
        public int compare(Shape shape1, Shape shape2) {
            return Double.compare(shape1.getArea(), shape2.getArea());
        }
    };

    Comparator<Shape> PerimeterComparator = new Comparator<>() {
        @Override
        public int compare(Shape shape1, Shape shape2) {
            return Double.compare(shape1.getPerimeter(), shape2.getPerimeter());
        }
    };
}
