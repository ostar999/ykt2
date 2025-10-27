package com.uuzuche.lib_zxing.camera;

import android.graphics.Bitmap;
import com.google.zxing.LuminanceSource;

/* loaded from: classes6.dex */
public class BitmapLuminanceSource extends LuminanceSource {
    private byte[] bitmapPixels;

    public BitmapLuminanceSource(Bitmap bitmap) {
        super(bitmap.getWidth(), bitmap.getHeight());
        int width = bitmap.getWidth() * bitmap.getHeight();
        int[] iArr = new int[width];
        this.bitmapPixels = new byte[bitmap.getWidth() * bitmap.getHeight()];
        bitmap.getPixels(iArr, 0, getWidth(), 0, 0, getWidth(), getHeight());
        for (int i2 = 0; i2 < width; i2++) {
            this.bitmapPixels[i2] = (byte) iArr[i2];
        }
    }

    @Override // com.google.zxing.LuminanceSource
    public byte[] getMatrix() {
        return this.bitmapPixels;
    }

    @Override // com.google.zxing.LuminanceSource
    public byte[] getRow(int i2, byte[] bArr) {
        System.arraycopy(this.bitmapPixels, i2 * getWidth(), bArr, 0, getWidth());
        return bArr;
    }
}
