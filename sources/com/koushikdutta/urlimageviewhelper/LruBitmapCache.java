package com.koushikdutta.urlimageviewhelper;

import android.graphics.Bitmap;

/* loaded from: classes4.dex */
public class LruBitmapCache extends LruCache<String, Bitmap> {
    public LruBitmapCache(int i2) {
        super(i2);
    }

    @Override // com.koushikdutta.urlimageviewhelper.LruCache
    public int sizeOf(String str, Bitmap bitmap) {
        return bitmap.getRowBytes() * bitmap.getHeight();
    }
}
