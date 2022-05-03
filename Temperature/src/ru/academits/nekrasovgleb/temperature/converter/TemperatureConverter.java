package ru.academits.nekrasovgleb.temperature.converter;

public class TemperatureConverter {
    public enum TemperatureScale {
        CELSIUS,
        KELVIN,
        FAHRENHEIT;

        @Override
        public String toString() {
            if (this == TemperatureScale.CELSIUS) {
                return "Цельсий";
            }

            if (this == TemperatureScale.KELVIN) {
                return "Кельвин";
            }

            return "Фаренгейт";
        }
    }

    private TemperatureScale scaleFrom = TemperatureScale.CELSIUS;
    private TemperatureScale scaleTo = TemperatureScale.CELSIUS;

    public void setScaleFrom(TemperatureScale scaleFrom) {
        this.scaleFrom = scaleFrom;
    }

    public void setScaleTo(TemperatureScale scaleTo) {
        this.scaleTo = scaleTo;
    }

    public double convertTemperature(double enteredTemperature) {
        if (scaleFrom == scaleTo) {
            return enteredTemperature;
        }

        double celsiusTemperature;

        if (scaleFrom == TemperatureScale.KELVIN) {
            celsiusTemperature = convertKelvinToCelsius(enteredTemperature);
        } else if (scaleFrom == TemperatureScale.FAHRENHEIT) {
            celsiusTemperature = convertFahrenheitToCelsius(enteredTemperature);
        } else {
            celsiusTemperature = enteredTemperature;
        }

        if (scaleTo == TemperatureScale.KELVIN) {
            return convertCelsiusToKelvin(celsiusTemperature);
        }

        if (scaleTo == TemperatureScale.FAHRENHEIT) {
            return convertCelsiusToFahrenheit(celsiusTemperature);
        }

        return celsiusTemperature;
    }

    public double convertCelsiusToKelvin(double celsiusTemperature) {
        return celsiusTemperature + 273.15;
    }

    public double convertCelsiusToFahrenheit(double celsiusTemperature) {
        return celsiusTemperature * 9 / 5 + 32;
    }

    public double convertKelvinToCelsius(double kelvinTemperature) {
        return kelvinTemperature - 273.15;
    }

    public double convertFahrenheitToCelsius(double fahrenheitTemperature) {
        return (fahrenheitTemperature - 32) * 5 / 9;
    }
}