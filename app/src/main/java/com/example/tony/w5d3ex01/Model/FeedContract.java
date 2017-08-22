package com.example.tony.w5d3ex01.Model;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by tony on 8/16/17.
 */

public final class FeedContract  {
    public static final String CONTENT_AUTHORITY="com.example.tony.w5d3ex01";
    public static final String PATH_MOVIE="movie";
    public static final String PATH_GENRE="genre";

    private static final Uri BASE_CONTENT_URI= Uri.parse("content://"+CONTENT_AUTHORITY);
    public FeedContract() {
    }
    public static class FeedEntryMovies implements BaseColumns{
        public static final Uri CONTENT_URI= BASE_CONTENT_URI.buildUpon().appendPath(
                PATH_MOVIE).build();
        public static final String CONTENT_TYPE= "vnd.android.cursor.dir/"+CONTENT_URI+
                "/"+PATH_MOVIE;
        public static final String CONTENT_ITEM_TYPE="vnd.android.cursor.item/"+CONTENT_URI+
                "/" + PATH_MOVIE;

        public static Uri buildMovieURI(long id){
            return ContentUris.withAppendedId(CONTENT_URI,id);
        }

        public static final String COLUMN_NAME_NAME="name";
        public static final String COLUMN_NAME_DATE="date";
        public static final String TABLE_NAME_MOVIES="movies";
        public static final String COLUMN_NAME_FK="genre_id";

    }
    public static class FeedEntryGenre implements BaseColumns {
        public static final Uri CONTENT_URI= BASE_CONTENT_URI.buildUpon().appendPath(
                PATH_GENRE).build();
        public static final String CONTENT_TYPE= "vnd.android.cursor.dir/"+CONTENT_URI+
                "/"+PATH_GENRE;
        public static final String CONTENT_ITEM_TYPE="vnd.android.cursor.item/"+CONTENT_URI+
                "/" + PATH_GENRE;

        public static Uri buildGenreURI(long id){
            return ContentUris.withAppendedId(CONTENT_URI,id);
        }
        public static final String TABLE_NAME_GENRE="genre";
        public static final String COLUMN_NAME_NAME="name";
    }
}
