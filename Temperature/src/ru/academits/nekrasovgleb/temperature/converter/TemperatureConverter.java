package ru.academits.nekrasovgleb.temperature.converter;

public class TemperatureConverter {
    public static final TemperatureScale[] SCALES = {new CelsiusScale(), new FahrenheitScale(), new KelvinScale()};

    private TemperatureScale scaleFrom = new CelsiusScale();
    private TemperatureScale scaleTo = new CelsiusScale();

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