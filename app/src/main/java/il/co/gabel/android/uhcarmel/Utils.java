package il.co.gabel.android.uhcarmel;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import il.co.gabel.android.uhcarmel.security.User;

public class Utils {

    private static SharedPreferences sp;
    private static SharedPreferences.Editor spe;
    private static FirebaseDatabase firebaseDatabase;



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
        String first_name=sp.getString(context.getString(R.string.sp_first_name),"");
        String last_name=sp.getString(context.getString(R.string.sp_last_name),"");

        User user = new User(mirs,isAdmin,isShabatAdmin,isWhAdmin,first_name,last_name);
        return user;
    }

    public static String getUserUID(Context context){
        SharedPreferences sp = getSharedPreferences(context);
        return sp.getString(context.getString(R.string.user_uid_key),"");
    }

    private static FirebaseDatabase getFBDatabase(){
        if(firebaseDatabase==null){
            firebaseDatabase = FirebaseDatabase.getInstance();
        }
        return firebaseDatabase;
    }

    public static DatabaseReference getFBDBReference(Context context){
        String prefix = context.getString(R.string.FIREBASE_DATABASE_PREFIX);
        return getFBDatabase().getReference().child(prefix);
    }



}
