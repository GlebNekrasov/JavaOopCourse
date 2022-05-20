package ru.academits.nekrasovgleb.temperature.converter;

import java.util.Arrays;

public class TemperatureConverter {
    private final TemperatureScale[] scales;

    private TemperatureScale scaleFrom;
    private TemperatureScale scaleTo;

    public TemperatureConverter(TemperatureScale[] scales) {
        if (scales.length == 0) {
            throw new IllegalArgumentException("При создании модели конвертера передан пустой массив температурных шкал." +
                    "Массив должен содержать не меньше 1 шкалы.");
        }

        this.scales = Arrays.copyOf(scales, scales.length);
        scaleFrom = scales[0];
        scaleTo = scales[0];
    }

    public TemperatureScale[] getScales() {
        return scales;
    }

    public void setScaleFrom(TemperatureScale scaleFrom) {
        this.scaleFrom = scaleFrom;
    }

    public void setScaleTo(TemperatureScale scaleTo) {
        this.scaleTo = scaleTo;
    }

    public double convertTemperature(double enteredTemperature) {
        double celsiusTemperature = scaleFrom.convertToCelsius(enteredTemperature);
        return scaleTo.convertFromCelsius(celsiusTemperature);
    }
}