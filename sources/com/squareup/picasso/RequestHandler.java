package com.squareup.picasso;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.NetworkInfo;
import com.squareup.picasso.Picasso;
import java.io.IOException;

/* loaded from: classes6.dex */
public abstract class RequestHandler {

    public static final class Result {
        private final Bitmap bitmap;
        private final int exifOrientation;
        private final Picasso.LoadedFrom loadedFrom;

        public Result(Bitmap bitmap, Picasso.LoadedFrom loadedFrom) {
            this(bitmap, loadedFrom, 0);
        }

        public Bitmap getBitmap() {
            return this.bitmap;
        }

        public int getExifOrientation() {
            return this.exifOrientation;
        }

        public Picasso.LoadedFrom getLoadedFrom() {
            return this.loadedFrom;
        }

        public Result(Bitmap bitmap, Picasso.LoadedFrom loadedFrom, int i2) {
            this.bitmap = bitmap;
            this.loadedFrom = loadedFrom;
            this.exifOrientation = i2;
        }
    }

    public static void calculateInSampleSize(int i2, int i3, BitmapFactory.Options options, Request request) {
        calculateInSampleSize(i2, i3, options.outWidth, options.outHeight, options, request);
    }

    public static BitmapFactory.Options createBitmapOptions(Request request) {
        boolean zHasSize = request.hasSize();
        boolean z2 = request.config != null;
        if (!zHasSize && !z2) {
            return null;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = zHasSize;
        if (z2) {
            options.inPreferredConfig = request.config;
        }
        return options;
    }

    public static boolean requiresInSampleSize(BitmapFactory.Options options) {
        return options != null && options.inJustDecodeBounds;
    }

    public abstract boolean canHandleRequest(Request request);

    public int getRetryCount() {
        return 0;
    }

    public abstract Result load(Request request) throws IOException;

    public boolean shouldRetry(boolean z2, NetworkInfo networkInfo) {
        return false;
    }

    public boolean supportsReplay() {
        return false;
    }

    public static void calculateInSampleSize(int i2, int i3, int i4, int i5, BitmapFactory.Options options, Request request) {
        int iMax;
        double dFloor;
        if (i5 > i3 || i4 > i2) {
            if (i3 == 0) {
                dFloor = Math.floor(i4 / i2);
            } else if (i2 == 0) {
                dFloor = Math.floor(i5 / i3);
            } else {
                int iFloor = (int) Math.floor(i5 / i3);
                int iFloor2 = (int) Math.floor(i4 / i2);
                iMax = request.centerInside ? Math.max(iFloor, iFloor2) : Math.min(iFloor, iFloor2);
            }
            iMax = (int) dFloor;
        } else {
            iMax = 1;
        }
        options.inSampleSize = iMax;
        options.inJustDecodeBounds = false;
    }
}
