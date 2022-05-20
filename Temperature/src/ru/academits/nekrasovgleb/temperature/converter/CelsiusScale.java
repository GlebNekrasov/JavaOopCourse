package ru.academits.nekrasovgleb.temperature.converter;

public class CelsiusScale extends TemperatureScale {
    @Override
    public double convertToCelsius(double temperature) {
        return temperature;
    }

    @Override
    public double convertFromCelsius(double temperature) {
        return temperature;
    }

    @Override
    public String toString() {
        return "Цельсий";
    }
}