package il.co.gabel.android.uhcarmel.security;

public class User {
    private int mirs;
    private Boolean admin;
    private Boolean shabat_admin;
    private Boolean wh_admin;

    public User(){}

    public int getMirs() {
        return mirs;
    }

    public void setMirs(int mirs) {
        this.mirs = mirs;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public Boolean getShabat_admin() {
        return shabat_admin;
    }

    public void setShabat_admin(Boolean shabat_admin) {
        this.shabat_admin = shabat_admin;
    }

    public Boolean getWh_admin() {
        return wh_admin;
    }

    public void setWh_admin(Boolean wh_admin) {
        this.wh_admin = wh_admin;
    }
    
}
