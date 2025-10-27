package com.easefun.polyv.mediasdk.gifmaker;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import com.yikaobang.yixue.R2;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/* loaded from: classes3.dex */
public class AnimatedGifEncoder {
    private static final double MIN_TRANSPARENT_PERCENTAGE = 4.0d;
    private static final String TAG = "AnimatedGifEncoder";
    private int colorDepth;
    private byte[] colorTab;
    private boolean hasTransparentPixels;
    private int height;
    private Bitmap image;
    private byte[] indexedPixels;
    private OutputStream out;
    private byte[] pixels;
    private int transIndex;
    private int width;
    private Integer transparent = null;
    private int repeat = -1;
    private int delay = 0;
    public boolean started = false;
    private boolean[] usedEntry = new boolean[256];
    private int palSize = 7;
    private int dispose = -1;
    private boolean closeStream = false;
    private boolean firstFrame = true;
    private boolean sizeSet = false;
    private int sample = 10;

    private void analyzePixels() {
        byte[] bArr = this.pixels;
        int length = bArr.length;
        int i2 = length / 3;
        this.indexedPixels = new byte[i2];
        NeuQuant neuQuant = new NeuQuant(bArr, length, this.sample);
        this.colorTab = neuQuant.process();
        int i3 = 0;
        while (true) {
            byte[] bArr2 = this.colorTab;
            if (i3 >= bArr2.length) {
                break;
            }
            byte b3 = bArr2[i3];
            int i4 = i3 + 2;
            bArr2[i3] = bArr2[i4];
            bArr2[i4] = b3;
            this.usedEntry[i3 / 3] = false;
            i3 += 3;
        }
        int i5 = 0;
        int i6 = 0;
        while (i5 < i2) {
            byte[] bArr3 = this.pixels;
            int i7 = i6 + 1;
            int i8 = i7 + 1;
            int map = neuQuant.map(bArr3[i6] & 255, bArr3[i7] & 255, bArr3[i8] & 255);
            this.usedEntry[map] = true;
            this.indexedPixels[i5] = (byte) map;
            i5++;
            i6 = i8 + 1;
        }
        this.pixels = null;
        this.colorDepth = 8;
        this.palSize = 7;
        Integer num = this.transparent;
        if (num != null) {
            this.transIndex = findClosest(num.intValue());
        } else if (this.hasTransparentPixels) {
            this.transIndex = findClosest(0);
        }
    }

    private int findClosest(int i2) {
        if (this.colorTab == null) {
            return -1;
        }
        int iRed = Color.red(i2);
        int iGreen = Color.green(i2);
        int iBlue = Color.blue(i2);
        int length = this.colorTab.length;
        int i3 = 0;
        int i4 = 16777216;
        int i5 = 0;
        while (i3 < length) {
            byte[] bArr = this.colorTab;
            int i6 = i3 + 1;
            int i7 = iRed - (bArr[i3] & 255);
            int i8 = i6 + 1;
            int i9 = iGreen - (bArr[i6] & 255);
            int i10 = iBlue - (bArr[i8] & 255);
            int i11 = (i7 * i7) + (i9 * i9) + (i10 * i10);
            int i12 = i8 / 3;
            if (this.usedEntry[i12] && i11 < i4) {
                i4 = i11;
                i5 = i12;
            }
            i3 = i8 + 1;
        }
        return i5;
    }

