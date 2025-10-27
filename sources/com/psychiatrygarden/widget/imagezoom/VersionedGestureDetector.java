package com.psychiatrygarden.widget.imagezoom;

import android.content.Context;

/* loaded from: classes6.dex */
public final class VersionedGestureDetector {
    public static GestureDetector newInstance(Context context, OnGestureListener listener) {
        FroyoGestureDetector froyoGestureDetector = new FroyoGestureDetector(context);
        froyoGestureDetector.setOnGestureListener(listener);
        return froyoGestureDetector;
    }
}
