package ru.academits.nekrasovgleb.matrix;

import ru.academits.nekrasovgleb.vector.Vector;

public class Matrix {
    private Vector[] rows;

    public Matrix(Vector[] rows) {
        if (rows.length == 0) {
            throw new IllegalArgumentException("При создании матрицы в качестве аргумента передан пустой массив векторов." +
                    " Количество векторов в массиве должно быть > 0");
        }

        int maxVectorSize = 0;

        for (Vector e : rows) {
            if (e != null && e.getSize() > maxVectorSize) {
                maxVectorSize = e.getSize();
            }
        }

        if (maxVectorSize == 0) {
            throw new IllegalArgumentException("При создании матрицы в качестве аргумента передан массив векторов " +
                    "нулевой размерности. В массиве должен быть хотя бы один вектор с размерностью > 0.");
        }

        this.rows = new Vector[rows.length];

        for (int i = 0; i < rows.length; ++i) {
            this.rows[i] = new Vector(maxVectorSize);

            if (rows[i] != null) {
                this.rows[i].add(rows[i]);
            }
        }
    }

    public Matrix(int rowsCount, int columnsCount) {
        if (rowsCount <= 0) {
            throw new IllegalArgumentException("При создании матрицы передано недопустимое количество строк: {" +
                    rowsCount + "}. Количество строк в матрице должна быть > 0");
        }

        if (columnsCount <= 0) {
            throw new IllegalArgumentException("При создании матрицы передано недопустимое количество столбцов: {" +
                    columnsCount + "}. Количество столбцов в матрице должна быть > 0");
        }

        rows = new Vector[rowsCount];

        for (int i = 0; i < rowsCount; ++i) {
            rows[i] = new Vector(columnsCount);
        }
    }

    public Matrix(double[][] components) {
        if (components.length == 0) {
            throw new IllegalArgumentException("При создании матрицы в качестве аргумента передан пустой массив массивов." +
                    " Количество массивов в массиве должно быть > 0");
        }

        int maxRowLength = 0;

        for (double[] e : components) {
            if (e != null && e.length > maxRowLength) {
                maxRowLength = e.length;
            }
        }

        if (maxRowLength == 0) {
            throw new IllegalArgumentException("При создании матрицы в качестве аргумента передан массив пустых " +
                    "массивов. В массиве массивов должен быть хотя бы один массив с длиной > 0.");
        }

        rows = new Vector[components.length];

        for (int i = 0; i < components.length; ++i) {
            rows[i] = new Vector(maxRowLength, components[i]);
        }
    }

    public Matrix(Matrix matrix) {
        rows = new Vector[matrix.rows.length];

        for (int i = 0; i < matrix.rows.length; ++i) {
            rows[i] = new Vector(matrix.rows[i]);
        }
    }

    public int getRowsCount() {
        return rows.length;
    }

    public int getColumnsCount() {
        return rows[0].getSize();
    }

    public Vector getRow(int rowIndex) {
        if (rowIndex < 0 || rowIndex >= rows.length) {
            throw new IndexOutOfBoundsException("При получении вектора-строки передан недопустимый индекс строки:" +
                    " {" + rowIndex + "}. Индекс строки должен быть в интервале от 0 до " + (rows.length - 1));
        }

        return new Vector(rows[rowIndex]);
    }

    public void setRow(int rowIndex, Vector vector) {
        if (rowIndex < 0 || rowIndex >= rows.length) {
            throw new IndexOutOfBoundsException("При задании вектора-строки передан недопустимый индекс строки:" +
                    " {" + rowIndex + "}. Индекс строки должен быть в интервале от 0 до " + (rows.length - 1));
        }

        if (vector.getSize() != getColumnsCount()) {
            throw new IllegalArgumentException("При задании вектора-строки передан вектор не соответствующей размерности:" +
                    " {" + vector.getSize() + "}. Размерность вектора должна быть равна " + getColumnsCount());
        }

        rows[rowIndex] = new Vector(vector);
    }

    public Vector getColumn(int columnIndex) {
        if (columnIndex < 0 || columnIndex >= getColumnsCount()) {
            throw new IndexOutOfBoundsException("При получении вектора-столбца передан недопустимый индекс столбца:" +
                    " {" + columnIndex + "}. Индекс столбца должен быть в интервале от 0 до " + (getColumnsCount() - 1));
        }

        double[] column = new double[rows.length];

        for (int i = 0; i < rows.length; ++i) {
            column[i] = rows[i].getComponent(columnIndex);
        }

        return new Vector(column);
    }

    public void transpose() {
        Vector[] transposedRows = new Vector[getColumnsCount()];

        for (int i = 0; i < transposedRows.length; ++i) {
            transposedRows[i] = getColumn(i);
        }

        rows = transposedRows;
    }

    public void multiplyByScalar(double scalar) {
        for (Vector e : rows) {
            e.multiplyByScalar(scalar);
        }
    }

