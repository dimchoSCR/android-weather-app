package team.project.weather.interfaces;

import java.io.IOException;

/**
 * Created by Dimitar on 6.5.2016 г..
 */
public interface IHttpRequester {
    String getWeatherByCoordinates(double lat, double lon) throws IOException;
}
