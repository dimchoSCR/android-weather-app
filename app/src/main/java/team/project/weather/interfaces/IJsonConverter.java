package team.project.weather.interfaces;

import team.project.weather.model.WeatherResponse;

/**
 * Created by Dimitar on 5.5.2016 г..
 */
public interface IJsonConverter {
    WeatherResponse jsonToWeatherResponse(String jsonResponse);
}
