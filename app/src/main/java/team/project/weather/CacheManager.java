package team.project.weather;

import java.util.Date;

import team.project.weather.model.Day;
import team.project.weather.service.WeatherStorageService;

public class CacheManager implements WeatherStorageService{
    @Override
    public void store(Day day) throws Exception {
        //TODO Store a day into the cache
    }

    @Override
    public Day retrieve() throws Exception {
        //TODO retrieve the data from the cache

        // This is an example of the data the method should return when ready
        Day currentDay = new Day();
        currentDay.setLocationCity("Sofia");
        currentDay.setTemperature(26f);
        currentDay.setWeather(Day.Weather.CLOUDY);
        currentDay.setWindSpeed(5.3f);
        currentDay.setLastUpdated(new Date());

        // To test the exception handling uncomment the code below
        // and comment the return statement
        //throw new Exception("Test");

        return currentDay;
    }
}
