package team.project.weather.service;

import team.project.weather.model.Day;

public interface WeatherService {
    Day getCurrentDay(double lat,double lon) throws Exception;
}
