package com.sam.kadarairkopi.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "table_coffee";
    public static final String COL_ID = "id";
    public static final String COL_WEIGHT = "berat_kopi";
    public static final String COL_WATER = "kadar_air";

    private static final String db_name = "coffee.db";
    private static final int db_version = 1;

    private static final String db_create = "create table "
            + TABLE_NAME + "("
            + COL_ID + " integer primary key autoincrement, "
            + COL_WEIGHT + " varchar(50) not null, "
            + COL_WATER + " varchar(50) not null);";

    public DBHelper(Context context) {
        super(context, db_name, null, db_version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(db_create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVer, int newVer) {
        Log.w(DBHelper.class.getName(), "Upgrading database from : "
                + oldVer + " to "
                + newVer + ", which will destroy all old data");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
