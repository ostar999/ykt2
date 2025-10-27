package com.jakewharton.rxbinding2.widget;

import android.widget.AdapterView;
import androidx.annotation.NonNull;

/* loaded from: classes4.dex */
final class AutoValue_AdapterViewNothingSelectionEvent extends AdapterViewNothingSelectionEvent {
    private final AdapterView<?> view;

    public AutoValue_AdapterViewNothingSelectionEvent(AdapterView<?> adapterView) {
        if (adapterView == null) {
            throw new NullPointerException("Null view");
        }
        this.view = adapterView;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof AdapterViewNothingSelectionEvent) {
            return this.view.equals(((AdapterViewNothingSelectionEvent) obj).view());
        }
        return false;
    }

    public int hashCode() {
        return this.view.hashCode() ^ 1000003;
    }

    public String toString() {
        return "AdapterViewNothingSelectionEvent{view=" + this.view + "}";
    }

    @Override // com.jakewharton.rxbinding2.widget.AdapterViewSelectionEvent
    @NonNull
    public AdapterView<?> view() {
        return this.view;
    }
}
