package com.example.tony.w5d3ex01.Model;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by tony on 8/16/17.
 */

public class MovieProvider extends ContentProvider {
    private DBHelper dbHelper;
    private static final int GENRE=100;
    private static final int GENRE_ID=101;
    private static final int MOVIE=200;
    private static final int MOVIE_ID=201;

    private static final UriMatcher uriMatcher= buildUriMatcher();

    public static UriMatcher buildUriMatcher() {
        String content= FeedContract.CONTENT_AUTHORITY;
        UriMatcher matcher= new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(content,FeedContract.PATH_GENRE,GENRE);
        matcher.addURI(content,FeedContract.PATH_GENRE+"/#",GENRE_ID);
        matcher.addURI(content,FeedContract.PATH_MOVIE, MOVIE);
        matcher.addURI(content,FeedContract.PATH_MOVIE+"/#",MOVIE_ID);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        dbHelper = new DBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri,
                        @Nullable String[] projection, //how we are going to project our Data
                        @Nullable String selection,
                        @Nullable String[] selectionArgs,
                        @Nullable String sortOrder) {
        final SQLiteDatabase db=dbHelper.getWritableDatabase();
        Cursor returnCursor;
        switch (uriMatcher.match(uri)){
            case GENRE:
                returnCursor=db.query(
                        FeedContract.FeedEntryGenre.TABLE_NAME_GENRE,
                        projection,
                        selection,
                        selectionArgs,
                        null,null,
                        sortOrder
                );
                break;
            case GENRE_ID:
                long _id= ContentUris.parseId(uri);
                returnCursor=db.query(
                        FeedContract.FeedEntryGenre.TABLE_NAME_GENRE,
                        projection,
                        FeedContract.FeedEntryGenre._ID+" =?",
                        new String[]{String.valueOf(_id)},
                        null,null,
                        sortOrder
                );
                break;
            case MOVIE:
                returnCursor=db.query(
                        FeedContract.FeedEntryMovies.TABLE_NAME_MOVIES,
                        projection,
                        selection,
                        selectionArgs,
                        null,null,
                        sortOrder
                );
                break;
            case MOVIE_ID:
                _id= ContentUris.parseId(uri);
                returnCursor=db.query(
                        FeedContract.FeedEntryMovies.TABLE_NAME_MOVIES,
                        projection,
                        FeedContract.FeedEntryGenre._ID+" =?",
                        new String[]{String.valueOf(_id)},
                        null,null,
                        sortOrder
                );
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: "+ uri);
        }
        returnCursor.setNotificationUri(getContext().getContentResolver(),uri);
        return returnCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)){
            case GENRE:
                return FeedContract.FeedEntryGenre.CONTENT_TYPE;
            case GENRE_ID:
                return FeedContract.FeedEntryGenre.CONTENT_ITEM_TYPE;
            case MOVIE:
                return FeedContract.FeedEntryMovies.CONTENT_TYPE;
            case MOVIE_ID:
                return FeedContract.FeedEntryMovies.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown URI"+uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final SQLiteDatabase db= dbHelper.getWritableDatabase();
        long _id;
        Uri returnUri;
        switch (uriMatcher.match(uri)){
            case GENRE:
                _id=db.insert(FeedContract.FeedEntryGenre.TABLE_NAME_GENRE,
                        null,contentValues);
                if(_id>0)
                    returnUri=FeedContract.FeedEntryGenre.buildGenreURI(_id);
                else
                    throw new UnsupportedOperationException("Unable to Insert into"+uri);
                break;
            case MOVIE:
                _id=db.insert(FeedContract.FeedEntryMovies.TABLE_NAME_MOVIES,
                        null,contentValues);
                if(_id>0)
                    returnUri=FeedContract.FeedEntryMovies.buildMovieURI(_id);
                else
                    throw new UnsupportedOperationException("Unable to Insert into"+uri);
                break;
            default:
                throw new UnsupportedOperationException("Unknow URI: "+uri);
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri,
                      @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        final SQLiteDatabase db= dbHelper.getWritableDatabase();
        int rows;
        switch (uriMatcher.match(uri)){
            case GENRE:
                rows= db.delete(FeedContract.FeedEntryGenre.TABLE_NAME_GENRE,
                        selection,selectionArgs);
                break;
            case MOVIE:
                rows= db.delete(FeedContract.FeedEntryMovies.TABLE_NAME_MOVIES,
                        selection,selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknow URI: "+uri);
        }
        if(selection==null||rows!=0){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return rows;
    }

    @Override
    public int update(@NonNull Uri uri,
                      @Nullable ContentValues contentValues,
                      @Nullable String selection,
                      @Nullable String[] selectionArgs) {

        final SQLiteDatabase db= dbHelper.getWritableDatabase();
        int rows;
        switch (uriMatcher.match(uri)){
            case GENRE:
                rows= db.update(FeedContract.FeedEntryGenre.TABLE_NAME_GENRE,
                        contentValues,
                        selection,selectionArgs);
                break;
            case MOVIE:
                rows= db.update(FeedContract.FeedEntryMovies.TABLE_NAME_MOVIES,
                        contentValues,
                        selection,selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknow URI: "+uri);
        }
        if(rows!=0){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return rows;
    }
}
