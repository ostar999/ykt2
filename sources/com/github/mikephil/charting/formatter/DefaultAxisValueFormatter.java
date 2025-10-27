package com.github.mikephil.charting.formatter;

import cn.hutool.core.text.StrPool;
import java.text.DecimalFormat;

/* loaded from: classes3.dex */
public class DefaultAxisValueFormatter extends ValueFormatter {
    protected int digits;
    protected DecimalFormat mFormat;

    public DefaultAxisValueFormatter(int i2) {
        this.digits = i2;
        StringBuffer stringBuffer = new StringBuffer();
        for (int i3 = 0; i3 < i2; i3++) {
            if (i3 == 0) {
                stringBuffer.append(StrPool.DOT);
            }
            stringBuffer.append("0");
        }
        this.mFormat = new DecimalFormat("###,###,###,##0" + stringBuffer.toString());
    }

    public int getDecimalDigits() {
        return this.digits;
    }

    @Override // com.github.mikephil.charting.formatter.ValueFormatter
    public String getFormattedValue(float f2) {
        return this.mFormat.format(f2);
    }
}
