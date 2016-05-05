package team.project.weather;

import com.google.gson.Gson;

import team.project.weather.interfaces.IJsonConverter;
import team.project.weather.model.WeatherResponse;

public class JsonConverter implements IJsonConverter {

    private Gson gson;

    public JsonConverter() {
        this.gson = new Gson();
    }

    @Override
    public WeatherResponse jsonToWeatherResponse(String jsonResponse) {
        WeatherResponse wr = this.gson.fromJson(jsonResponse, WeatherResponse.class);
        return wr;
    }
}
