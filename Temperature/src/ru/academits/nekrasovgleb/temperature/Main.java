package ru.academits.nekrasovgleb.temperature;

import ru.academits.nekrasovgleb.temperature.converter.TemperatureConverter;
import ru.academits.nekrasovgleb.temperature.view.TemperatureWindow;
import ru.academits.nekrasovgleb.temperature.view.View;

public class Main {
    public static void main(String[] args) {
        TemperatureConverter temperatureConverter = new TemperatureConverter();
        View view = new TemperatureWindow(temperatureConverter);
        view.start();
    }
}