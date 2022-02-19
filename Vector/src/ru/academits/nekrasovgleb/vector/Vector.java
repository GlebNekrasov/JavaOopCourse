package ru.academits.nekrasovgleb.vector;

import java.util.Arrays;

public class Vector {
    private double[] components;

    public Vector(int vectorSize) {
        if (vectorSize <= 0) {
            throw new IllegalArgumentException("При создании вектора передано недопустимое значение размерности вектора:" +
                    " {" + vectorSize + "}. Размерность вектора должна быть > 0");
        }

        components = new double[vectorSize];
    }

    public Vector(double[] components) {
        if (components.length == 0) {
            throw new IllegalArgumentException("При создании вектора передан массив длины 0 в качестве аргумента. " +
                    "Длина массива должна быть > 0");
        }

        this.components = Arrays.copyOf(components, components.length);
    }

    public Vector(int vectorSize, double[] components) {
        if (vectorSize <= 0) {
            throw new IllegalArgumentException("При создании вектора передано недопустимое значение размерности вектора:" +
                    " {" + vectorSize + "}. Размерность вектора должна быть > 0");
        }

        this.components = new double[vectorSize];

        if (components.length != 0) {
            System.arraycopy(components, 0, this.components, 0, Math.min(components.length, vectorSize));
        }
    }

    public Vector(Vector vector) {
        components = Arrays.copyOf(vector.components, vector.components.length);
    }

    public int getSize() {
        return components.length;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("{");

        for (double component : components) {
            stringBuilder
                    .append(component)
                    .append(", ");
        }

        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        stringBuilder.append("}");

        return stringBuilder.toString();
    }

    public void add(Vector vector) {
        if (vector.components.length > components.length) {
            components = Arrays.copyOf(components, vector.components.length);
        }

        for (int i = 0; i < vector.components.length; ++i) {
            components[i] += vector.components[i];
        }
    }

    public void subtract(Vector vector) {
        if (vector.components.length > components.length) {
            components = Arrays.copyOf(components, vector.components.length);
        }

        for (int i = 0; i < vector.components.length; ++i) {
            components[i] -= vector.components[i];
        }
    }

    public void multiplyByScalar(double scalar) {
        for (int i = 0; i < components.length; ++i) {
            components[i] *= scalar;
        }
    }

    public void reverse() {
        multiplyByScalar(-1.0);
    }

    public double getLength() {
        double sum = 0.0;

        for (double e : components) {
            sum += e * e;
        }

        return Math.sqrt(sum);
    }

    public double getComponent(int index) {
        if (index < 0) {
            throw new ArrayIndexOutOfBoundsException("При получении значения компоненты вектора передан недопустимый номер" +
                    " компоненты: {" + index + "}. Номер компоненты вектора должен быть в интервале от 0 до " +
                    (components.length - 1));
        }

        if (index >= components.length) {
            throw new ArrayIndexOutOfBoundsException("При получении значения компоненты вектора передан недопустимый номер" +
                    " компоненты: {" + index + "}. Номер компоненты вектора должен быть в интервале от 0 до " +
                    (components.length - 1));
        }

        return components[index];
    }

    public void setComponent(int index, double component) {
        if (index < 0) {
            throw new ArrayIndexOutOfBoundsException("При установке значения компоненты вектора передан недопустимый номер" +
                    " компоненты: {" + index + "}. Номер компоненты вектора должен быть в интервале от 0 до " +
                    (components.length - 1));
        }

        if (index >= components.length) {
            throw new ArrayIndexOutOfBoundsException("При установке значения компоненты вектора передан недопустимый номер" +
                    " компоненты: {" + index + "}. Номер компоненты вектора должен быть в интервале от 0 до " +
                    (components.length - 1));
        }

        components[index] = component;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || o.getClass() != getClass()) {
            return false;
        }

        Vector vector = (Vector) o;

        return Arrays.equals(components, vector.components);
    }

    @Override
    public int hashCode() {
        final int prime = 11;
        int hash = 1;

        for (double e : components) {
            hash = prime * hash + Double.hashCode(e);
        }

        return hash;
    }

    public static Vector getSum(Vector vector1, Vector vector2) {
        Vector sumVector = new Vector(vector1);
        sumVector.add(vector2);
        return sumVector;
    }

    public static Vector getDifference(Vector vector1, Vector vector2) {
        Vector differenceVector = new Vector(vector1);
        differenceVector.subtract(vector2);
        return differenceVector;
    }

    public static double getScalarProduct(Vector vector1, Vector vector2) {
        double scalarProduct = 0;
        int minVectorSize = Math.min(vector1.components.length, vector2.components.length);

        for (int i = 0; i < minVectorSize; ++i) {
            scalarProduct += vector1.components[i] * vector2.components[i];
        }

        return scalarProduct;
    }
}