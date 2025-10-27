package com.actionbarsherlock.widget.wheelview.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/* loaded from: classes.dex */
public class ArrayWheelAdapter<T> extends AbstractWheelTextAdapter {
    private T[] items;
    private String label;
    private int position;

    public ArrayWheelAdapter(Context context, T[] tArr, int i2) {
        super(context);
        this.label = "";
        this.position = i2;
        this.items = tArr;
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
            Object itemText = getItemText(i2);
            if (itemText == null) {
                itemText = "";
            }
            textView.setText(itemText + this.label);
            if (this.itemResourceId == -1) {
                configureTextView(textView, AbstractWheelTextAdapter.DEFAULT_TEXT_COLOR, 20);
            }
            if (i2 == this.position) {
                configureTextView(textView, -16750410, 23);
            }
        }
        return view;
    }

    @Override // com.actionbarsherlock.widget.wheelview.adapter.AbstractWheelTextAdapter
    public CharSequence getItemText(int i2) {
        if (i2 < 0) {
            return null;
        }
        T[] tArr = this.items;
        if (i2 >= tArr.length) {
            return null;
        }
        T t2 = tArr[i2];
        return t2 instanceof CharSequence ? (CharSequence) t2 : t2.toString();
    }

    @Override // com.actionbarsherlock.widget.wheelview.adapter.WheelViewAdapter
    public int getItemsCount() {
        return this.items.length;
    }

    public void setLabel(String str) {
        this.label = str;
    }
}
