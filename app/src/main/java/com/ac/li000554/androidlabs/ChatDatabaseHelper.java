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



    //constructor
    public ChatDatabaseHelper(Context ctx){
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_MESSAGE + " TEXT );");
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

   /* //open database
    @Override
    public void onOpen(SQLiteDatabase db){
        database = db;
        Log.i("Database", "onOpen was called");
    }
*/
/*    //insert value into database/table
    public void insertDB(String content){
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_MESSAGE, content);
        database.insert(TABLE_NAME, null, contentValues);
    }*/

    //close
/*    @Override
    public void close(){
        if(database != null && database.isOpen()){
            database.close();
        }
    }*/

/*public Cursor getRecords(){
        return database.query(TABLE_NAME, null, null, null, null, null, null);
}*/

}

