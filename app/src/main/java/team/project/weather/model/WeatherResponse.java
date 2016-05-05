package team.project.weather.model;

import team.project.weather.model.responseParts.Clouds;
import team.project.weather.model.responseParts.Coordinates;
import team.project.weather.model.responseParts.Rain;
import team.project.weather.model.responseParts.SunsetSunrise;
import team.project.weather.model.responseParts.Weather;
import team.project.weather.model.responseParts.WeatherDetails;
import team.project.weather.model.responseParts.Wind;

public class WeatherResponse {

    private Coordinates coord;
    private Weather[] weather;
    private String base;
    private WeatherDetails main;
    private Wind wind;
    private Rain rain;
    private Clouds clouds;
    private long dt;
    private SunsetSunrise sys;
    private int id;
    private String name;
    private int cod;

    public WeatherResponse() {
    }

    public WeatherResponse(Coordinates coord, Weather[] weather, String base, WeatherDetails main,
                           Wind wind, Rain rain, Clouds clouds, long dt, SunsetSunrise sys, int id,
                           String name, int cod) {
        this.coord = coord;
        this.weather = weather;
        this.base = base;
        this.main = main;
        this.wind = wind;
        this.rain = rain;
        this.clouds = clouds;
        this.dt = dt;
        this.sys = sys;
        this.id = id;
        this.name = name;
        this.cod = cod;
    }

    public Coordinates getCoord() {
        return coord;
    }

    public void setCoord(Coordinates coord) {
        this.coord = coord;
    }

    public Weather[] getWeather() {
        return weather;
    }

    public void setWeather(Weather[] weather) {
        this.weather = weather;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public WeatherDetails getMain() {
        return main;
    }

    public void setMain(WeatherDetails main) {
        this.main = main;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Rain getRain() {
        return rain;
    }

    public void setRain(Rain rain) {
        this.rain = rain;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public long getDt() {
        return dt;
    }

    public void setDt(long dt) {
        this.dt = dt;
    }

    public SunsetSunrise getSys() {
        return sys;
    }

    public void setSys(SunsetSunrise sys) {
        this.sys = sys;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }
}
