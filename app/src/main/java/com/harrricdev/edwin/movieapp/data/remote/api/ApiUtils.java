package com.harrricdev.edwin.movieapp.data.remote.api;

import com.harrricdev.edwin.movieapp.BuildConfig;

/**
 * Created by edwin on 5/6/17.
 */

public class ApiUtils {

    public static String getBaseUrl(){
        return "https://api.themoviedb.org/3/";
    }

    public static String getApiKey() {
        return BuildConfig.API_KEY;
    }

}
