package androidx.constraintlayout.core.motion.utils;

/* loaded from: classes.dex */
public class Rect {
    public int bottom;
    public int left;
    public int right;

    /* renamed from: top, reason: collision with root package name */
    public int f1774top;

    public int height() {
        return this.bottom - this.f1774top;
    }

    public int width() {
        return this.right - this.left;
    }
}
