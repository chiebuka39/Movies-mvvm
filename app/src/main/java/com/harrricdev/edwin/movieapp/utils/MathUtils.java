package com.harrricdev.edwin.movieapp.utils;

/**
 * Created by edwin on 5/17/17.
 */

public class MathUtils {

    private MathUtils() { }

    public static float constrain(float min, float max, float v) {
        return Math.max(min, Math.min(max, v));
    }
}
