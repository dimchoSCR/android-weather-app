package team.project.weather;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import team.project.weather.interfaces.IHttpRequester;

public class HttpRequester implements IHttpRequester {

    public HttpRequester() {
    }

    @Override
    public String getWeatherByCoordinates(double lat, double lon) throws IOException {
        UrlBuilder urlBuilder = new UrlBuilder();

        String url = urlBuilder.getUrlByCoordinates(lat, lon);
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
}
