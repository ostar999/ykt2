package com.actionbarsherlock.widget.wheelview.adapter;

import android.content.Context;
import com.actionbarsherlock.widget.wheelview.WheelAdapter;

/* loaded from: classes.dex */
public class AdapterWheel extends AbstractWheelTextAdapter {
    private WheelAdapter adapter;

    public AdapterWheel(Context context, WheelAdapter wheelAdapter) {
        super(context);
        this.adapter = wheelAdapter;
    }

    public WheelAdapter getAdapter() {
        return this.adapter;
    }

    @Override // com.actionbarsherlock.widget.wheelview.adapter.AbstractWheelTextAdapter
    public CharSequence getItemText(int i2) {
        return this.adapter.getItem(i2);
    }

    @Override // com.actionbarsherlock.widget.wheelview.adapter.WheelViewAdapter
    public int getItemsCount() {
        return this.adapter.getItemsCount();
    }
}
