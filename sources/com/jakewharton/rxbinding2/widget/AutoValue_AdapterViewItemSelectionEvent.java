package com.jakewharton.rxbinding2.widget;

import android.view.View;
import android.widget.AdapterView;
import androidx.annotation.NonNull;

/* loaded from: classes4.dex */
final class AutoValue_AdapterViewItemSelectionEvent extends AdapterViewItemSelectionEvent {
    private final long id;
    private final int position;
    private final View selectedView;
    private final AdapterView<?> view;

    public AutoValue_AdapterViewItemSelectionEvent(AdapterView<?> adapterView, View view, int i2, long j2) {
        if (adapterView == null) {
            throw new NullPointerException("Null view");
        }
        this.view = adapterView;
        if (view == null) {
            throw new NullPointerException("Null selectedView");
        }
        this.selectedView = view;
        this.position = i2;
        this.id = j2;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof AdapterViewItemSelectionEvent)) {
            return false;
        }
        AdapterViewItemSelectionEvent adapterViewItemSelectionEvent = (AdapterViewItemSelectionEvent) obj;
        return this.view.equals(adapterViewItemSelectionEvent.view()) && this.selectedView.equals(adapterViewItemSelectionEvent.selectedView()) && this.position == adapterViewItemSelectionEvent.position() && this.id == adapterViewItemSelectionEvent.id();
    }

    public int hashCode() {
        int iHashCode = (((((this.view.hashCode() ^ 1000003) * 1000003) ^ this.selectedView.hashCode()) * 1000003) ^ this.position) * 1000003;
        long j2 = this.id;
        return iHashCode ^ ((int) (j2 ^ (j2 >>> 32)));
    }

    @Override // com.jakewharton.rxbinding2.widget.AdapterViewItemSelectionEvent
    public long id() {
        return this.id;
    }

    @Override // com.jakewharton.rxbinding2.widget.AdapterViewItemSelectionEvent
    public int position() {
        return this.position;
    }

    @Override // com.jakewharton.rxbinding2.widget.AdapterViewItemSelectionEvent
    @NonNull
    public View selectedView() {
        return this.selectedView;
    }

    public String toString() {
        return "AdapterViewItemSelectionEvent{view=" + this.view + ", selectedView=" + this.selectedView + ", position=" + this.position + ", id=" + this.id + "}";
    }

    @Override // com.jakewharton.rxbinding2.widget.AdapterViewSelectionEvent
    @NonNull
    public AdapterView<?> view() {
        return this.view;
    }
}
