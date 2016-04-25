package team.project.weather;

import android.util.Log;

import java.util.Date;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import team.project.weather.model.Day;
import team.project.weather.service.WeatherService;

public class OpenWeatherService implements WeatherService {

    private static final String TAG = "Service";

    @Override
    public Day getCurrentDay(float lat, float lon) throws Exception {
        // TODO Get the weather data using OpenWeatherMap API and parse it

        //  Example data
        Day currentDay = new Day();
        currentDay.setLocationCity("Johannesburg");
        currentDay.setTemperature(27.9f);
        currentDay.setWeather(Day.Weather.SUNNY);
        currentDay.setWindSpeed(10.2f);
        currentDay.setLastUpdated(new Date());

        // To test the exception handling uncomment the code below
        // and comment the return statement
        // throw new Exception("Test");

        return currentDay;
    }

    // HTTP Requests don't work on the main thread!!!
    public void testApi() {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                String url = "http://api.openweathermap.org/data/2.5/weather?q=Sofia&appid=8673a08097591104b9aa183591c4a5ff";

                Request request = new Request.Builder()
                        .url(url)
                        .build();

                Response response = null;

                try {
                    response = client.newCall(request).execute();
                    String res = response.body().string();
                    Log.d(TAG, res);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }
}
