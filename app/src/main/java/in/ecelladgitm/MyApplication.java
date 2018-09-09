package in.ecelladgitm;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.firebase.database.FirebaseDatabase;

public class MyApplication extends Application {

    public static int theme;

    public static void setAppTheme (Activity activity) {
        if (theme == 0) {
            activity.setTheme(R.style.AppTheme);
        } else if (theme == 1) {
            activity.setTheme(R.style.LightAppTheme);
        } else if (theme == 2) {
            activity.setTheme(R.style.DarkAppTheme);
        } else if (theme == 3) {
            activity.setTheme(R.style.AutoAppTheme);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        SharedPreferences defaultSharedPreference = PreferenceManager.getDefaultSharedPreferences(this);
        theme = defaultSharedPreference.getInt("theme", 2);
    }

    /*static {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO);
    }*/
}
