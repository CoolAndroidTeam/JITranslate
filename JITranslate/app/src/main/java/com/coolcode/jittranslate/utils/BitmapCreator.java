package com.coolcode.jittranslate.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Base64;
import android.util.DisplayMetrics;

public class BitmapCreator {
    private String bmString;
    private Bitmap bm;
    public BitmapCreator(String bm) {
        this.bmString = bm;
        this.createBitmap();
    }
    private void createBitmap() {
        byte[] decodedString = Base64.decode(bmString, Base64.DEFAULT);
        this.bm = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }

    public Bitmap createResizedBitmap(DisplayMetrics metrics){
        int width = bm.getWidth();
        int height = bm.getHeight();

        float scaleWidth = metrics.scaledDensity;
        float scaleHeight = metrics.scaledDensity;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
    }
}
