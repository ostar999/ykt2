package net.center.blurview.impl;

import android.content.Context;
import android.graphics.Bitmap;

/* loaded from: classes9.dex */
public class EmptyBlurImpl implements BlurImpl {
    @Override // net.center.blurview.impl.BlurImpl
    public void blur(Bitmap bitmap, Bitmap bitmap2) {
    }

    @Override // net.center.blurview.impl.BlurImpl
    public boolean prepare(Context context, Bitmap bitmap, float f2) {
        return false;
    }

    @Override // net.center.blurview.impl.BlurImpl
    public void release() {
    }
}
