package ru.academits.nekrasovgleb.vector;

public class Vector {
    private double[] components;

    public Vector(double[] components) {
        if (components.length == 0) {
            throw new IllegalArgumentException("Размерность вектора должна быть > 0");
        }

        this.components = components;
    }

    public double[] getComponents() {
        return components;
    }

    public void setComponents(double[] components) {
        if (components.length == 0) {
            throw new IllegalArgumentException("Размерность вектора должна быть > 0");
        }

        this.components = components;
    }

    public Vector(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Размерность вектора должна быть > 0");
        }

        this.components = new double[n];
    }

    public Vector(Vector vector) {
        this.components = new double[vector.components.length];
        System.arraycopy(vector.components, 0, this.components, 0, vector.components.length);
    }

    public Vector(int n, double[] components) {
        if (n <= 0 || components.length == 0) {
            throw new IllegalArgumentException("Размерность вектора должна быть > 0");
        }

        this.components = new double[n];

        for (int i = 0; i < n && i < components.length; ++i) {
            this.components[i] = components[i];
        }
    }

    public int getSize() {
        return components.length;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        for (double component : components) {
            stringBuilder
                    .append(component)
                    .append(", ");
        }

        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        return "{ " + stringBuilder + " }";
    }

    public void add(Vector vector) {
        if (getSize() >= vector.getSize()) {
            for (int i = 0; i < vector.getSize(); ++i) {
                this.components[i] += vector.components[i];
            }
        } else {
            Vector sumVector = new Vector(vector);
            for (int i = 0; i < getSize(); ++i) {
                sumVector.components[i] += this.components[i];
            }
            this.components = sumVector.components;
        }
    }

    public void subtract(Vector vector) {
        Vector subtrahendVector = new Vector(vector);
        subtrahendVector.reverse();
        this.add(subtrahendVector);
    }

    public void multiplyByScalar(double n) {
        for (int i = 0; i < getSize(); ++i) {
            this.components[i] *= n;
        }
    }

    public void reverse() {
        this.multiplyByScalar(-1.0);
    }

    public double getLength() {
        double length = 0.0;

        for (double e : components) {
            length += e * e;
        }

        return Math.sqrt(length);
    }

    public double getComponent(int number) {
        return components[number];
    }

    public void setComponent(int number, double component) {
        if (number < 0) {
            throw new IllegalArgumentException("Номер компоненты вектора должен быть >= 0");
        }

        if (number >= getSize()) {
            throw new IllegalArgumentException("Номер компоненты вектора должен быть меньше размерности вектора");
        }

        this.components[number] = component;
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

        if (vector.getSize() != getSize()) {
            return false;
        }

        for (int i = 0; i < getSize(); ++i) {
            if (vector.components[i] != components[i]) {
                return false;
            }
        }

        return true;
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

    public static Vector getScalarProduct(Vector vector1, Vector vector2) {
        if (vector1.getSize() >= vector2.getSize()) {
            Vector scalarProductVector = new Vector(vector1);

            for (int i = 0; i < vector2.getSize(); ++i) {
                scalarProductVector.components[i] *= vector2.components[i];
            }

            for (int i = vector2.getSize(); i < vector1.getSize(); ++i) {
                scalarProductVector.components[i] = 0;
            }

            return scalarProductVector;
        }

        Vector scalarProductVector = new Vector(vector2);

        for (int i = 0; i < vector1.getSize(); ++i) {
            scalarProductVector.components[i] *= vector1.components[i];
        }

        for (int i = vector1.getSize(); i < vector2.getSize(); ++i) {
            scalarProductVector.components[i] = 0;
        }

        return scalarProductVector;
    }
}