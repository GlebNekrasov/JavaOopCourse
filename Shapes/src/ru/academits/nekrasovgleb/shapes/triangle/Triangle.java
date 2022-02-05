package ru.academits.nekrasovgleb.shapes.triangle;

import ru.academits.nekrasovgleb.shapes.Shape;

public class Triangle implements Shape {
    private double x1, y1, x2, y2, x3, y3;

    public Triangle(double x1, double y1, double x2, double y2, double x3, double y3) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.x3 = x3;
        this.y3 = y3;
    }

    public double getX1() {
        return x1;
    }

    public void setX1(double x1) {
        this.x1 = x1;
    }

    public double getY1() {
        return y1;
    }

    public void setY1(double y1) {
        this.y1 = y1;
    }

    public double getX2() {
        return x2;
    }

    public void setX2(double x2) {
        this.x2 = x2;
    }

    public double getY2() {
        return y2;
    }

    public void setY2(double y2) {
        this.y2 = y2;
    }

    public double getX3() {
        return x3;
    }

    public void setX3(double x3) {
        this.x3 = x3;
    }

    public double getY3() {
        return y3;
    }

    public void setY3(double y3) {
        this.y3 = y3;
    }

    @Override
    public double getWidth() {
        return Math.max(x1, Math.max(x2, x3)) - Math.min(x1, Math.min(x2, x3));
    }

    @Override
    public double getHeight() {
        return Math.max(y1, Math.max(y2, y3)) - Math.min(y1, Math.min(y2, y3));
    }

    @Override
    public double getArea() {
        double triangleSide1Length = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
        double triangleSide2Length = Math.sqrt(Math.pow(x3 - x2, 2) + Math.pow(y3 - y2, 2));
        double triangleSide3Length = Math.sqrt(Math.pow(x3 - x1, 2) + Math.pow(y3 - y1, 2));
        double triangleHalfPerimeter = (triangleSide1Length + triangleSide2Length + triangleSide3Length) / 2;

        return Math.sqrt(triangleHalfPerimeter * (triangleHalfPerimeter - triangleSide1Length) *
                (triangleHalfPerimeter - triangleSide2Length) * (triangleHalfPerimeter - triangleSide3Length));
    }

    @Override
    public double getPerimeter() {
        double triangleSide1Length = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
        double triangleSide2Length = Math.sqrt(Math.pow(x3 - x2, 2) + Math.pow(y3 - y2, 2));
        double triangleSide3Length = Math.sqrt(Math.pow(x3 - x1, 2) + Math.pow(y3 - y1, 2));

        return triangleSide1Length + triangleSide2Length + triangleSide3Length;
    }

    @Override
    public String toString() {
        return String.format("(x1 = %f, y1 = %f, x2 = %f, y2 = %f, x3 = %f, y3 = %f)", x1, y1, x2, y2, x3, y3);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int hash = 1;
        hash = prime * hash + Double.hashCode(x1);
        hash = prime * hash + Double.hashCode(y1);
        hash = prime * hash + Double.hashCode(x2);
        hash = prime * hash + Double.hashCode(y2);
        hash = prime * hash + Double.hashCode(x3);
        hash = prime * hash + Double.hashCode(y3);
        return hash;
    }

    @Override
    public boolean equals(Shape shape) {
        if (shape == this) {
            return true;
        }

        if (shape == null || shape.getClass() != getClass()) {
            return false;
        }

        Triangle triangle = (Triangle) shape;

        return triangle.x1 == x1 && triangle.y1 == y1 &&
                triangle.x2 == x2 && triangle.y2 == y2 &&
                triangle.x3 == x3 && triangle.y3 == y3;
    }
}
