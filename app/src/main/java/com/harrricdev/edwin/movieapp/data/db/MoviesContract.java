package com.harrricdev.edwin.movieapp.data.db;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by edwin on 5/13/17.
 */

public class MoviesContract {

    public static final String AUTHORITY = "com.harrricdev.edwin.movieapp";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+ AUTHORITY);

    public static final String PATH_MOVIES = "movies";

    public static final class  MovieEntry implements BaseColumns {


        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIES).build();


        // Task table and column names
        public static final String TABLE_NAME = "movies";

        // Since TaskEntry implements the interface "BaseColumns", it has an automatically produced
        // "_ID" column in addition to the two below
        public static final String COLUMN_TITLE = "title";

        public static final String COLUMN_NUMBER = "number";


    }

}
