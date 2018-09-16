package il.co.gabel.android.uhcarmel;

import android.content.Context;
import android.content.SharedPreferences;

import il.co.gabel.android.uhcarmel.security.User;

public class Utils {

    private static SharedPreferences sp;
    private static SharedPreferences.Editor spe;



    public static SharedPreferences.Editor getSharedPreferencesEditor(Context context){
        if (spe!=null){
            return spe;
        }
        getSharedPreferences(context);
        spe = sp.edit();
        return spe;
    }

    public static SharedPreferences getSharedPreferences(Context context){
        if(sp!=null){
            return sp;
        }
        sp = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        return sp;
    }

    public static User currentUser(Context context){
        SharedPreferences sp = getSharedPreferences(context);
        int mirs = sp.getInt(context.getString(R.string.user_mirs),0);
        if(mirs==0){
            return null;
        }
        Boolean isAdmin=sp.getBoolean(context.getString(R.string.is_admin),false);
        Boolean isShabatAdmin=sp.getBoolean(context.getString(R.string.is_shabat_admin),false);
        Boolean isWhAdmin=sp.getBoolean(context.getString(R.string.is_wh_admin),false);

        User user = new User(mirs,isAdmin,isShabatAdmin,isWhAdmin);
        return user;
    }



}
