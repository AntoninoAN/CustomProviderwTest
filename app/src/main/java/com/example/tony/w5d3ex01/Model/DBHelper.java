package com.example.tony.w5d3ex01.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.tony.w5d3ex01.Model.FeedContract.FeedEntryMovies;
import com.example.tony.w5d3ex01.Model.FeedContract.FeedEntryGenre;

/**
 * Created by tony on 8/16/17.
 */

public class DBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "mydatabase.db";

    public static final String SQL_CREATE_ENTRIES_MOVIES =
            "CREATE TABLE " + FeedEntryMovies.TABLE_NAME_MOVIES + " (" +
                    FeedEntryMovies._ID + " INTEGER PRIMARY KEY," +
                    FeedEntryMovies.COLUMN_NAME_NAME+ " TEXT," +
                    FeedEntryMovies.COLUMN_NAME_DATE + " TEXT," +
                    FeedEntryMovies.COLUMN_NAME_FK +" INTEGER ," +
                    "FOREIGN KEY ("+
                    FeedEntryMovies._ID+") REFERENCES "+
                    FeedEntryGenre.TABLE_NAME_GENRE+"("+
                    FeedEntryGenre._ID+"))";
    public static final String SQL_CREATE_ENTRIES_GENRE=
            "CREATE TABLE "+ FeedEntryGenre.TABLE_NAME_GENRE + " ("+
            FeedEntryGenre._ID + " INTEGER PRIMARY KEY," +
            FeedEntryGenre.COLUMN_NAME_NAME + " TEXT)";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES_GENRE);
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES_MOVIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES_GENRE);
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES_MOVIES);
        onCreate(sqLiteDatabase);
    }
}
