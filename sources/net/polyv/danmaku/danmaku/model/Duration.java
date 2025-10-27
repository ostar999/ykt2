package net.polyv.danmaku.danmaku.model;

/* loaded from: classes9.dex */
public class Duration implements Cloneable {
    private float factor = 1.0f;
    private long mInitialDuration;
    public long value;

    public Duration(long j2) {
        this.mInitialDuration = j2;
        this.value = j2;
    }

    public void setFactor(float f2) {
        if (this.factor != f2) {
            this.factor = f2;
            this.value = (long) (this.mInitialDuration * f2);
        }
    }

    public void setValue(long j2) {
        this.mInitialDuration = j2;
        this.value = (long) (j2 * this.factor);
    }
}
