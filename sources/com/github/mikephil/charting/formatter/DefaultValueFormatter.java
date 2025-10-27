package com.github.mikephil.charting.formatter;

import cn.hutool.core.text.StrPool;
import java.text.DecimalFormat;

/* loaded from: classes3.dex */
public class DefaultValueFormatter extends ValueFormatter {
    protected int mDecimalDigits;
    protected DecimalFormat mFormat;

    public DefaultValueFormatter(int i2) {
        setup(i2);
    }

    public int getDecimalDigits() {
        return this.mDecimalDigits;
    }

    @Override // com.github.mikephil.charting.formatter.ValueFormatter
    public String getFormattedValue(float f2) {
        return this.mFormat.format(f2);
    }

    public void setup(int i2) {
        this.mDecimalDigits = i2;
        StringBuffer stringBuffer = new StringBuffer();
        for (int i3 = 0; i3 < i2; i3++) {
            if (i3 == 0) {
                stringBuffer.append(StrPool.DOT);
            }
            stringBuffer.append("0");
        }
        this.mFormat = new DecimalFormat("###,###,###,##0" + stringBuffer.toString());
    }
}
