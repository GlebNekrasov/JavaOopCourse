package ru.academits.nekrasovgleb.temperature;

import ru.academits.nekrasovgleb.temperature.converter.*;
import ru.academits.nekrasovgleb.temperature.view.TemperatureWindow;
import ru.academits.nekrasovgleb.temperature.view.View;

public class Main {
    public static void main(String[] args) {
        TemperatureScale[] scales = {new CelsiusScale(), new FahrenheitScale(), new KelvinScale()};
        TemperatureConverter temperatureConverter = new TemperatureConverter(scales);
        View view = new TemperatureWindow(temperatureConverter);
        view.start();
    }
}