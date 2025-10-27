package com.jakewharton.rxbinding2.widget;

import android.widget.AbsListView;
import androidx.annotation.NonNull;

/* loaded from: classes4.dex */
final class AutoValue_AbsListViewScrollEvent extends AbsListViewScrollEvent {
    private final int firstVisibleItem;
    private final int scrollState;
    private final int totalItemCount;
    private final AbsListView view;
    private final int visibleItemCount;

    public AutoValue_AbsListViewScrollEvent(AbsListView absListView, int i2, int i3, int i4, int i5) {
        if (absListView == null) {
            throw new NullPointerException("Null view");
        }
        this.view = absListView;
        this.scrollState = i2;
        this.firstVisibleItem = i3;
        this.visibleItemCount = i4;
        this.totalItemCount = i5;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof AbsListViewScrollEvent)) {
            return false;
        }
        AbsListViewScrollEvent absListViewScrollEvent = (AbsListViewScrollEvent) obj;
        return this.view.equals(absListViewScrollEvent.view()) && this.scrollState == absListViewScrollEvent.scrollState() && this.firstVisibleItem == absListViewScrollEvent.firstVisibleItem() && this.visibleItemCount == absListViewScrollEvent.visibleItemCount() && this.totalItemCount == absListViewScrollEvent.totalItemCount();
    }

    @Override // com.jakewharton.rxbinding2.widget.AbsListViewScrollEvent
    public int firstVisibleItem() {
        return this.firstVisibleItem;
    }

    public int hashCode() {
        return ((((((((this.view.hashCode() ^ 1000003) * 1000003) ^ this.scrollState) * 1000003) ^ this.firstVisibleItem) * 1000003) ^ this.visibleItemCount) * 1000003) ^ this.totalItemCount;
    }

    @Override // com.jakewharton.rxbinding2.widget.AbsListViewScrollEvent
    public int scrollState() {
        return this.scrollState;
    }

    public String toString() {
        return "AbsListViewScrollEvent{view=" + this.view + ", scrollState=" + this.scrollState + ", firstVisibleItem=" + this.firstVisibleItem + ", visibleItemCount=" + this.visibleItemCount + ", totalItemCount=" + this.totalItemCount + "}";
    }

    @Override // com.jakewharton.rxbinding2.widget.AbsListViewScrollEvent
    public int totalItemCount() {
        return this.totalItemCount;
    }

    @Override // com.jakewharton.rxbinding2.widget.AbsListViewScrollEvent
    @NonNull
    public AbsListView view() {
        return this.view;
    }

    @Override // com.jakewharton.rxbinding2.widget.AbsListViewScrollEvent
    public int visibleItemCount() {
        return this.visibleItemCount;
    }
}
