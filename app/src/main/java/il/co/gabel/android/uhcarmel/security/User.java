package il.co.gabel.android.uhcarmel.security;

import android.content.Context;
import android.content.SharedPreferences;

import il.co.gabel.android.uhcarmel.R;
import il.co.gabel.android.uhcarmel.Utils;

public class User {
    private int mirs;
    private Boolean admin;
    private Boolean shabat_admin;
    private Boolean wh_admin;
    private String first_name;
    private String last_name;
    private Shabat shabat;



    public User(){}

    public User(int mirs, Boolean admin, Boolean shabat_admin, Boolean  wh_admin, String first_name, String last_name){
        this.admin=admin;
        this.mirs=mirs;
        this.shabat_admin=shabat_admin;
        this.wh_admin=wh_admin;
    }

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

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public Shabat getShabat() {
        return shabat;
    }

    public void setShabat(Shabat shabat) {
        this.shabat = shabat;
    }

    public void saveData(Context context){
        SharedPreferences.Editor editor = Utils.getSharedPreferencesEditor(context);
        editor.putBoolean(context.getString(R.string.is_admin), getAdmin());
        editor.putBoolean(context.getString(R.string.is_shabat_admin), getShabat_admin());
        editor.putBoolean(context.getString(R.string.is_wh_admin), getWh_admin());
        editor.putInt(context.getString(R.string.user_mirs), getMirs());
        editor.putString(context.getString(R.string.sp_last_name), getLast_name());
        editor.putString(context.getString(R.string.sp_first_name), getFirst_name());
        editor.commit();
    }
    public void removeData(Context context){
        SharedPreferences.Editor editor = Utils.getSharedPreferencesEditor(context);
        editor.remove(context.getString(R.string.is_admin));
        editor.remove(context.getString(R.string.is_shabat_admin));
        editor.remove(context.getString(R.string.is_wh_admin));
        editor.remove(context.getString(R.string.user_mirs));
        editor.remove(context.getString(R.string.sp_last_name));
        editor.remove(context.getString(R.string.sp_first_name));
        editor.commit();


    }
}
