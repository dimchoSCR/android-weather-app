package team.project.weather;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Date;

import team.project.weather.model.Day;

public class CacheManagerTest {
    private static final String CITY_NAME = "Sofia";
    private static final float TEMPERATURE =  11.3f;
    private static final Day.Weather WEATHER = Day.Weather.RAINING;
    private static final Date DATE = new Date();
    private static final float WIND_SPEED = 3.4f;

    private CacheManager cacheManager;

    @Before
    public void initialize() throws Exception{
        //cacheManager = new CacheManager();

        Day day = new Day();
        day.setLocationCity(CITY_NAME);
        day.setTemperature(TEMPERATURE);
        day.setWeather(WEATHER);
        day.setLastUpdated(DATE);
        day.setWindSpeed(WIND_SPEED);

        cacheManager.store(day);
    }

    @Test
    public void testCache() throws Exception{
        Day currentDay = cacheManager.retrieve();

        assertNotNull(currentDay);
        assertEquals("City name does not match",currentDay.getLocationCity(),CITY_NAME);
        assertThat("Temperature does not match",currentDay.getTemperature(), CoreMatchers.equalTo(TEMPERATURE));
        assertEquals("Weather info does not match",currentDay.getWeather(),WEATHER);
        assertEquals("Date does not match",currentDay.getLastUpdated().toString(),DATE.toString());
        assertThat("Wind speed does not match",currentDay.getWindSpeed(), CoreMatchers.equalTo(WIND_SPEED));

    }
}
