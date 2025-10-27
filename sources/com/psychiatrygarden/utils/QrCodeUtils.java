package com.psychiatrygarden.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.util.Hashtable;

/* loaded from: classes6.dex */
public class QrCodeUtils {
    private static Bitmap addLogo(Bitmap src, Bitmap logo) {
        if (src == null || logo == null) {
            return src;
        }
        int width = src.getWidth();
        int height = src.getHeight();
        int width2 = logo.getWidth();
        int height2 = logo.getHeight();
        if (width == 0 || height == 0) {
            return null;
        }
        if (width2 == 0 || height2 == 0) {
            return src;
        }
        float f2 = ((width * 1.0f) / 6.0f) / width2;
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        try {
            Canvas canvas = new Canvas(bitmapCreateBitmap);
            canvas.drawBitmap(src, 0.0f, 0.0f, (Paint) null);
            canvas.scale(f2, f2, width >> 1, height >> 1);
            canvas.drawBitmap(logo, (width - width2) >> 1, (height - height2) >> 1, (Paint) null);
            canvas.save();
            canvas.restore();
            return bitmapCreateBitmap;
        } catch (Exception e2) {
            e2.getStackTrace();
            return null;
        }
    }

    public static Bitmap createQRcodeImage(int width, int height, String value, Bitmap logo) {
        if (value != null) {
            try {
                if (!"".equals(value) && value.length() >= 1) {
                    Hashtable hashtable = new Hashtable();
                    hashtable.put(EncodeHintType.CHARACTER_SET, "UTF-8");
                    hashtable.put(EncodeHintType.MARGIN, 2);
                    hashtable.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
                    BitMatrix bitMatrixEncode = new QRCodeWriter().encode(value, BarcodeFormat.QR_CODE, width, height, hashtable);
                    int[] iArr = new int[width * height];
                    for (int i2 = 0; i2 < height; i2++) {
                        for (int i3 = 0; i3 < width; i3++) {
                            if (bitMatrixEncode.get(i3, i2)) {
                                iArr[(i2 * width) + i3] = -16777216;
                            } else {
                                iArr[(i2 * width) + i3] = -1;
                            }
                        }
                    }
                    Bitmap bitmapCreateBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                    bitmapCreateBitmap.setPixels(iArr, 0, width, 0, 0, width, height);
                    return addLogo(bitmapCreateBitmap, logo);
                }
            } catch (WriterException e2) {
                e2.printStackTrace();
            }
        }
        return null;
    }
}
