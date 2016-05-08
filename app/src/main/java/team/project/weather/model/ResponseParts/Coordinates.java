package team.project.weather.model.responseParts;

public class Coordinates {
    private double lon;

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    private double lat;

    public Coordinates() {
        this.lat = 0.;
        this.lon = 0.;
    }

    public Coordinates(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }


    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}
