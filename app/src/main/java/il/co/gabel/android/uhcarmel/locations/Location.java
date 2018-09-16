package il.co.gabel.android.uhcarmel.locations;

public class Location {

    private String name;
    private Float altitude;
    private Float longitude;

    public Location(){}


    public String getWazeUrl(){
        return "https://waze.com/ul?ll="+ String.valueOf(altitude) + "," + String.valueOf(longitude);
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getAltitude() {
        return altitude;
    }

    public void setAltitude(Float altitude) {
        this.altitude = altitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }
}
