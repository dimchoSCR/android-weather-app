package team.project.weather;

public class UrlBuilder {

    private static final String APPID = "8673a08097591104b9aa183591c4a5ff";
    private static final String MAIN_URL = "http://api.openweathermap.org/data/2.5/weather";

    public UrlBuilder() {
    }

    public String getURLWithAppid() {
        String url = String.format("%s?appid=%s", this.MAIN_URL, this.APPID);
        return url;
    }

    public String getUrlByCity(String city) {
        String url = String.format("%s&q=%s", this.getURLWithAppid(), city);
        return url;
    }

    public String getUrlByCoordinates(double lat, double lon) {
        String url = String.format("%s&lat=%s&lon=%s", this.getURLWithAppid(), lat, lon);
        return url;
    }
}
