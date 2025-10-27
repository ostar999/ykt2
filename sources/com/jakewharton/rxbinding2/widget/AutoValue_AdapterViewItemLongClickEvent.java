package com.jakewharton.rxbinding2.widget;

import android.view.View;
import android.widget.AdapterView;
import androidx.annotation.NonNull;

/* loaded from: classes4.dex */
final class AutoValue_AdapterViewItemLongClickEvent extends AdapterViewItemLongClickEvent {
    private final View clickedView;
    private final long id;
    private final int position;
    private final AdapterView<?> view;

    public AutoValue_AdapterViewItemLongClickEvent(AdapterView<?> adapterView, View view, int i2, long j2) {
        if (adapterView == null) {
            throw new NullPointerException("Null view");
        }
        this.view = adapterView;
        if (view == null) {
            throw new NullPointerException("Null clickedView");
        }
        this.clickedView = view;
        this.position = i2;
        this.id = j2;
    }

    @Override // com.jakewharton.rxbinding2.widget.AdapterViewItemLongClickEvent
    @NonNull
    public View clickedView() {
        return this.clickedView;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof AdapterViewItemLongClickEvent)) {
            return false;
        }
        AdapterViewItemLongClickEvent adapterViewItemLongClickEvent = (AdapterViewItemLongClickEvent) obj;
        return this.view.equals(adapterViewItemLongClickEvent.view()) && this.clickedView.equals(adapterViewItemLongClickEvent.clickedView()) && this.position == adapterViewItemLongClickEvent.position() && this.id == adapterViewItemLongClickEvent.id();
    }

    public int hashCode() {
        int iHashCode = (((((this.view.hashCode() ^ 1000003) * 1000003) ^ this.clickedView.hashCode()) * 1000003) ^ this.position) * 1000003;
        long j2 = this.id;
        return iHashCode ^ ((int) (j2 ^ (j2 >>> 32)));
    }

    @Override // com.jakewharton.rxbinding2.widget.AdapterViewItemLongClickEvent
    public long id() {
        return this.id;
    }

    @Override // com.jakewharton.rxbinding2.widget.AdapterViewItemLongClickEvent
    public int position() {
        return this.position;
    }

    public String toString() {
        return "AdapterViewItemLongClickEvent{view=" + this.view + ", clickedView=" + this.clickedView + ", position=" + this.position + ", id=" + this.id + "}";
    }

    @Override // com.jakewharton.rxbinding2.widget.AdapterViewItemLongClickEvent
    @NonNull
    public AdapterView<?> view() {
        return this.view;
    }
}
