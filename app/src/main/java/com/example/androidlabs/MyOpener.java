package com.example.androidlabs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyOpener extends SQLiteOpenHelper {

    protected final static String DATABASE_NAME="MessagesDB";
    protected final static int VERSION_NUM=1;
    protected final static String TABLE_NAME="MESSAGES";
    protected final static String COL_MSG="TEXTMESSAGE";
    protected final static String COL_IS_SENT="SENT";
    protected final static String COL_id="_id";



    public MyOpener(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+ TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_MSG + " TEXT, "
                + COL_IS_SENT + " INTEGER);");

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);


        //create new table
        onCreate(db);

    }
}
