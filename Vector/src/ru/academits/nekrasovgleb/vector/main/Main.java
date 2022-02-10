package ru.academits.nekrasovgleb.vector.main;

import ru.academits.nekrasovgleb.vector.Vector;

public class Main {
    public static void main(String[] args) {
        Vector vector1 = new Vector(3);
        System.out.println("Создан пустой вектор1: " + vector1);

        vector1 = new Vector(new double[]{1.5, 0.5, 2.5});
        System.out.println("Пустой вектор1 заполнен новыми значениями компонент: " + vector1);

        Vector vector2 = new Vector(vector1);
        System.out.println("Создан новый вектор2 - копия вектора1: " + vector2);

        vector2 = new Vector(5, new double[]{2.0, 2.0, 2.0, 2.0});
        System.out.println("В векторе2 изменены все компоненты и увеличена размерность: " + vector2);
        System.out.println("Размерность вектора2 теперь равна " + vector2.getSize());
        System.out.println("Проверяем, что вектор1 не изменился: " + vector1);

        vector2.subtract(vector1);
        System.out.println("После вычитания из вектора2 вектора1 компоненты вектора2 равны: " + vector2);

        vector2.add(vector1);
        System.out.println("После прибавления к вектору2 вектора1 компоненты вектора2 стали прежними: " + vector2);

        vector2.multiplyByScalar(2);
        System.out.println("После умножения вектора2 на 2 компоненты вектора2 равны: " + vector2);

        vector2.reverse();
        System.out.println("После разворота вектора2 компоненты вектора2 равны: " + vector2);

        System.out.println("Длина вектора2 равна: " + vector2.getLength());

        vector2.setComponent(4, vector2.getComponent(0) * 2);
        System.out.println("Изменили четвертую компоненту вектора2 - установили в ней значение нулевой компоненты, " +
                "умноженной на 2: " + vector2);

        Vector vector3 = new Vector(new double[]{-4.0, -4.0, -4.0, -4.0, -8.0});
        System.out.println("Создан новый вектор3: " + vector3);

        if (vector3.equals(vector1)) {
            System.out.println("Вектор3 равен вектору1");
        } else {
            System.out.println("Вектор3 не равен вектору1");
        }

        if (vector3.equals(vector2)) {
            System.out.println("Вектор3 равен вектору2");
        } else {
            System.out.println("Вектор3 не равен вектору2");
        }

        System.out.printf("Хэш код вектора1: %d. Хэш код вектора2: %d. Хэш код вектора3: %d.",
                vector1.hashCode(), vector2.hashCode(), vector3.hashCode());
        System.out.println();

        System.out.println("Сумма вектора1 и вектора2 равна: " + Vector.getSum(vector1, vector2));
        System.out.println("Разность вектора1 и вектора2 равна: " + Vector.getDifference(vector1, vector2));
        System.out.println("Скалярное произведение вектора1 и вектора2 равно: " +
                Vector.getScalarProduct(vector1, vector2));
    }
}