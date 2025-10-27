package com.actionbarsherlock.widget.wheelview.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/* loaded from: classes.dex */
public class NumericWheelAdapter extends AbstractWheelTextAdapter {
    public static final int DEFAULT_MAX_VALUE = 9;
    private static final int DEFAULT_MIN_VALUE = 0;
    private String format;
    private String label;
    private int maxValue;
    private int minValue;

    public NumericWheelAdapter(Context context) {
        this(context, 0, 9);
    }

    @Override // com.actionbarsherlock.widget.wheelview.adapter.AbstractWheelTextAdapter, com.actionbarsherlock.widget.wheelview.adapter.WheelViewAdapter
    public View getItem(int i2, View view, ViewGroup viewGroup) {
        if (i2 < 0 || i2 >= getItemsCount()) {
            return null;
        }
        if (view == null) {
            view = getView(this.itemResourceId, viewGroup);
        }
        TextView textView = getTextView(view, this.itemTextResourceId);
        if (textView != null) {
            CharSequence itemText = getItemText(i2);
            if (itemText == null) {
                itemText = "";
            }
            textView.setText(((Object) itemText) + this.label);
            if (this.itemResourceId == -1) {
                configureTextView(textView, AbstractWheelTextAdapter.DEFAULT_TEXT_COLOR, 20);
            }
        }
        return view;
    }

    @Override // com.actionbarsherlock.widget.wheelview.adapter.AbstractWheelTextAdapter
    public CharSequence getItemText(int i2) {
        if (i2 < 0 || i2 >= getItemsCount()) {
            return null;
        }
        int i3 = this.minValue + i2;
        String str = this.format;
        return str != null ? String.format(str, Integer.valueOf(i3)) : Integer.toString(i3);
    }

    @Override // com.actionbarsherlock.widget.wheelview.adapter.WheelViewAdapter
    public int getItemsCount() {
        return (this.maxValue - this.minValue) + 1;
    }

    public void setLabel(String str) {
        this.label = str;
    }

    public NumericWheelAdapter(Context context, int i2, int i3) {
        this(context, i2, i3, null);
    }

    public NumericWheelAdapter(Context context, int i2, int i3, String str) {
        super(context);
        this.minValue = i2;
        this.maxValue = i3;
        this.format = str;
    }
}
