package team.project.weather;

import org.junit.Test;

import java.io.IOException;

import team.project.weather.model.WeatherResponse;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {

    private static final String APPID = "8673a08097591104b9aa183591c4a5ff";
    private static final String MAIN_URL = "http://api.openweathermap.org/data/2.5/weather";

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void url_build() {
        String url = String.format("%s?appid=%s", this.MAIN_URL, this.APPID);
        assertEquals(url, "http://api.openweathermap.org/data/2.5/weather?appid=8673a08097591104b9aa183591c4a5ff");
    }

    @Test
    public void url_by_city() {
        String url = String.format("%s&q=%s", "http://api.openweathermap.org/data/2.5/weather?appid=8673a08097591104b9aa183591c4a5ff", "Sofia");
        assertEquals(url, "http://api.openweathermap.org/data/2.5/weather?appid=8673a08097591104b9aa183591c4a5ff&q=Sofia");
    }

    @Test
    public void http_request() throws IOException {
        OpenWeatherService ows = new OpenWeatherService();
        String res = ows.getWeatherByCoordinates(42.67, 23.25);
        System.out.println(res);
        assertEquals(1, 1);
    }

    @Test
    public void parse_request() throws IOException {
        OpenWeatherService ows = new OpenWeatherService();
        String res = ows.getWeatherByCoordinates(42.67, 23.25);
        System.out.println(res);

        //WeatherResponse wr = ows.parseResponse(res);
        //System.out.println(wr.getMain().getTemp());
    }
}