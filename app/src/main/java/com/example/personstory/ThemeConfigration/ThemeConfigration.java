package com.example.personstory.ThemeConfigration;

import android.content.Context;
import android.content.SharedPreferences;

public class ThemeConfigration {

    private SharedPreferences preferences;
    public static SharedPreferences preferences2;
    private String RES_ID_Theme = "resIdTheme";
    private String RES_ID_DB = "resIdDb";

    public ThemeConfigration(Context context) {
        preferences = context.getSharedPreferences("myTheme", Context.MODE_PRIVATE);
        preferences2 = context.getSharedPreferences("checkDbIsExisting", Context.MODE_PRIVATE);
    }

    public void saveTheme(int resId) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(RES_ID_Theme, resId).apply();
    }

    public int getThemeId() {
        return preferences.getInt(RES_ID_Theme, 0);
    }


    public void DbIsExisting(Boolean result) {
        SharedPreferences.Editor editor = preferences2.edit();
        editor.putBoolean(RES_ID_DB, result).apply();
    }


    public boolean getDBresult() {
        return preferences2.getBoolean(RES_ID_DB, false);
    }


}
