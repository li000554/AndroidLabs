package com.ac.li000554.androidlabs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ChatDatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Messages.db";
    public static final String TABLE_NAME = "DBTable";
    public static final int VERSION_NUM = 1;
    public static final String KEY_ID = "id";
    public static final String KEY_MESSAGE = "message";
    public static final String SQL_CREATE ="CREATE TABLE "
            + TABLE_NAME
            + "( "
            + KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_MESSAGE
            + " TEXT "
            + ");";



    //constructor
    public ChatDatabaseHelper(Context ctx){

        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(SQL_CREATE);
        Log.i("ChatDatabaseHelper", "Calling onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
        Log.i("ChatDatabaseHelper", "Calling onUpgrade, oldVersion=" + oldVersion + " newVersion=" + newVersion);

    }

    @Override
    public  void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
        Log.i("ChatDatabaseHelper", "Calling onDowngrade, oldVersion=" + oldVersion + " newVersion=" + newVersion);
    }


}

