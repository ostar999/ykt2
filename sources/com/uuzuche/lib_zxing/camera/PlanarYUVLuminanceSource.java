package com.uuzuche.lib_zxing.camera;

import android.graphics.Bitmap;
import com.google.zxing.LuminanceSource;

/* loaded from: classes6.dex */
public final class PlanarYUVLuminanceSource extends LuminanceSource {
    private final int dataHeight;
    private final int dataWidth;
    private final int left;

    /* renamed from: top, reason: collision with root package name */
    private final int f24225top;
    private final byte[] yuvData;

    public PlanarYUVLuminanceSource(byte[] bArr, int i2, int i3, int i4, int i5, int i6, int i7) {
        super(i6, i7);
        this.yuvData = bArr;
        this.dataWidth = i2;
        this.dataHeight = i3;
        this.left = i4;
        this.f24225top = i5;
    }

    public int getDataHeight() {
        return this.dataHeight;
    }

    public int getDataWidth() {
        return this.dataWidth;
    }

    @Override // com.google.zxing.LuminanceSource
    public byte[] getMatrix() {
        int width = getWidth();
        int height = getHeight();
        int i2 = this.dataWidth;
        if (width == i2 && height == this.dataHeight) {
            return this.yuvData;
        }
        int i3 = width * height;
        byte[] bArr = new byte[i3];
        int i4 = (this.f24225top * i2) + this.left;
        if (width == i2) {
            System.arraycopy(this.yuvData, i4, bArr, 0, i3);
            return bArr;
        }
        byte[] bArr2 = this.yuvData;
        for (int i5 = 0; i5 < height; i5++) {
            System.arraycopy(bArr2, i4, bArr, i5 * width, width);
            i4 += this.dataWidth;
        }
        return bArr;
    }

    @Override // com.google.zxing.LuminanceSource
    public byte[] getRow(int i2, byte[] bArr) {
        if (i2 < 0 || i2 >= getHeight()) {
            throw new IllegalArgumentException("Requested row is outside the image: " + i2);
        }
        int width = getWidth();
        if (bArr == null || bArr.length < width) {
            bArr = new byte[width];
        }
        System.arraycopy(this.yuvData, ((i2 + this.f24225top) * this.dataWidth) + this.left, bArr, 0, width);
        return bArr;
    }

    @Override // com.google.zxing.LuminanceSource
    public boolean isCropSupported() {
        return true;
    }

    public Bitmap renderCroppedGreyscaleBitmap() {
        int width = getWidth();
        int height = getHeight();
        int[] iArr = new int[width * height];
        byte[] bArr = this.yuvData;
        int i2 = (this.f24225top * this.dataWidth) + this.left;
        for (int i3 = 0; i3 < height; i3++) {
            int i4 = i3 * width;
            for (int i5 = 0; i5 < width; i5++) {
                iArr[i4 + i5] = ((bArr[i2 + i5] & 255) * 65793) | (-16777216);
            }
            i2 += this.dataWidth;
        }
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmapCreateBitmap.setPixels(iArr, 0, width, 0, 0, width, height);
        return bitmapCreateBitmap;
    }
}
