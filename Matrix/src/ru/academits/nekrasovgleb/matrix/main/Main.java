package ru.academits.nekrasovgleb.matrix.main;

import ru.academits.nekrasovgleb.matrix.Matrix;
import ru.academits.nekrasovgleb.vector.Vector;

public class Main {
    public static void main(String[] args) {
        System.out.println("Проверяем конструктор создания матрицы из векторов разной размерности:");
        Vector[] vectors = {
                new Vector(new double[]{1.5, 2.0, 0.5}),
                new Vector(new double[]{0.5, 2.5, 4.5, 6.0, 7.5}),
                new Vector(new double[]{1.0, 1.5, 3.0, 4.0})
        };
        Matrix matrix1 = new Matrix(vectors);
        System.out.println("Создана матрица1: " + matrix1);
        System.out.println();

        System.out.println("Проверяем конструктор создания матрицы из массива массивов разной длины:");
        double[][] doubles = new double[][]{{}, {1.5, 2.0, 0.5, 4.0}, {}, {}, {}};
        Matrix matrix2 = new Matrix(doubles);
        System.out.println("Создана матрица2: " + matrix2);
        System.out.println();

        System.out.println("Проверяем конструктор создания матрицы нулей через указание количества строк и столбцов:");
        Matrix matrix3 = new Matrix(3, 4);
        System.out.println("Создана матрица3: " + matrix3);
        System.out.println();

        System.out.println("Проверяем конструктор копирования матрицы:");
        Matrix matrix4 = new Matrix(matrix1);
        System.out.println("Создана матрица4 путем копирования матрицы1: " + matrix4);
        System.out.println();

        System.out.println("Количество строк в матрице1 равно " + matrix1.getRowsCount());
        System.out.println("Количество столбцов в матрице1 равно " + matrix1.getColumnsCount());
        System.out.println();

        System.out.println("Вектор-строка с индексом 1 в матрице1: " + matrix1.getRow(1));
        matrix1.setRow(1, new Vector(new double[]{1, 2, 3, 4, 5}));
        System.out.println("Заменили в матрице1 вектор-строку с индексом 1: " + matrix1);
        System.out.println("Вектор-столбец с индексом 2 в матрице1: " + matrix1.getColumn(2));
        System.out.println();

        matrix1.transpose();
        System.out.println("Транспонировали матрицу1:" + matrix1);
        System.out.println();

        matrix1.multiplyByScalar(2);
        System.out.println("Умножили матрицу1 на скаляр: " + matrix1);
        System.out.println();

        Vector[] vectors2 = {
                new Vector(new double[]{3, -3, -5, 8}),
                new Vector(new double[]{-3, 2, 4, -6}),
                new Vector(new double[]{2, -5, -7, 5}),
                new Vector(new double[]{-4, 3, 5, -6})
        };
        Matrix matrix5 = new Matrix(vectors2);
        System.out.println("Создали квадратную матрицу5: " + matrix5);
        System.out.println("Определитель матрицы5 равен " + matrix5.getDeterminant());
        System.out.println();

        Vector vector3 = new Vector(new double[]{1, 2, 3});
        System.out.println("Результат умножения матрицы1 на вектор {1, 2, 3}: " + matrix1.multiplyByVector(vector3));
        System.out.println();

        Matrix matrix6 = new Matrix(matrix1);
        System.out.println("Создана матрица6 путем копирования матрицы1: " + matrix6);
        matrix1.add(matrix6);
        System.out.println("Матрица1 после сложения матрицы1 с матрицей 6: " + matrix1);
        matrix1.subtract(matrix6);
        System.out.println("Матрица1 после вычитания матрицы6 из матрицы1: " + matrix1);
        System.out.println();

        Matrix matrix7 = new Matrix(Matrix.getSum(matrix1, matrix6));
        System.out.println("Создана матрица7 как сумма матрицы1 и матрицы6: " + matrix7);
        Matrix matrix8 = new Matrix(Matrix.getDifference(matrix1, matrix6));
        System.out.println("Создана матрица8 как разность матрицы1 и матрицы6: " + matrix8);
        System.out.println();

        Vector[] vectors3 = {
                new Vector(new double[]{2, 2, 2, 3, 3}),
                new Vector(new double[]{3, 3, 2, 2, 2})
        };
        Matrix matrix9 = new Matrix(vectors3);
        System.out.println("Создана матрица9: " + matrix9);
        System.out.println("Результат умножения матрицы9 на матрицу1: " + Matrix.getProduct(matrix9, matrix1));
    }
}