package net.tsz.afinal.bitmap.core;

import android.graphics.Bitmap;

/* loaded from: classes9.dex */
public interface IMemoryCache {
    void evictAll();

    Bitmap get(String str);

    void put(String str, Bitmap bitmap);

    void remove(String str);
}
