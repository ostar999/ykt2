package com.actionbarsherlock.widget.wheelview.adapter;

import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;

/* loaded from: classes.dex */
public interface WheelViewAdapter {
    View getEmptyItem(View view, ViewGroup viewGroup);

    View getItem(int i2, View view, ViewGroup viewGroup);

    int getItemsCount();

    void registerDataSetObserver(DataSetObserver dataSetObserver);

    void unregisterDataSetObserver(DataSetObserver dataSetObserver);
}
