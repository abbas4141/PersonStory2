package com.example.personstory.SQLitePackage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;


import com.example.personstory.MainActivity;
import com.example.personstory.ShowAllStorys;
import com.example.personstory.ThemeConfigration.ThemeConfigration;
import com.example.personstory.personpackage.PersonModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static String dbName = "PersonDb.db3";
    private String dbPath, tableName = "PersonTable";
    private Context context;
    private SQLiteDatabase sqLiteDatabase;


    public SQLiteHelper(@Nullable Context context) {
        super(context, dbName, null, 1);
        this.context = context;
        dbPath = context.getApplicationInfo().dataDir + "/databases/";
        checkDbIsExistingOrNot();
    }

    private void checkDbIsExistingOrNot() {
        boolean DBisExisting= MainActivity.checkDbIsExistingSP.getDBresult();
        if (DBisExisting){
            //the Db is existing
            copyAndCheckExisting2();
        }else if (!DBisExisting){
            //the Db is not existing
            copyAndCheckExisting();
         MainActivity.checkDbIsExistingSP.DbIsExisting(true);
        }
    }

    private void copyAndCheckExisting() {
        getReadableDatabase();
        File file = new File(dbPath);
//        if (!file.exists()){
//            try {
        try {
            copyDbFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        close();
//        }
    }
    private void copyAndCheckExisting2() {
        getReadableDatabase();
        File file = new File(dbPath);
        if (!file.exists()){
//            try {
        try {
            copyDbFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        close();
        }
    }
    private void copyDbFile() throws IOException {
        InputStream inputStream = context.getAssets().open(dbName);
        OutputStream outputStream = new FileOutputStream(dbPath + dbName);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }
        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }

    public List<PersonModel> getAll() {
        sqLiteDatabase = getReadableDatabase();
        List<PersonModel> temp = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(tableName, null, null,
                null, null, null, null);
        while (cursor.moveToNext()) {
            PersonModel personModel =
                    new PersonModel(cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getBlob(3));
            temp.add(personModel);
        }
        cursor.close();
        sqLiteDatabase.close();
        return temp;
    }

    public void insertPerson(String personName, String personState, byte[] imageBytes) {
        sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("PersonState", personState);
        contentValues.put("PersonName", personName);
        contentValues.put("PersonImage", imageBytes);
        sqLiteDatabase.insert(tableName, null, contentValues);
        sqLiteDatabase.close();
    }

    public void updatePerson(int personId, String personName, String personState, byte[] imageBytes) {
        sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("personstate", personState);
        contentValues.put("personname", personName);
        contentValues.put("personimage", imageBytes);
        sqLiteDatabase
                .update(tableName, contentValues, "personid=?", new String[]{String.valueOf(personId)});
        sqLiteDatabase.close();
    }

    public void removePerson(int personId) {
        sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete(tableName, "personid=?", new String[]{String.valueOf(personId)});
        sqLiteDatabase.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.disableWriteAheadLogging();
    }
}
