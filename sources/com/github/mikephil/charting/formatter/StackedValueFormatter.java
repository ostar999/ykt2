package com.github.mikephil.charting.formatter;

import cn.hutool.core.text.StrPool;
import com.github.mikephil.charting.data.BarEntry;
import java.text.DecimalFormat;

/* loaded from: classes3.dex */
public class StackedValueFormatter extends ValueFormatter {
    private boolean mDrawWholeStack;
    private DecimalFormat mFormat;
    private String mSuffix;

    public StackedValueFormatter(boolean z2, String str, int i2) {
        this.mDrawWholeStack = z2;
        this.mSuffix = str;
        StringBuffer stringBuffer = new StringBuffer();
        for (int i3 = 0; i3 < i2; i3++) {
            if (i3 == 0) {
                stringBuffer.append(StrPool.DOT);
            }
            stringBuffer.append("0");
        }
        this.mFormat = new DecimalFormat("###,###,###,##0" + stringBuffer.toString());
    }

    @Override // com.github.mikephil.charting.formatter.ValueFormatter
    public String getBarStackedLabel(float f2, BarEntry barEntry) {
        float[] yVals;
        if (this.mDrawWholeStack || (yVals = barEntry.getYVals()) == null) {
            return this.mFormat.format(f2) + this.mSuffix;
        }
        if (yVals[yVals.length - 1] != f2) {
            return "";
        }
        return this.mFormat.format(barEntry.getY()) + this.mSuffix;
    }
}
