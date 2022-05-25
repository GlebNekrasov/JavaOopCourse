package ru.academits.nekrasovgleb.csvconverter.main;

import ru.academits.nekrasovgleb.csvconverter.CsvConverter;

public class Main {
    public static void main(String[] args) {
        CsvConverter htmlTable = new CsvConverter();
        htmlTable.convertCsv("csv_test.csv", "HTML_table.html", "Тестовая таблица");
    }
}