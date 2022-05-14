package ru.academits.nekrasovgleb.csvconverter;

import java.io.*;
import java.util.LinkedList;

public class CsvConverter {
    private boolean isNewLine = true;
    private boolean isFinishedCell = true;
    private int firstCharIndex;
    private int lastCharIndex;

    public CsvConverter() {
    }

    public void convertCsv(String fileName) {
        LinkedList<String> csvLinesList = readCsvFile(fileName);

        if (csvLinesList.isEmpty()) {
            throw new IllegalArgumentException("В качестве аргумента передана пустая таблица");
        }

        LinkedList<String> htmlLinesList = new LinkedList<>();
        htmlLinesList.add("<table>");

        for (String csvLine : csvLinesList) {
            htmlLinesList.add(convertCsvLine(csvLine, isNewLine));
        }

        htmlLinesList.add("</table>");
        writeHtmlFile(htmlLinesList);
    }

    private LinkedList<String> readCsvFile(String fileName) {
        LinkedList<String> linesList = new LinkedList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                linesList.add(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Не удалось найти файл: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("При попытке прочитать файл произошла ошибка: " + e.getMessage());
        }

        return linesList;
    }

    private void writeHtmlFile(LinkedList<String> htmlLinesList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("HTML_Table.html"))) {
            for (String line : htmlLinesList) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("При попытке записать данные в файл произошла ошибка: " + e.getMessage());
        }
    }

    private String convertCsvLine(String csvLine, boolean isNewLine) {
        StringBuilder stringBuilder = new StringBuilder();

        firstCharIndex = 0;
        lastCharIndex = 0;

        if (isNewLine) {
            stringBuilder.append("<tr>");
        } else {
            String cellContinuation = getCellContinuation(csvLine);
            stringBuilder.append(cellContinuation);

            if (isFinishedCell) {
                stringBuilder.append("</td>");
            } else {
                this.isNewLine = false;
                return stringBuilder.toString();
            }
        }

        while (lastCharIndex < csvLine.length()) {
            stringBuilder.append("<td>");
            String nextCell = getNextCell(csvLine, firstCharIndex);
            stringBuilder.append(nextCell);

            if (isFinishedCell) {
                stringBuilder.append("</td>");
            } else {
                this.isNewLine = false;
                return stringBuilder.toString();
            }
        }

        stringBuilder.append("</tr>");
        this.isNewLine = true;
        return stringBuilder.toString();
    }

    private String getCellContinuation(String csvLine) {
        int firstIndexWithoutQuote = firstCharIndex;

        while (true) {
            lastCharIndex = csvLine.indexOf("\"", firstIndexWithoutQuote);
            String cellContinuation;

            if (lastCharIndex == -1) {
                cellContinuation = csvLine.substring(firstCharIndex);
                cellContinuation = cellContinuation.replace("\"\"", "\"");
                cellContinuation = cellContinuation + "<br/>";
                isFinishedCell = false;
                lastCharIndex = csvLine.length();
                return cellContinuation;
            }

            if (lastCharIndex == csvLine.length() - 1) {
                cellContinuation = csvLine.substring(firstCharIndex, lastCharIndex);
                cellContinuation = cellContinuation.replace("\"\"", "\"");
                isFinishedCell = true;
                lastCharIndex = csvLine.length();
                return cellContinuation;
            }

            if (csvLine.charAt(lastCharIndex + 1) == '"') {
                firstIndexWithoutQuote = lastCharIndex + 2;
                continue;
            }

            if (csvLine.charAt(lastCharIndex + 1) == ',') {
                cellContinuation = csvLine.substring(firstCharIndex, lastCharIndex);
                cellContinuation = cellContinuation.replace("\"\"", "\"");
                isFinishedCell = true;
                firstCharIndex = lastCharIndex + 2;
                return cellContinuation;
            }
        }
    }

    private String getNextCell(String csvLine, int firstCharIndex) {
        if (firstCharIndex >= csvLine.length()) {
            isFinishedCell = true;
            lastCharIndex = csvLine.length();
            return "";
        }

        if (csvLine.charAt(firstCharIndex) == '"') {
            ++this.firstCharIndex;
            return getCellContinuation(csvLine);
        }

        lastCharIndex = csvLine.indexOf(",", firstCharIndex);

        if (lastCharIndex == firstCharIndex) {
            ++this.firstCharIndex;
            ++lastCharIndex;
            isFinishedCell = true;
            return "";
        }

        if (lastCharIndex == -1) {
            lastCharIndex = csvLine.length();
            isFinishedCell = true;
            return csvLine.substring(firstCharIndex);
        }

        this.firstCharIndex = lastCharIndex + 1;
        isFinishedCell = true;
        return csvLine.substring(firstCharIndex, lastCharIndex);
    }
}