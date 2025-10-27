package com.github.mikephil.charting.buffer;

import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

/* loaded from: classes3.dex */
public class BarBuffer extends AbstractBuffer<IBarDataSet> {
    protected float mBarWidth;
    protected boolean mContainsStacks;
    protected int mDataSetCount;
    protected int mDataSetIndex;
    protected boolean mInverted;

    public BarBuffer(int i2, int i3, boolean z2) {
        super(i2);
        this.mDataSetIndex = 0;
        this.mInverted = false;
        this.mBarWidth = 1.0f;
        this.mDataSetCount = i3;
        this.mContainsStacks = z2;
    }

    public void addBar(float f2, float f3, float f4, float f5) {
        float[] fArr = this.buffer;
        int i2 = this.index;
        int i3 = i2 + 1;
        fArr[i2] = f2;
        int i4 = i3 + 1;
        fArr[i3] = f3;
        int i5 = i4 + 1;
        fArr[i4] = f4;
        this.index = i5 + 1;
        fArr[i5] = f5;
    }

    public void setBarWidth(float f2) {
        this.mBarWidth = f2;
    }

    public void setDataSet(int i2) {
        this.mDataSetIndex = i2;
    }

    public void setInverted(boolean z2) {
        this.mInverted = z2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.github.mikephil.charting.buffer.AbstractBuffer
    public void feed(IBarDataSet iBarDataSet) {
        float f2;
        float fAbs;
        float fAbs2;
        float f3;
        float entryCount = iBarDataSet.getEntryCount() * this.phaseX;
        float f4 = this.mBarWidth / 2.0f;
        for (int i2 = 0; i2 < entryCount; i2++) {
            BarEntry barEntry = (BarEntry) iBarDataSet.getEntryForIndex(i2);
            if (barEntry != null) {
                float x2 = barEntry.getX();
                float y2 = barEntry.getY();
                float[] yVals = barEntry.getYVals();
                if (!this.mContainsStacks || yVals == null) {
                    float f5 = x2 - f4;
                    float f6 = x2 + f4;
                    if (this.mInverted) {
                        f2 = y2 >= 0.0f ? y2 : 0.0f;
                        if (y2 > 0.0f) {
                            y2 = 0.0f;
                        }
                    } else {
                        float f7 = y2 >= 0.0f ? y2 : 0.0f;
                        if (y2 > 0.0f) {
                            y2 = 0.0f;
                        }
                        float f8 = y2;
                        y2 = f7;
                        f2 = f8;
                    }
                    if (y2 > 0.0f) {
                        y2 *= this.phaseY;
                    } else {
                        f2 *= this.phaseY;
                    }
                    addBar(f5, y2, f6, f2);
                } else {
                    float f9 = -barEntry.getNegativeSum();
                    float f10 = 0.0f;
                    int i3 = 0;
                    while (i3 < yVals.length) {
                        float f11 = yVals[i3];
                        if (f11 == 0.0f && (f10 == 0.0f || f9 == 0.0f)) {
                            fAbs = f11;
                            fAbs2 = f9;
                            f9 = fAbs;
                        } else if (f11 >= 0.0f) {
                            fAbs = f11 + f10;
                            fAbs2 = f9;
                            f9 = f10;
                            f10 = fAbs;
                        } else {
                            fAbs = Math.abs(f11) + f9;
                            fAbs2 = Math.abs(f11) + f9;
                        }
                        float f12 = x2 - f4;
                        float f13 = x2 + f4;
                        if (this.mInverted) {
                            f3 = f9 >= fAbs ? f9 : fAbs;
                            if (f9 > fAbs) {
                                f9 = fAbs;
                            }
                        } else {
                            float f14 = f9 >= fAbs ? f9 : fAbs;
                            if (f9 > fAbs) {
                                f9 = fAbs;
                            }
                            float f15 = f9;
                            f9 = f14;
                            f3 = f15;
                        }
                        float f16 = this.phaseY;
                        addBar(f12, f9 * f16, f13, f3 * f16);
                        i3++;
                        f9 = fAbs2;
                    }
                }
            }
        }
        reset();
    }
}
