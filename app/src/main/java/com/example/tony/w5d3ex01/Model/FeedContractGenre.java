package com.example.tony.w5d3ex01.Model;

import android.provider.BaseColumns;

/**
 * Created by tony on 8/16/17.
 */

public final class FeedContractGenre {
    public static final String CONTENT_AUTHORITY="com.example.tony.w5d3ex01";
    public static final String PATH_GENRE="genre";
    public FeedContractGenre() {
    }
    public static class FeedEntryGenre implements BaseColumns {
        public static final String TABLE_NAME_GENRE="genre";
        public static final String COLUMN_NAME_NAME="name";
    }
}
