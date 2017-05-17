package com.harrricdev.edwin.movieapp.utils;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by Gigi on 5/15/17.
 */

public class OfflineImage {

    private static String name_ = "images";

    public static String saveToInternalStorage(Bitmap bitmapImage, Context context, String name){


        ContextWrapper cw = new ContextWrapper(context);
        // path to /data/data/yourapp/app_data/imageDir

        //name_= "com.harrricdev.edwin.movieapp"; //Folder name in device android/data/
        File directory = cw.getDir(name_, Context.MODE_PRIVATE);

        // Create imageDir
        File mypath=new File(directory,name);

        FileOutputStream fos = null;
        try {

            fos = new FileOutputStream(mypath);

            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (Exception e) {
            Log.e("HARRY4 ", directory.getAbsolutePath());
            e.printStackTrace();
        }

        Log.e("HARRY4 ", directory.getPath());
        return directory.getAbsolutePath();
    }

    /** Method to retrieve image from your device **/

    public static Bitmap loadImageFromStorage(String path, String name)
    {
        Bitmap b;
        try {
            File f=new File(path, name);
            b = BitmapFactory.decodeStream(new FileInputStream(f));
            return b;
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
