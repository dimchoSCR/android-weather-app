package team.project.weather.model;

import java.util.Date;

public class Day {

    public enum Weather{
        SUNNY,CLOUDY,RAINING,SNOWING
    }

    float temperature; // in deg C
    String locationCity; // city name
    String locationCountry; // country name
    Weather weather;
    float windSpeed; // in m/s
    Date lastUpdated;
}
