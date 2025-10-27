package com.psychiatrygarden.activity.purchase.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

/* loaded from: classes5.dex */
public class Bimp {
    public static int max;
    public static ArrayList<ImageItem> tempSelectBitmap = new ArrayList<>();
    public static ArrayList<ImageItem> temp = new ArrayList<>();

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int i2 = options.outHeight;
        int i3 = options.outWidth;
        if (i2 <= reqHeight && i3 <= reqWidth) {
            return 1;
        }
        int iRound = Math.round(i2 / reqHeight);
        int iRound2 = Math.round(i3 / reqWidth);
        return iRound < iRound2 ? iRound : iRound2;
    }

    public static Bitmap revitionImageSize(String path) throws IOException {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(new File(path)));
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(bufferedInputStream, null, options);
        bufferedInputStream.close();
        int i2 = 0;
        while (true) {
            if ((options.outWidth >> i2) <= 1000 && (options.outHeight >> i2) <= 1000) {
                BufferedInputStream bufferedInputStream2 = new BufferedInputStream(new FileInputStream(new File(path)));
                options.inSampleSize = calculateInSampleSize(options, 480, 800);
                options.inJustDecodeBounds = false;
                return BitmapFactory.decodeStream(bufferedInputStream2, null, options);
            }
            i2++;
        }
    }
}
