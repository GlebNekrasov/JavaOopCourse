package ru.academits.nekrasovgleb.csvconverter;

import java.io.*;

public class CsvConverter {
    private String csvFileName;
    private String htmlFileName;
    private String htmlTitle;
    private boolean isBeginningLine;
    private boolean isEndLine;
    private boolean isBeginningCell;
    private boolean isEndCell;
    private boolean isCellInQuotes;
    private boolean isPreviousSymbolQuote;

    public CsvConverter() {
    }

    public void convertCsv(String csvFileName, String htmlFileName, String htmlTitle) {
        this.csvFileName = csvFileName;
        this.htmlFileName = htmlFileName;
        this.htmlTitle = htmlTitle;

        if (!checkCsvFile()) {
            throw new IllegalArgumentException("В качестве аргумента передано название CSV-файла, который не удалось прочитать.");
        }

        writeOpeningTags();

        isBeginningCell = true;
        isEndCell = false;
        isCellInQuotes = false;
        isPreviousSymbolQuote = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(csvFileName))) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(htmlFileName, true))) {
                String nextSymbol;

                for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                    if (line.length() == 0) {
                        isBeginningLine = true;
                        isEndLine = true;
                        nextSymbol = "";
                        String convertedSymbol = convertSymbol(nextSymbol);
                        writer.write(convertedSymbol);
                        continue;
                    }

                    for (int i = 0; i < line.length(); ++i) {
                        nextSymbol = Character.toString(line.charAt(i));
                        isBeginningLine = i == 0;
                        isEndLine = i == line.length() - 1;
                        String convertedSymbol = convertSymbol(nextSymbol);
                        writer.write(convertedSymbol);
                    }
                }
            } catch (IOException e) {
                System.exit(0);
            }
        } catch (IOException e2) {
            System.exit(0);
        }

        writeClosingTags();
    }

    private String convertSymbol(String symbol) {
        switch (symbol) {
            case "<" -> symbol = "&lt";
            case ">" -> symbol = "&gt";
            case "&" -> symbol = "&amp";
        }

        StringBuilder stringBuilder = new StringBuilder();

        if (isBeginningLine && isBeginningCell) {
            stringBuilder.append("<tr>");
        }

        if (isBeginningCell) {
            stringBuilder.append("<td>");

            if (symbol.equals("\"")) {
                symbol = "";
                isCellInQuotes = true;
                isBeginningCell = false;
            } else if (symbol.equals(",")) {
                symbol = "";
                isEndCell = true;
            } else if (isEndLine) {
                isEndCell = true;
                isBeginningCell = false;
            } else {
                isBeginningCell = false;
            }

            stringBuilder.append(symbol);
        } else {
            if (!isCellInQuotes) {
                if (symbol.equals("\"")) {
                    if (isPreviousSymbolQuote) {
                        symbol = "\"";
                        isPreviousSymbolQuote = false;
                    } else {
                        symbol = "";
                        isPreviousSymbolQuote = true;
                    }

                    stringBuilder.append(symbol);
                } else if (symbol.equals(",")) {
                    symbol = "";
                    isEndCell = true;
                    stringBuilder.append(symbol);
                } else {
                    stringBuilder.append(symbol);
                }

                if (isEndLine) {
                    isEndCell = true;
                }
            } else if (symbol.equals("\"")) {
                if (isPreviousSymbolQuote) {
                    symbol = "\"";
                    isPreviousSymbolQuote = false;
                } else if (isEndLine) {
                    symbol = "";
                    isCellInQuotes = false;
                    isEndCell = true;
                } else {
                    symbol = "";
                    isPreviousSymbolQuote = true;
                }

                stringBuilder.append(symbol);
            } else if (symbol.equals(",")) {
                if (isPreviousSymbolQuote) {
                    symbol = "";
                    isPreviousSymbolQuote = false;
                    isCellInQuotes = false;
                    isEndCell = true;
                } else {
                    symbol = ",";
                }

                stringBuilder.append(symbol);
            } else {
                stringBuilder.append(symbol);
            }
        }

        if (isEndCell) {
            stringBuilder.append("</td>");
        }

        if (isEndLine && isEndCell) {
            stringBuilder.append("</tr>");
        }

        if (isEndLine && !isEndCell) {
            stringBuilder.append("<br/>");
        }

        if (isEndCell) {
            isEndCell = false;
            isBeginningCell = true;
        }

        return stringBuilder.toString();
    }

    private boolean checkCsvFile() {
        try (FileReader reader = new FileReader(csvFileName)) {
            int firstSymbol = reader.read();

            if (firstSymbol == -1) {
                throw new IllegalArgumentException("В качестве аргумента передано название пустого CSV-файла. " +
                        "Файл должен содержать не меньше 1 символа.");
            }

            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private void writeOpeningTags() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(htmlFileName))) {
            writer.write("<!DOCTYPE html>");
            writer.newLine();
            writer.write("<html>");
            writer.newLine();
            writer.write("<head>");
            writer.newLine();
            writer.write("<meta charset=\"UTF-8\">");
            writer.newLine();
            writer.write("<title>" + htmlTitle + "</title>");
            writer.newLine();
            writer.write("</head>");
            writer.newLine();
            writer.write("<body>");
            writer.newLine();
            writer.write("<table>");
            writer.newLine();
        } catch (IOException e) {
            System.exit(0);
        }
    }

    private void writeClosingTags() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(htmlFileName, true))) {
            writer.newLine();
            writer.write("</table>");
            writer.newLine();
            writer.write("</body>");
            writer.newLine();
            writer.write("</html>");
        } catch (IOException e) {
            System.exit(0);
        }
    }
}