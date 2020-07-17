package com.sam.kadarairkopi.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

public class DBSource {
    private SQLiteDatabase sqLiteDatabase;
    private DBHelper dbHelper;

    private String[] allColumns = {DBHelper.COL_ID, DBHelper.COL_WEIGHT, DBHelper.COL_WATER};

    public DBSource(Context context) {
        dbHelper = new DBHelper(context);
    }

    public void open() throws SQLException {
        sqLiteDatabase = dbHelper.getWritableDatabase();
    }

    public LogData createLog(String weightKopi, String waterKopi) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COL_WEIGHT, weightKopi);
        values.put(DBHelper.COL_WATER, waterKopi);

        long insertId = sqLiteDatabase.insert(DBHelper.TABLE_NAME, null, values);

        Cursor cursor = sqLiteDatabase.query(DBHelper.TABLE_NAME, allColumns, DBHelper.COL_ID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();

        LogData newLog = cursorToLog(cursor);
        cursor.close();

        return newLog;
    }

    private LogData cursorToLog(Cursor cursor) {
        LogData logData = new LogData();

        Log.v("info", "The getLong " + cursor.getLong(0));
        Log.v("info", "TheSetLatLng " + cursor.getString(1) + "," + cursor.getString(2));

        logData.setIdKopi(cursor.getLong(0));
        logData.setBeratKopi(cursor.getString(1));
        logData.setKadarAirKopi(cursor.getString(2));

        return logData;
    }

    public ArrayList<LogData> getAllLog() {
        ArrayList<LogData> listLogData = new ArrayList<>();

        Cursor cursor = sqLiteDatabase.query(DBHelper.TABLE_NAME, allColumns, null, null, null, null, null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            LogData logDatas = cursorToLog(cursor);
            listLogData.add(logDatas);
            cursor.moveToNext();
        }
        cursor.close();
        return listLogData;
    }

    public void deleteLog() {
        sqLiteDatabase = dbHelper.getWritableDatabase();
        sqLiteDatabase.execSQL("delete from " + DBHelper.TABLE_NAME);
        sqLiteDatabase.close();

    }

}