    public double getDeterminant() {
        if (rows.length != getColumnsCount()) {
            throw new UnsupportedOperationException("Определитель матрицы можно посчитать только для квадратной матрицы." +
                    " Была попытка посчитать определитель для матрицы размером " + rows.length + "x" + getColumnsCount());
        }

        if (rows.length == 1) {
            return rows[0].getComponent(0);
        }

        if (rows.length == 2) {
            return rows[0].getComponent(0) * rows[1].getComponent(1) -
                    rows[0].getComponent(1) * rows[1].getComponent(0);
        }

        double determinant = 0;

        // далее вычисляем определитель путем разложения определителя по элементам столбца с индексом 0
        // i - индекс строки, которая вычеркивается для получения минорной матрицы
        // j - индекс строки при проходе всех строк матрицы, кроме вычеркнутой, для копирования компонент исходной матрицы в минорную
        // k - индекс столбца при проходе всех столбцов матрицы, кроме 0-ого, для копирования компонент исходной матрицы в минорную
        for (int i = 0; i < rows.length; ++i) {
            double[][] minorComponents = new double[rows.length - 1][rows.length - 1];

            for (int j = 0; j < rows.length; ++j) {
                if (j == i) {
                    continue;
                }

                if (j < i) {
                    for (int k = 1; k < rows.length; ++k) {
                        minorComponents[j][k - 1] = rows[j].getComponent(k);
                    }
                } else {
                    for (int k = 1; k < rows.length; ++k) {
                        minorComponents[j - 1][k - 1] = rows[j].getComponent(k);
                    }
                }
            }

            Matrix minor = new Matrix(minorComponents);
            determinant += Math.pow(-1, i + 2) * rows[i].getComponent(0) * minor.getDeterminant();
        }

        return determinant;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("{");

        for (Vector component : rows) {
            stringBuilder
                    .append(component)
                    .append(", ");
        }

        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        stringBuilder.append("}");

        return stringBuilder.toString();
    }

    public Vector multiplyByVector(Vector vector) {
        if (vector.getSize() != getColumnsCount()) {
            throw new IllegalArgumentException("При умножении матрицы на вектор передан вектор недопустимой размерности: {" +
                    vector.getSize() + "}. Размерность вектора должна совпадать с количеством столбцов в матрице: {" +
                    getColumnsCount() + "}");
        }

        Vector resultVector = new Vector(rows.length);

        for (int i = 0; i < rows.length; ++i) {
            resultVector.setComponent(i, Vector.getScalarProduct(rows[i], vector));
        }

        return resultVector;
    }

    private static void checkSizeEqual(Matrix matrix1, Matrix matrix2) {
        if (matrix1.getRowsCount() != matrix2.getRowsCount()) {
            throw new IllegalArgumentException("Была попытка сделать операцию над матрицами с разным числом строк: {" +
                    matrix1.getRowsCount() + "} и {" + matrix2.getRowsCount() + "}. Число строк должно совпадать.");
        }

        if (matrix1.getColumnsCount() != matrix2.getColumnsCount()) {
            throw new IllegalArgumentException("Была попытка сделать операцию над матрицами с разным числом столбцов: {" +
                    matrix1.getColumnsCount() + "} и {" + matrix2.getColumnsCount() + "}. Число столбцов должно совпадать.");
        }
    }

    public void add(Matrix matrix) {
        checkSizeEqual(this, matrix);

        for (int i = 0; i < rows.length; ++i) {
            rows[i].add(matrix.rows[i]);
        }
    }

    public void subtract(Matrix matrix) {
        checkSizeEqual(this, matrix);

        for (int i = 0; i < rows.length; ++i) {
            rows[i].subtract(matrix.rows[i]);
        }
    }

    public static Matrix getSum(Matrix matrix1, Matrix matrix2) {
        checkSizeEqual(matrix1, matrix2);

        Matrix sumMatrix = new Matrix(matrix1);
        sumMatrix.add(matrix2);
        return sumMatrix;
    }

    public static Matrix getDifference(Matrix matrix1, Matrix matrix2) {
        checkSizeEqual(matrix1, matrix2);

        Matrix differenceMatrix = new Matrix(matrix1);
        differenceMatrix.subtract(matrix2);
        return differenceMatrix;
    }

    public static Matrix getProduct(Matrix matrix1, Matrix matrix2) {
        if (matrix1.getColumnsCount() != matrix2.getRowsCount()) {
            throw new IllegalArgumentException("Была попытка умножения матрицы с количеством столбцов: {" +
                    matrix1.getColumnsCount() + "} на матрицу с количеством строк {" + matrix2.getRowsCount() + "}. " +
                    "Число столбцов в первой матрице должно совпадать с числом строк во второй матрице.");
        }

        double[][] components = new double[matrix1.getRowsCount()][matrix2.getColumnsCount()];

        for (int i = 0; i < matrix1.getRowsCount(); ++i) {
            for (int j = 0; j < matrix2.getColumnsCount(); ++j) {
                components[i][j] = Vector.getScalarProduct(matrix1.rows[i], matrix2.getColumn(j));
            }
        }

        return new Matrix(components);
    }
}