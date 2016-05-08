package team.project.weather;

import android.content.SharedPreferences;

import java.util.Date;

import team.project.weather.interfaces.IHttpRequester;
import team.project.weather.interfaces.IJsonConverter;
import team.project.weather.model.Day;
import team.project.weather.model.WeatherResponse;
import team.project.weather.service.WeatherService;

public class OpenWeatherService implements WeatherService {

    private IJsonConverter jsonConverter;
    private IHttpRequester httpRequester;
    private SharedPreferences sharedPref;

    public OpenWeatherService(SharedPreferences sharedPref) {
        this.sharedPref = sharedPref;
        this.jsonConverter = new JsonConverter();
        this.httpRequester = new HttpRequester();
    }

    public OpenWeatherService(IJsonConverter jsonConverter) {

        this.jsonConverter = jsonConverter;
        this.httpRequester = new HttpRequester();
    }

    public OpenWeatherService(IHttpRequester httpRequester) {
        this.httpRequester = httpRequester;
        this.jsonConverter = new JsonConverter();
    }

    public OpenWeatherService(IJsonConverter jsonConverter, IHttpRequester httpRequester) {
        this.jsonConverter = jsonConverter;
        this.httpRequester = httpRequester;
    }

    @Override
    public Day getCurrentDay(double lat, double lon) throws Exception {

        // Get the preference from the SharedPreferences file
        String tempUnitPref = sharedPref.getString(SettingsFragment.TEMP_UNIT_KEY,"");

        String res = this.httpRequester.getWeatherByCoordinates(lat, lon);
        WeatherResponse weather = this.jsonConverter.jsonToWeatherResponse(res);

        Day current = new Day();
        current.setLocationCity(weather.getName());

        switch(tempUnitPref){
            case "Celsius":
                current.setTemperature((float)TemperatureUnitConvertor.
                    kelvinToCelsius(weather.getMain().getTemp()));
                current.setTemperatureUnit("C");
                break;
            case "Fahrenheit":
                current.setTemperature((float)TemperatureUnitConvertor.
                        kelvinToFahrenheit(weather.getMain().getTemp()));
                current.setTemperatureUnit("F");
                break;
        }

        int weatherConditionsCode = weather.getWeather()[0].getId();

        current.setWeather(this.getWeatherConditionsByCode(weatherConditionsCode));
        current.setWindSpeed((float) weather.getWind().getSpeed());
        current.setLastUpdated(new Date());

        return current;
    }

    // Weather codes: http://openweathermap.org/weather-conditions
    private Day.Weather getWeatherConditionsByCode(int code) {

        int firstDigit = code / 100;
        switch (firstDigit) {
            case 2: return Day.Weather.RAINING;
            case 3: return Day.Weather.RAINING;
            case 5: return Day.Weather.RAINING;
            case 6: return Day.Weather.SNOWING;
            case 7: return Day.Weather.CLOUDY;
            case 8: {
                if (code == 800) {
                    return Day.Weather.SUNNY;
                } else {
                    return Day.Weather.CLOUDY;
                }
            }

            case 9: {
                if (code == 900 || code == 901 || code == 902 || code == 906) {
                    return Day.Weather.RAINING;
                } else if (code == 903) {
                    return Day.Weather.SNOWING;
                } else if (code == 904) {
                    return Day.Weather.SUNNY;
                } else if (code == 905) {
                    return Day.Weather.CLOUDY;
                } else if (code >= 951 && code < 960) {
                    return Day.Weather.CLOUDY;
                } else {
                    return Day.Weather.RAINING;
                }
            }

            default: return Day.Weather.SUNNY;
        }
    }
}
