package team.project.weather;

import java.util.Date;

import team.project.weather.model.Day;
import team.project.weather.service.WeatherService;

public class OpenWeatherService implements WeatherService{

    @Override
    public Day getCurrentDay(float lat, float lon) throws Exception {
        // TODO Get the weather data using OpenWeatherMap API and parse it

        //  Example data
        Day currentDay = new Day();
        currentDay.setLocationCity("Johannesburg");
        currentDay.setTemperature(27.9f);
        currentDay.setWeather(Day.Weather.SUNNY);
        currentDay.setWindSpeed(10.2f);
        currentDay.setLastUpdated(new Date());

        // To test the exception handling uncomment the code below
        // and comment the return statement
        // throw new Exception("Test");

        return currentDay;
    }
}
