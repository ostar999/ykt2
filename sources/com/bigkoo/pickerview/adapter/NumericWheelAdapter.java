package com.bigkoo.pickerview.adapter;

import com.contrarywind.adapter.WheelAdapter;

/* loaded from: classes2.dex */
public class NumericWheelAdapter implements WheelAdapter {
    private int maxValue;
    private int minValue;

    public NumericWheelAdapter(int i2, int i3) {
        this.minValue = i2;
        this.maxValue = i3;
    }

    @Override // com.contrarywind.adapter.WheelAdapter
    public Object getItem(int i2) {
        if (i2 < 0 || i2 >= getItemsCount()) {
            return 0;
        }
        return Integer.valueOf(this.minValue + i2);
    }

    @Override // com.contrarywind.adapter.WheelAdapter
    public int getItemsCount() {
        return (this.maxValue - this.minValue) + 1;
    }

    @Override // com.contrarywind.adapter.WheelAdapter
    public int indexOf(Object obj) {
        try {
            return ((Integer) obj).intValue() - this.minValue;
        } catch (Exception unused) {
            return -1;
        }
    }
}
