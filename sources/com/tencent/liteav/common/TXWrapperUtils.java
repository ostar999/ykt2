package com.tencent.liteav.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.tencent.liteav.basic.log.TXCLog;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes6.dex */
public class TXWrapperUtils {
    private static final String TAG = "TXWrapperUtils";

    public static Bitmap generateBitmap(Context context, String str) throws IOException {
        Bitmap bitmapDecodeFile = BitmapFactory.decodeFile(str);
        if (bitmapDecodeFile != null) {
            return bitmapDecodeFile;
        }
        try {
            InputStream inputStreamOpen = context.getAssets().open(str);
            bitmapDecodeFile = BitmapFactory.decodeStream(inputStreamOpen);
            inputStreamOpen.close();
            return bitmapDecodeFile;
        } catch (IOException e2) {
            TXCLog.e(TAG, "generateBitmap failed. " + e2);
            return bitmapDecodeFile;
        }
    }
}
