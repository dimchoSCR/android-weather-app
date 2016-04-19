package team.project.weather.model;

import java.util.Date;

import team.project.weather.R;

public class Day {

    public enum Weather{
        SUNNY("Sunny",1),
        CLOUDY("Cloudy", R.mipmap.ic_rain_s_cloudy),
        RAINING("Raining",2),
        SNOWING("Snowing",3);

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

    private float temperature; // in deg C
    private String locationCity; // city name
    //String locationCountry; // country name
    private Weather weather;
    private float windSpeed; // in m/s
    private Date lastUpdated;

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
