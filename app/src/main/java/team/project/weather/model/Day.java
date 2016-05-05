package team.project.weather.model;

import java.io.Serializable;
import java.util.Date;

import team.project.weather.R;

public class Day implements Serializable{

    public enum Weather {
        SUNNY("Sunny",R.mipmap.ic_sunny),
        CLOUDY("Cloudy", R.mipmap.ic_cloudy),
        RAINING("Raining",R.mipmap.ic_raining),
        SNOWING("Snowing",R.mipmap.ic_snowing);

        private String weatherName;
        private int iconID;

        Weather(String name, int iconID){
            weatherName = name;
            this.iconID = iconID;
        }

        public String getWeatherName(){
            return weatherName;
        }

        public int getIconID(){
            return iconID;
        }
    }

    private float temperature = 0; // in deg C
    private String locationCity = "Sofia"; // city name
    //String locationCountry; // country name
    private Weather weather = Weather.CLOUDY;
    private float windSpeed = 0; // in m/s
    private Date lastUpdated = new Date(0);

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public String getLocationCity() {
        return locationCity;
    }

    public void setLocationCity(String locationCity) {
        this.locationCity = locationCity;
    }

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    public float getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(float windSpeed) {
        this.windSpeed = windSpeed;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
