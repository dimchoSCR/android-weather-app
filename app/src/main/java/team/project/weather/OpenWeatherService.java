package team.project.weather;

import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;

import java.util.Date;

import team.project.weather.interfaces.IHttpRequester;
import team.project.weather.interfaces.IJsonConverter;
import team.project.weather.model.Day;
import team.project.weather.model.WeatherResponse;
import team.project.weather.service.WeatherService;

public class OpenWeatherService implements WeatherService {

    private static final String TAG = "Service";
    private static final String APPID = "8673a08097591104b9aa183591c4a5ff";
    private static final String MAIN_URL = "http://api.openweathermap.org/data/2.5/weather";

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


        // TODO: Get the current weather conditions and parse them
        // current.setWeather();
        current.setWindSpeed((float) weather.getWind().getSpeed());
        current.setLastUpdated(new Date());

        return current;
    }
}
