package com.jakewharton.rxbinding2.widget;

import android.view.View;
import android.widget.AdapterView;
import androidx.annotation.NonNull;

/* loaded from: classes4.dex */
final class AutoValue_AdapterViewItemClickEvent extends AdapterViewItemClickEvent {
    private final View clickedView;
    private final long id;
    private final int position;
    private final AdapterView<?> view;

    public AutoValue_AdapterViewItemClickEvent(AdapterView<?> adapterView, View view, int i2, long j2) {
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

    @Override // com.jakewharton.rxbinding2.widget.AdapterViewItemClickEvent
    @NonNull
    public View clickedView() {
        return this.clickedView;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof AdapterViewItemClickEvent)) {
            return false;
        }
        AdapterViewItemClickEvent adapterViewItemClickEvent = (AdapterViewItemClickEvent) obj;
        return this.view.equals(adapterViewItemClickEvent.view()) && this.clickedView.equals(adapterViewItemClickEvent.clickedView()) && this.position == adapterViewItemClickEvent.position() && this.id == adapterViewItemClickEvent.id();
    }

    public int hashCode() {
        int iHashCode = (((((this.view.hashCode() ^ 1000003) * 1000003) ^ this.clickedView.hashCode()) * 1000003) ^ this.position) * 1000003;
        long j2 = this.id;
        return iHashCode ^ ((int) (j2 ^ (j2 >>> 32)));
    }

    @Override // com.jakewharton.rxbinding2.widget.AdapterViewItemClickEvent
    public long id() {
        return this.id;
    }

    @Override // com.jakewharton.rxbinding2.widget.AdapterViewItemClickEvent
    public int position() {
        return this.position;
    }

    public String toString() {
        return "AdapterViewItemClickEvent{view=" + this.view + ", clickedView=" + this.clickedView + ", position=" + this.position + ", id=" + this.id + "}";
    }

    @Override // com.jakewharton.rxbinding2.widget.AdapterViewItemClickEvent
    @NonNull
    public AdapterView<?> view() {
        return this.view;
    }
}
