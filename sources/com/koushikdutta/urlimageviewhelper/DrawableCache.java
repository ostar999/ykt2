package com.koushikdutta.urlimageviewhelper;

import android.graphics.drawable.Drawable;

/* loaded from: classes4.dex */
public final class DrawableCache extends SoftReferenceHashTable<String, Drawable> {
    private static DrawableCache mInstance = new DrawableCache();

    private DrawableCache() {
    }

    public static DrawableCache getInstance() {
        return mInstance;
    }
}
