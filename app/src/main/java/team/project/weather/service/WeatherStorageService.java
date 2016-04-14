package team.project.weather.service;

import team.project.weather.model.Day;

public interface WeatherStorageService {
    void store(Day day)throws Exception;
    Day retrieve() throws Exception;
}
