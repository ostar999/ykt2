package com.jakewharton.rxbinding2.view;

import android.view.View;
import androidx.annotation.NonNull;

/* loaded from: classes4.dex */
final class AutoValue_ViewAttachAttachedEvent extends ViewAttachAttachedEvent {
    private final View view;

    public AutoValue_ViewAttachAttachedEvent(View view) {
        if (view == null) {
            throw new NullPointerException("Null view");
        }
        this.view = view;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof ViewAttachAttachedEvent) {
            return this.view.equals(((ViewAttachAttachedEvent) obj).view());
        }
        return false;
    }

    public int hashCode() {
        return this.view.hashCode() ^ 1000003;
    }

    public String toString() {
        return "ViewAttachAttachedEvent{view=" + this.view + "}";
    }

    @Override // com.jakewharton.rxbinding2.view.ViewAttachEvent
    @NonNull
    public View view() {
        return this.view;
    }
}
