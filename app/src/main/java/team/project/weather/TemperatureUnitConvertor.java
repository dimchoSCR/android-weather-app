package team.project.weather;

public class TemperatureUnitConvertor {

    public static double kelvinToCelsius(double kelvin) {
        return Math.round((kelvin - 273.15) * 10.0 ) / 10.0;
    }

    public static double kelvinToFahrenheit(double kelvin) {
        return Math.round((kelvin * 1.8 - 459.67) * 10.0 ) / 10.0;
    }

    public static double celsiusToFahrenheit(double celsius) {
        return Math.round(((celsius * 1.8) + 32) *10.0)/10.0;
    }

    public static double celsiusToKelvin(double celsius) {
        return celsius + 273.15;
    }

    public static double fahrenheitToCelsius(double fahrenheit) {
        return Math.round(((fahrenheit/1.8) - 32 )*10.0)/10.0;
    }

    public static double fahrenheitToKelvin(double fahrenheit) {
        return (fahrenheit + 459.67) * (5./9.);
    }
}
