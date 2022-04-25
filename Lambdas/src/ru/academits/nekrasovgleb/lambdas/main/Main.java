package ru.academits.nekrasovgleb.lambdas.main;

import ru.academits.nekrasovgleb.lambdas.Person;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        ArrayList<Person> persons = new ArrayList<>(Arrays.asList(
                new Person("Владимир", 23),
                new Person("Анна", 18),
                new Person("Иван", 35),
                new Person("Данил", 27),
                new Person("Виктория", 34),
                new Person("Иван", 14),
                new Person("Виктор", 16),
                new Person("Светлана", 43),
                new Person("Владимир", 44),
                new Person("Светлана", 25),
                new Person("Наталья", 17),
                new Person("Дарья", 32),
                new Person("Иван", 57),
                new Person("Алексей", 46)
        ));

        // пункт А - получить список уникальных имен
        List<String> distinctNames = persons.stream().map(Person::getName).distinct().collect(Collectors.toList());

        // пункт Б - вывести список уникальных имен в формате: Имена: Иван, Сергей, Петр.
        System.out.println(distinctNames.stream().collect(Collectors.joining(", ", "Имена: ", ".")));
        System.out.println();

        // пункт В - получить список людей младше 18, посчитать для них средний возраст
        List<Person> youngPersons = persons.stream().filter(x -> x.getAge() < 18).collect(Collectors.toList());
        OptionalDouble youngPersonsAverageAge = youngPersons.stream().mapToDouble(Person::getAge).average();

        if (youngPersonsAverageAge.isPresent()) {
            System.out.println("Средний возраст людей младше 18 лет: " + youngPersonsAverageAge.getAsDouble());
        } else {
            System.out.println("Людей младше 18 лет нет.");
        }

        System.out.println();

        // пункт Г - при помощи группировки получить Map, в котором ключи имена, а значения средний возраст
        Map<String, Double> averageAgeByNames =
                persons.stream().collect(Collectors.groupingBy(Person::getName, Collectors.averagingInt(Person::getAge)));
        System.out.println("Средний возраст людей с одинаковым именем:");
        Stream.of(averageAgeByNames).forEach(x -> System.out.println(x.entrySet()));
        System.out.println();

        // пункт Д - получить людей, возраст которых от 20 до 45, вывести в консоль их имена в порядке убывания возраста
        List<Person> from20To45Persons = persons.stream()
                .filter(x -> (x.getAge() >= 20 & x.getAge() <= 45))
                .sorted((p1, p2) -> p2.getAge() - p1.getAge())
                .collect(Collectors.toList());

        System.out.println("Список людей в возрасте от 20 до 45 лет:");
        from20To45Persons.forEach(p -> System.out.println(p.getName() + " - " + p.getAge()));
    }
}