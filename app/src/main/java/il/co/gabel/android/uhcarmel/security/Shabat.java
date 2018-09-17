package il.co.gabel.android.uhcarmel.security;

public class Shabat {
    private Boolean status;
    private String address;
    private String city;
    private String commnet;

    public Shabat(){}

    public Shabat(Boolean status, String address,String city, String commnet){
        this.status=status;
        this.address=address;
        this.city=city;
        this.commnet=commnet;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCommnet() {
        return commnet;
    }

    public void setCommnet(String commnet) {
        this.commnet = commnet;
    }
}
