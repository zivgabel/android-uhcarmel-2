package il.co.gabel.android.uhcarmel;

import android.content.Context;
import android.content.SharedPreferences;

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


}
