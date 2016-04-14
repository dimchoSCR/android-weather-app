package team.project.weather.service;

import team.project.weather.model.Day;

public interface WeatherService {
    Day getCurrentDay(float lat,float lon) throws Exception;
}
