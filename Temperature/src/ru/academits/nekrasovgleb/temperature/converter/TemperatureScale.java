package ru.academits.nekrasovgleb.temperature.converter;

public abstract class TemperatureScale {
    public abstract double convertToCelsius(double temperature);

    public abstract double convertFromCelsius(double temperature);

    @Override
    public abstract String toString();
}
