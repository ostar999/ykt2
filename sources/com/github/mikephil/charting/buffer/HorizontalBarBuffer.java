package com.github.mikephil.charting.buffer;

import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

/* loaded from: classes3.dex */
public class HorizontalBarBuffer extends BarBuffer {
    public HorizontalBarBuffer(int i2, int i3, boolean z2) {
        super(i2, i3, z2);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.github.mikephil.charting.buffer.BarBuffer, com.github.mikephil.charting.buffer.AbstractBuffer
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
                    addBar(f2, f6, y2, f5);
                } else {
                    float f9 = -barEntry.getNegativeSum();
                    float f10 = 0.0f;
                    int i3 = 0;
                    while (i3 < yVals.length) {
                        float f11 = yVals[i3];
                        if (f11 >= 0.0f) {
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
                        addBar(f3 * f16, f13, f9 * f16, f12);
                        i3++;
                        f9 = fAbs2;
                    }
                }
            }
        }
        reset();
    }
}
