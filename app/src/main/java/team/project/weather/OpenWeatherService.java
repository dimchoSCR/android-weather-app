package team.project.weather;

import java.io.IOException;
import java.util.Date;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import team.project.weather.interfaces.IJsonConverter;
import team.project.weather.model.Day;
import team.project.weather.model.WeatherResponse;
import team.project.weather.service.WeatherService;

public class OpenWeatherService implements WeatherService {

    private static final String TAG = "Service";
    private static final String APPID = "8673a08097591104b9aa183591c4a5ff";
    private static final String MAIN_URL = "http://api.openweathermap.org/data/2.5/weather";

    private IJsonConverter jsonConverter;

    public OpenWeatherService() {
        this.jsonConverter = new JsonConverter();
    }

    public OpenWeatherService(IJsonConverter jsonConverter) {
        this.jsonConverter = jsonConverter;
    }

    @Override
    public Day getCurrentDay(double lat, double lon) throws Exception {
        String res = this.getWeatherByCoordinates(lat, lon);
        WeatherResponse weather = this.jsonConverter.jsonToWeatherResponse(res);

        Day current = new Day();
        current.setLocationCity(weather.getName());
        current.setTemperature((float) weather.getMain().getTemp());

        // TODO: Get the current weather conditions and parse them
        // current.setWeather();
        current.setWindSpeed((float) weather.getWind().getSpeed());
        current.setLastUpdated(new Date());

        return current;
    }

    private String getWeatherByCoordinates(double lat, double lon) throws IOException {
        String url = this.getUrlByCoordinates(lat, lon);
        Response res = this.simpleGetHttpRequest(url);
        return res.body().string();
    }

    private Response simpleGetHttpRequest(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return  response;
    }

    private String getURLWithAppid() {
        String url = String.format("%s?appid=%s", this.MAIN_URL, this.APPID);
        return url;
    }

    private String getUrlByCity(String city) {
        String url = String.format("%s&q=%s", this.getURLWithAppid(), city);
        return url;
    }

    private String getUrlByCoordinates(double lat, double lon) {
        String url = String.format("%s&lat=%s&lon=%s", this.getURLWithAppid(), lat, lon);
        return url;
    }
}