    private void getImagePixels() {
        int width = this.image.getWidth();
        int height = this.image.getHeight();
        int i2 = this.width;
        if (width != i2 || height != this.height) {
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i2, this.height, Bitmap.Config.ARGB_8888);
            new Canvas(bitmapCreateBitmap).drawBitmap(bitmapCreateBitmap, 0.0f, 0.0f, (Paint) null);
            this.image = bitmapCreateBitmap;
        }
        int i3 = width * height;
        int[] iArr = new int[i3];
        this.image.getPixels(iArr, 0, width, 0, 0, width, height);
        this.pixels = new byte[i3 * 3];
        this.hasTransparentPixels = false;
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        while (i4 < i3) {
            int i7 = iArr[i4];
            if (i7 == 0) {
                i5++;
            }
            byte[] bArr = this.pixels;
            int i8 = i6 + 1;
            bArr[i6] = (byte) (i7 & 255);
            int i9 = i8 + 1;
            bArr[i8] = (byte) ((i7 >> 8) & 255);
            bArr[i9] = (byte) ((i7 >> 16) & 255);
            i4++;
            i6 = i9 + 1;
        }
        double d3 = (i5 * 100) / i3;
        this.hasTransparentPixels = d3 > MIN_TRANSPARENT_PERCENTAGE;
        if (Log.isLoggable(TAG, 3)) {
            Log.d(TAG, "got pixels for frame with " + d3 + "% transparent pixels");
        }
    }

    private void writeGraphicCtrlExt() throws IOException {
        int i2;
        int i3;
        this.out.write(33);
        this.out.write(R2.attr.actionModePasteDrawable);
        this.out.write(4);
        if (this.transparent != null || this.hasTransparentPixels) {
            i2 = 1;
            i3 = 2;
        } else {
            i2 = 0;
            i3 = 0;
        }
        int i4 = this.dispose;
        if (i4 >= 0) {
            i3 = i4 & 7;
        }
        this.out.write(i2 | (i3 << 2) | 0 | 0);
        writeShort(this.delay);
        this.out.write(this.transIndex);
        this.out.write(0);
    }

    private void writeImageDesc() throws IOException {
        this.out.write(44);
        writeShort(0);
        writeShort(0);
        writeShort(this.width);
        writeShort(this.height);
        if (this.firstFrame) {
            this.out.write(0);
        } else {
            this.out.write(this.palSize | 128);
        }
    }

    private void writeLSD() throws IOException {
        writeShort(this.width);
        writeShort(this.height);
        this.out.write(this.palSize | 240);
        this.out.write(0);
        this.out.write(0);
    }

    private void writeNetscapeExt() throws IOException {
        this.out.write(33);
        this.out.write(255);
        this.out.write(11);
        writeString("NETSCAPE2.0");
        this.out.write(3);
        this.out.write(1);
        writeShort(this.repeat);
        this.out.write(0);
    }

    private void writePalette() throws IOException {
        OutputStream outputStream = this.out;
        byte[] bArr = this.colorTab;
        outputStream.write(bArr, 0, bArr.length);
        int length = 768 - this.colorTab.length;
        for (int i2 = 0; i2 < length; i2++) {
            this.out.write(0);
        }
    }

    private void writePixels() throws IOException {
        new LZWEncoder(this.width, this.height, this.indexedPixels, this.colorDepth).encode(this.out);
    }

    private void writeShort(int i2) throws IOException {
        this.out.write(i2 & 255);
        this.out.write((i2 >> 8) & 255);
    }

    private void writeString(String str) throws IOException {
        for (int i2 = 0; i2 < str.length(); i2++) {
            this.out.write((byte) str.charAt(i2));
        }
    }

    public boolean addFrame(Bitmap bitmap) {
        if (bitmap == null || !this.started) {
            return false;
        }
        try {
            if (!this.sizeSet) {
                setSize(bitmap.getWidth(), bitmap.getHeight());
            }
            this.image = bitmap;
            getImagePixels();
            analyzePixels();
            if (this.firstFrame) {
                writeLSD();
                writePalette();
                if (this.repeat >= 0) {
                    writeNetscapeExt();
                }
            }
            writeGraphicCtrlExt();
            writeImageDesc();
            if (!this.firstFrame) {
                writePalette();
            }
            writePixels();
            this.firstFrame = false;
            return true;
        } catch (IOException e2) {
            Log.e(TAG, e2.getMessage());
            return false;
        }
    }

    public boolean finish() throws IOException {
        boolean z2;
        if (!this.started) {
            return false;
        }
        this.started = false;
        try {
            this.out.write(59);
            this.out.flush();
            if (this.closeStream) {
                this.out.close();
            }
            z2 = true;
        } catch (IOException e2) {
            Log.e(TAG, e2.getMessage());
            z2 = false;
        }
        this.transIndex = 0;
        this.out = null;
        this.image = null;
        this.pixels = null;
        this.indexedPixels = null;
        this.colorTab = null;
        this.closeStream = false;
        this.firstFrame = true;
        return z2;
    }

    public void setDelay(int i2) {
        this.delay = Math.round(i2 / 10.0f);
    }

    public void setDispose(int i2) {
        if (i2 >= 0) {
            this.dispose = i2;
        }
    }

    public void setFrameRate(float f2) {
        if (f2 != 0.0f) {
            this.delay = Math.round(100.0f / f2);
        }
    }

    public void setQuality(int i2) {
        if (i2 < 1) {
            i2 = 1;
        }
        this.sample = i2;
    }

    public void setRepeat(int i2) {
        if (i2 >= 0) {
            this.repeat = i2;
        }
    }

    public void setSize(int i2, int i3) {
        if (!this.started || this.firstFrame) {
            this.width = i2;
            this.height = i3;
            if (i2 < 1) {
                this.width = 320;
            }
            if (i3 < 1) {
                this.height = 240;
            }
            this.sizeSet = true;
        }
    }

    public void setTransparent(int i2) {
        this.transparent = Integer.valueOf(i2);
    }

    public boolean start(OutputStream outputStream) {
        boolean z2 = false;
        if (outputStream == null) {
            return false;
        }
        this.closeStream = false;
        this.out = outputStream;
        try {
            writeString("GIF89a");
            z2 = true;
        } catch (IOException e2) {
            Log.e(TAG, e2.getMessage());
        }
        this.started = z2;
        return z2;
    }

    public boolean start(String str) {
        boolean zStart;
        try {
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(str));
            this.out = bufferedOutputStream;
            zStart = start(bufferedOutputStream);
            this.closeStream = true;
        } catch (IOException e2) {
            Log.e(TAG, e2.getMessage());
            zStart = false;
        }
        this.started = zStart;
        return zStart;
    }
}
