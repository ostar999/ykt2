package net.tsz.afinal.bitmap.core;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.FileDescriptor;

/* loaded from: classes9.dex */
public class BitmapDecoder {
    private BitmapDecoder() {
    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int i2, int i3) {
        int i4 = options.outHeight;
        int i5 = options.outWidth;
        if (i4 <= i3 && i5 <= i2) {
            return 1;
        }
        int iRound = i5 > i4 ? Math.round(i4 / i3) : Math.round(i5 / i2);
        while ((i5 * i4) / (iRound * iRound) > i2 * i3 * 2) {
            iRound++;
        }
        return iRound;
    }

    public static Bitmap decodeSampledBitmapFromByteArray(byte[] bArr, int i2, int i3, int i4, int i5) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inPurgeable = true;
        BitmapFactory.decodeByteArray(bArr, i2, i3, options);
        options.inSampleSize = calculateInSampleSize(options, i4, i5);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeByteArray(bArr, i2, i3, options);
    }

    public static Bitmap decodeSampledBitmapFromDescriptor(FileDescriptor fileDescriptor, int i2, int i3) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inPurgeable = true;
        BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);
        options.inSampleSize = calculateInSampleSize(options, i2, i3);
        options.inJustDecodeBounds = false;
        try {
            return BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);
        } catch (OutOfMemoryError e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources resources, int i2, int i3, int i4) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inPurgeable = true;
        BitmapFactory.decodeResource(resources, i2, options);
        options.inSampleSize = calculateInSampleSize(options, i3, i4);
        options.inJustDecodeBounds = false;
        try {
            return BitmapFactory.decodeResource(resources, i2, options);
        } catch (OutOfMemoryError e2) {
            e2.printStackTrace();
            return null;
        }
    }
}
