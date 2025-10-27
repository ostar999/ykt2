package com.github.mikephil.charting.buffer;

/* loaded from: classes3.dex */
public abstract class AbstractBuffer<T> {
    public final float[] buffer;
    protected float phaseX = 1.0f;
    protected float phaseY = 1.0f;
    protected int mFrom = 0;
    protected int mTo = 0;
    protected int index = 0;

    public AbstractBuffer(int i2) {
        this.buffer = new float[i2];
    }

    public abstract void feed(T t2);

    public void limitFrom(int i2) {
        if (i2 < 0) {
            i2 = 0;
        }
        this.mFrom = i2;
    }

    public void limitTo(int i2) {
        if (i2 < 0) {
            i2 = 0;
        }
        this.mTo = i2;
    }

    public void reset() {
        this.index = 0;
    }

    public void setPhases(float f2, float f3) {
        this.phaseX = f2;
        this.phaseY = f3;
    }

    public int size() {
        return this.buffer.length;
    }
}
