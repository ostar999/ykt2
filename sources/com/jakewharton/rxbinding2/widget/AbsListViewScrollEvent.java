package com.jakewharton.rxbinding2.widget;

import android.widget.AbsListView;
import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;
import com.google.auto.value.AutoValue;

@AutoValue
/* loaded from: classes4.dex */
public abstract class AbsListViewScrollEvent {
    @NonNull
    @CheckResult
    public static AbsListViewScrollEvent create(AbsListView absListView, int i2, int i3, int i4, int i5) {
        return new AutoValue_AbsListViewScrollEvent(absListView, i2, i3, i4, i5);
    }

    public abstract int firstVisibleItem();

    public abstract int scrollState();

    public abstract int totalItemCount();

    @NonNull
    public abstract AbsListView view();

    public abstract int visibleItemCount();
}
