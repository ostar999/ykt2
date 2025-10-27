package com.easefun.polyv.mediasdk.gifmaker;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

/* loaded from: classes3.dex */
public class GifMaker {
    private static final String TAG = "GifMaker";
    private OnGifListener mGifListener = null;
    private AnimatedGifEncoder mEncoder = null;

    public interface OnGifListener {
        void onError(Throwable th);

        void onFinish(byte[] bArr, int i2, int i3, int i4);

        void onMake(int i2, int i3, int i4);
    }

    public void cancel() throws IOException {
        AnimatedGifEncoder animatedGifEncoder = this.mEncoder;
        if (animatedGifEncoder != null) {
            animatedGifEncoder.finish();
        }
    }

    public void makeGif(List<Bitmap> list, float f2, float f3, float f4) throws IOException {
        this.mEncoder = new AnimatedGifEncoder();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        this.mEncoder.start(byteArrayOutputStream);
        this.mEncoder.setRepeat(0);
        this.mEncoder.setFrameRate(10.0f);
        long jCurrentTimeMillis = System.currentTimeMillis();
        int size = list.size();
        int width = 0;
        int height = 0;
        for (int i2 = 0; i2 < size && list.size() != 0; i2++) {
            Bitmap bitmap = list.get(i2);
            if (bitmap != null) {
                Bitmap bitmapCreateBitmap = null;
                try {
                    try {
                        long jCurrentTimeMillis2 = System.currentTimeMillis();
                        Matrix matrix = new Matrix();
                        matrix.postScale(f2, f3);
                        matrix.postRotate(f4);
                        bitmapCreateBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
                        if (width == 0 || height == 0) {
                            width = bitmapCreateBitmap.getWidth();
                            height = bitmapCreateBitmap.getHeight();
                        }
                        this.mEncoder.addFrame(bitmapCreateBitmap);
                        OnGifListener onGifListener = this.mGifListener;
                        if (onGifListener != null) {
                            onGifListener.onMake(i2 + 1, size, (int) (System.currentTimeMillis() - jCurrentTimeMillis2));
                        }
                        if (bitmapCreateBitmap != null && !bitmapCreateBitmap.isRecycled()) {
                            bitmapCreateBitmap.recycle();
                        }
                    } catch (Exception unused) {
                        System.gc();
                        if (bitmapCreateBitmap != null && !bitmapCreateBitmap.isRecycled()) {
                            bitmapCreateBitmap.recycle();
                        }
                    }
                } catch (Throwable th) {
                    if (bitmapCreateBitmap != null && !bitmapCreateBitmap.isRecycled()) {
                        bitmapCreateBitmap.recycle();
                    }
                    throw th;
                }
            }
        }
        AnimatedGifEncoder animatedGifEncoder = this.mEncoder;
        if (animatedGifEncoder.started) {
            animatedGifEncoder.finish();
            OnGifListener onGifListener2 = this.mGifListener;
            if (onGifListener2 != null) {
                onGifListener2.onFinish(byteArrayOutputStream.toByteArray(), width, height, (int) ((System.currentTimeMillis() - jCurrentTimeMillis) / 1000));
            }
        }
    }

    public void setOnGifListener(OnGifListener onGifListener) {
        this.mGifListener = onGifListener;
    }
}
