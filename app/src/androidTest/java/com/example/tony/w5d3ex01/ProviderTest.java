package com.example.tony.w5d3ex01;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.test.AndroidTestCase;

import com.example.tony.w5d3ex01.Model.FeedContract;

import java.util.Map;
import java.util.Set;

/**
 * Created by tony on 8/17/17.
 */

public class ProviderTest extends AndroidTestCase {
    private static final String TEST_GENRE_NAME="Family";
    private static final String TEST_UPDATE_GENRE_NAME="SciFi";
    private static final String TEST_MOVIE_NAME="Back to the Future";
    private static final String TEST_UPDATE_MOVIE_NAME="BacK to the Future II";
    private static final String TEST_MOVIE_RELEASE_DATE="1985-09-11";

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        testDeleteAllRecords();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        testDeleteAllRecords();
    }
    public void testDeleteAllRecords(){
        mContext.getContentResolver().delete(
                FeedContract.FeedEntryMovies.CONTENT_URI,
                null,
                null
        );
        mContext.getContentResolver().delete(
                FeedContract.FeedEntryGenre.CONTENT_URI,
                null,
                null
        );
        Cursor tCursor= mContext.getContentResolver().query(
                FeedContract.FeedEntryMovies.CONTENT_URI,
                null,null,null,null
        );
        assertEquals(0,tCursor.getCount());
        tCursor=mContext.getContentResolver().query(
                FeedContract.FeedEntryGenre.CONTENT_URI,
                null,
                null,
                null,
                null
        );
        assertEquals(0,tCursor.getCount());

        tCursor.close();
    }
    public void testGetType(){
        String type= mContext.getContentResolver().getType(FeedContract.FeedEntryGenre.CONTENT_URI);
        assertEquals(FeedContract.FeedEntryGenre.CONTENT_TYPE,type);

        type= mContext.getContentResolver().getType(
                FeedContract.FeedEntryGenre.buildGenreURI(0)
        );
        assertEquals(FeedContract.FeedEntryGenre.CONTENT_ITEM_TYPE,type);

        type= mContext.getContentResolver().getType(FeedContract.FeedEntryMovies.CONTENT_URI);
        assertEquals(FeedContract.FeedEntryMovies.CONTENT_TYPE,type);

        type= mContext.getContentResolver().getType(
                FeedContract.FeedEntryMovies.buildMovieURI(0)
        );
        assertEquals(FeedContract.FeedEntryMovies.CONTENT_ITEM_TYPE,type);
    }
    public void testInsertAndReadGenre(){
        ContentValues values= getGenreContentValues();
        Uri genreInsertUri= mContext.getContentResolver().insert(
                FeedContract.FeedEntryGenre.CONTENT_URI,
                values
        );
        long genreId= ContentUris.parseId(genreInsertUri);
        assertTrue(genreId>0);

        Cursor cursor= mContext.getContentResolver().query(
                FeedContract.FeedEntryGenre.CONTENT_URI,
                null,null,null,null
        );
        validateCursor(cursor,values);

        cursor.close();

        cursor= mContext.getContentResolver().query(FeedContract.FeedEntryGenre.buildGenreURI(
                genreId),
                null,null,null,null);
        validateCursor(cursor,values);
    }
    private ContentValues getMovieContentValues(long genreId){
        ContentValues values= new ContentValues();
        values.put(FeedContract.FeedEntryMovies.COLUMN_NAME_NAME,TEST_MOVIE_NAME);
        values.put(FeedContract.FeedEntryMovies.COLUMN_NAME_DATE,TEST_MOVIE_RELEASE_DATE);
        values.put(FeedContract.FeedEntryMovies.COLUMN_NAME_FK,genreId);
        return values;
    }
    private void validateCursor(Cursor valueCursor, ContentValues expectedValues){
        assertTrue(valueCursor.moveToFirst());
        Set<Map.Entry<String, Object>> valueSet= expectedValues.valueSet();
        for(Map.Entry<String,Object> entry: valueSet){
            String columnName= entry.getKey();
            int index= valueCursor.getColumnIndex(columnName);
            assertFalse(index==-1);
            switch (valueCursor.getType(index)){
                case Cursor.FIELD_TYPE_FLOAT:
                    assertEquals(entry.getValue(),valueCursor.getDouble(index));
                    break;
                case Cursor.FIELD_TYPE_INTEGER:
                    assertEquals(Integer.parseInt(entry.getValue().toString()),valueCursor.
                    getInt(index));
                    break;
                case Cursor.FIELD_TYPE_STRING:
                    assertEquals(entry.getValue(),valueCursor.getString(index));
                    break;
                default:
                    assertEquals(entry.getValue(),valueCursor.getString(index));
                    break;
            }
        }
        valueCursor.close();
    }
    public void testUpdateGenre(){
        ContentValues values= getGenreContentValues();
        Uri genreUri= mContext.getContentResolver().insert(
                FeedContract.FeedEntryGenre.CONTENT_URI,values
        );
        long genreID= ContentUris.parseId(genreUri);
        ContentValues updateValues= new ContentValues();
        updateValues.put(FeedContract.FeedEntryGenre._ID,genreID);
        updateValues.put(FeedContract.FeedEntryGenre.COLUMN_NAME_NAME,TEST_UPDATE_GENRE_NAME);
    }
    private ContentValues getGenreContentValues(){
        ContentValues values= new ContentValues();
        values.put(FeedContract.FeedEntryGenre.COLUMN_NAME_NAME,TEST_GENRE_NAME);
        return values;
    }
    public void testInsertAndReadMovie(){
        ContentValues values= getGenreContentValues();
        Uri genreInsertUri= mContext.getContentResolver().insert(
                FeedContract.FeedEntryGenre.CONTENT_URI,
                values
        );
        long genreId= ContentUris.parseId(genreInsertUri);
        ContentValues movieValues= getMovieContentValues(genreId);
        Uri movieInsertUri= mContext.getContentResolver().insert(
                FeedContract.FeedEntryMovies.CONTENT_URI,
                movieValues
        );
        long movieId= ContentUris.parseId(movieInsertUri);
        assertTrue(movieId>0);
        Cursor movieCursor= mContext.getContentResolver().query(
                FeedContract.FeedEntryMovies.CONTENT_URI,
                null,null,null,null
        );
        validateCursor(movieCursor,movieValues);
        movieCursor.close();

        movieCursor= mContext.getContentResolver().query(
                FeedContract.FeedEntryMovies.buildMovieURI(movieId),
                null,null,null,null
        );
        validateCursor(movieCursor,movieValues);
        movieCursor.close();
    }
    public void testUpdateMovie(){
        ContentValues genreValues= getGenreContentValues();
        Uri genreInsertUri= mContext.getContentResolver().insert(
                FeedContract.FeedEntryGenre.CONTENT_URI,
                genreValues
        );
        long genreId= ContentUris.parseId(genreInsertUri);
        ContentValues movieValues= getMovieContentValues(genreId);
        Uri movieInserUri= mContext.getContentResolver().insert(
                FeedContract.FeedEntryMovies.CONTENT_URI,
                movieValues
        );
        long movieId= ContentUris.parseId(movieInserUri);
        ContentValues updateMovie= new ContentValues(movieValues);
        updateMovie.put(FeedContract.FeedEntryMovies._ID,movieId);
        updateMovie.put(FeedContract.FeedEntryMovies.COLUMN_NAME_NAME,TEST_UPDATE_MOVIE_NAME);
        int rowsUpdated=mContext.getContentResolver().update(
                FeedContract.FeedEntryMovies.CONTENT_URI,
                updateMovie,
                FeedContract.FeedEntryMovies._ID+" =?",
                new String[]{String.valueOf(movieId)}
        );
        assertTrue(rowsUpdated>0);

        Cursor movieCursor= mContext.getContentResolver().query(
                FeedContract.FeedEntryMovies.buildMovieURI(movieId),
                null,null,null,null
        );
        validateCursor(movieCursor,updateMovie);
        movieCursor.close();
    }
}
