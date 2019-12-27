package com.example.personstory.checkDbIsExisting;

import android.content.Context;
import android.content.SharedPreferences;

public class CheckDbIsExistingSP {

    public static SharedPreferences preferences2;
    private String RES_ID_DB = "resIdDb";

    public CheckDbIsExistingSP(Context context) {
        preferences2 = context.getSharedPreferences("checkDbIsExisting", Context.MODE_PRIVATE);
    }

    public void DbIsExisting(Boolean result) {
        SharedPreferences.Editor editor = preferences2.edit();
        editor.putBoolean(RES_ID_DB, result).apply();
    }
    public boolean getDBresult() {
        return preferences2.getBoolean(RES_ID_DB, false);
    }
}
