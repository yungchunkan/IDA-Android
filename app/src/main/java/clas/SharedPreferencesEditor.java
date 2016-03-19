package clas;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by sai on 15年3月16日.
 */
public class SharedPreferencesEditor {
    private final String PREF_USER = "PREF_USER";

    private Context context;
    private SharedPreferences sharedPreferences;

    public SharedPreferencesEditor(Context context) {
        this.context = context;
    }

    private void open() {
        //sharedPreferences = this.context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setUser(String name) {
        open();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PREF_USER, name);
        editor.apply();
    }

    public String getUser() {
        open();
        return sharedPreferences.getString(PREF_USER, "");
    }
}
