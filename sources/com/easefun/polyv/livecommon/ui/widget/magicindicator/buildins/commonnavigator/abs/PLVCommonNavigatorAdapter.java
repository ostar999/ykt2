package com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.abs;

import android.content.Context;
import android.database.DataSetObservable;
import android.database.DataSetObserver;

/* loaded from: classes3.dex */
public abstract class PLVCommonNavigatorAdapter {
    private final DataSetObservable mDataSetObservable = new DataSetObservable();

    public abstract int getCount();

    public abstract IPLVPagerIndicator getIndicator(Context context);

    public abstract IPLVPagerTitleView getTitleView(Context context, int index);

    public float getTitleWeight(Context context, int index) {
        return 1.0f;
    }

    public final void notifyDataSetChanged() {
        this.mDataSetObservable.notifyChanged();
    }

    public final void notifyDataSetInvalidated() {
        this.mDataSetObservable.notifyInvalidated();
    }

    public final void registerDataSetObserver(DataSetObserver observer) {
        this.mDataSetObservable.registerObserver(observer);
    }

    public final void unregisterDataSetObserver(DataSetObserver observer) {
        this.mDataSetObservable.unregisterObserver(observer);
    }
}
