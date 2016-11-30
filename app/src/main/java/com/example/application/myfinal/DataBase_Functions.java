package com.example.application.myfinal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.application.myfinal.DataBase_Data.idn;


public class DataBase_Functions extends SQLiteOpenHelper {

    public static final int Data_Version = 1;
    public String create_query = "Create Table " + DataBase_Data.TableName + "(" + DataBase_Data.poster + " TEXT," + DataBase_Data.titl + " TEXT," + DataBase_Data.descreption + " TEXT," + DataBase_Data.date + " TEXT," + DataBase_Data.vote + " TEXT," + idn + " TEXT);";

    public DataBase_Functions(Context context) {
        super(context, DataBase_Data.DataBaseName, null, Data_Version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(create_query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DataBase_Data.TableName);
    }

    public void insertData(DataBase_Functions dbf, String poster, String title, String vote, String over, String date, String id) {
        SQLiteDatabase sd = dbf.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DataBase_Data.poster, poster);
        cv.put(DataBase_Data.date, date);
        cv.put(DataBase_Data.descreption, over);
        cv.put(DataBase_Data.titl, title);
        cv.put(DataBase_Data.vote, vote);
        cv.put(DataBase_Data.idn, id);
        sd.insert(DataBase_Data.TableName, null, cv);
    }

    public void deleteData(DataBase_Functions dbf, String poster) {
        SQLiteDatabase sd = dbf.getWritableDatabase();
        sd.execSQL("DELETE FROM " + DataBase_Data.TableName + " WHERE " + DataBase_Data.poster + " = '" + poster + "'");
        sd.close();
    }

    public Cursor getData(DataBase_Functions dbf) {
        SQLiteDatabase sd = dbf.getReadableDatabase();
        String[] columns = {DataBase_Data.poster, DataBase_Data.titl, DataBase_Data.descreption, DataBase_Data.date, DataBase_Data.vote, DataBase_Data.idn};
        Cursor CR = sd.query(DataBase_Data.TableName, columns, null, null, null, null, null);
        return CR;
    }

    public boolean isMovieExist(DataBase_Functions dbf, String id) {
        SQLiteDatabase sd = dbf.getReadableDatabase();
        String[] col = {id};
        Cursor cursor = sd.query(
                DataBase_Data.TableName,
                col,
                DataBase_Data.idn + " = '" + id + "'",
                null,
                null,
                null,
                null
        );
        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }
}