package team.project.weather;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.Date;
import java.util.StringTokenizer;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import team.project.weather.model.Day;
import team.project.weather.model.ResponseParts.Weather;
import team.project.weather.model.WeatherResponse;
import team.project.weather.service.WeatherService;

public class OpenWeatherService implements WeatherService {

    private static final String TAG = "Service";
    private static final String APPID = "8673a08097591104b9aa183591c4a5ff";
    private static final String MAIN_URL = "http://api.openweathermap.org/data/2.5/weather";

    @Override
    public Day getCurrentDay(double lat, double lon) throws Exception {
        String res = this.getWeatherByCoordinates(lat, lon);
        WeatherResponse weather = this.parseResponse(res);

        Day current = new Day();
        current.setLocationCity(weather.getName());
        current.setTemperature((float) weather.getMain().getTemp());
        // current.setWeather();
        current.setWindSpeed((float) weather.getWind().getSpeed());
        current.setLastUpdated(new Date());

        // To test the exception handling uncomment the code below
        // and comment the return statement
        // throw new Exception("Test");

        return current;
    }

    // HTTP Requests don't work on the main thread!!!
    public void testApi() throws Exception{
        OkHttpClient client = new OkHttpClient();
        String url = "http://api.openweathermap.org/data/2.5/weather?q=Sofia&appid=8673a08097591104b9aa183591c4a5ff";

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = null;

        response = client.newCall(request).execute();
        String res = response.body().string();
        Log.d(TAG, res);
    }

    public String getWeatherByCoordinates(double lat, double lon) throws IOException {
        String url = this.getUrlByCoordinates(lat, lon);
        Response res = this.simpleGetHttpRequest(url);
        return res.body().string();
    }

    private WeatherResponse parseResponse(String res) {
        Gson gson = new Gson();
        WeatherResponse wr = gson.fromJson(res, WeatherResponse.class);

        return wr;
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
