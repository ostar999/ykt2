package androidx.constraintlayout.motion.utils;

import android.view.View;

/* loaded from: classes.dex */
public class ViewState {
    public int bottom;
    public int left;
    public int right;
    public float rotation;

    /* renamed from: top, reason: collision with root package name */
    public int f1784top;

    public void getState(View v2) {
        this.left = v2.getLeft();
        this.f1784top = v2.getTop();
        this.right = v2.getRight();
        this.bottom = v2.getBottom();
        this.rotation = v2.getRotation();
    }

    public int height() {
        return this.bottom - this.f1784top;
    }

    public int width() {
        return this.right - this.left;
    }
}
