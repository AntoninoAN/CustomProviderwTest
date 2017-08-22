package com.example.tony.w5d3ex01.Model;

import android.content.ContentValues;
import android.support.annotation.Nullable;
import android.util.Log;

import static android.content.ContentValues.TAG;
import com.example.tony.w5d3ex01.Model.FeedContract.FeedEntryMovies;
import com.example.tony.w5d3ex01.Model.FeedContractGenre.FeedEntryGenre;

/**
 * Created by tony on 8/16/17.
 */

public class TestModel {
    public TestModel() {
    }
    private void saveRecord(String name,@Nullable String date, String genre,String table,String fk) {
        switch (table){
            case "movies":
                ContentValues values = new ContentValues();
                values.put(FeedEntryMovies.COLUMN_NAME_NAME,name);
                values.put(FeedEntryMovies.COLUMN_NAME_DATE,date);
                values.put(FeedEntryMovies.COLUMN_NAME_FK,fk);
                break;
            case "genre":
                break;
        }/*
        String title = "Record title";
        String subtitle = "Record subtitle";

        ContentValues values = new ContentValues();
        values.put(FeedEntry.COLUMN_NAME_TITLE, title);
        values.put(FeedEntry.COLUMN_NAME_SUBTITLE, subtitle);

        long recordId = database.insert(
                FeedEntry.TABLE_NAME,
                null,
                values
        );
        if(recordId > 0) {
            Log.d(TAG, "saveRecord: Record saved.");
        } else {
            Log.d(TAG, "saveRecord: Record not saved");
        }*/
    }
}
