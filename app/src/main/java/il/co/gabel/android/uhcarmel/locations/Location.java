package il.co.gabel.android.uhcarmel.locations;

public class Location {

    private String name;
    private Double altitude;
    private Double longitude;

    public Location(){}


    public String getWazeUrl(){
        return "https://waze.com/ul?ll="+ String.valueOf(altitude) + "," + String.valueOf(longitude);
    }

    public Location(String name, Double altitude, Double longitude){
        this.name=name;
        this.altitude=altitude;
        this.longitude=longitude;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAltitude() {
        return altitude;
    }

    public void setAltitude(Double altitude) {
        this.altitude = altitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
