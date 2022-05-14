package ru.academits.nekrasovgleb.temperature.converter;

public interface TemperatureScale {
    double convertToCelsius(double temperature);

    double convertFromCelsius(double temperature);

    @Override
    String toString();
}
