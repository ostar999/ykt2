package com.uuzuche.lib_zxing.encoding;

import android.graphics.Bitmap;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import java.util.Hashtable;

/* loaded from: classes6.dex */
public final class EncodingHandler {
    private static final int BLACK = -16777216;

    public static Bitmap createQRCode(String str, int i2) throws WriterException {
        new Hashtable().put(EncodeHintType.CHARACTER_SET, "utf-8");
        BitMatrix bitMatrixEncode = new MultiFormatWriter().encode(str, BarcodeFormat.QR_CODE, i2, i2);
        int width = bitMatrixEncode.getWidth();
        int height = bitMatrixEncode.getHeight();
        int[] iArr = new int[width * height];
        for (int i3 = 0; i3 < height; i3++) {
            for (int i4 = 0; i4 < width; i4++) {
                if (bitMatrixEncode.get(i4, i3)) {
                    iArr[(i3 * width) + i4] = -16777216;
                }
            }
        }
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmapCreateBitmap.setPixels(iArr, 0, width, 0, 0, width, height);
        return bitmapCreateBitmap;
    }
}
