package net.center.blurview.impl;

import android.content.Context;
import android.graphics.Bitmap;

/* loaded from: classes9.dex */
public interface BlurImpl {
    void blur(Bitmap bitmap, Bitmap bitmap2);

    boolean prepare(Context context, Bitmap bitmap, float f2);

    void release();
}
