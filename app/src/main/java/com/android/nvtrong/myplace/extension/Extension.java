package com.android.nvtrong.myplace.extension;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

/**
 * Created by nvtrong on 4/12/2018.
 */

public class Extension {
    public static byte[] convertBitmapToByte(Bitmap bitmap) {
        byte[] byteArray = null;
        if (bitmap != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byteArray = stream.toByteArray();
        }
        return byteArray;
    }
}
