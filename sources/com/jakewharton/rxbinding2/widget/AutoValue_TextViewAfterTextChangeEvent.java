package com.jakewharton.rxbinding2.widget;

import android.text.Editable;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/* loaded from: classes4.dex */
final class AutoValue_TextViewAfterTextChangeEvent extends TextViewAfterTextChangeEvent {
    private final Editable editable;
    private final TextView view;

    public AutoValue_TextViewAfterTextChangeEvent(TextView textView, @Nullable Editable editable) {
        if (textView == null) {
            throw new NullPointerException("Null view");
        }
        this.view = textView;
        this.editable = editable;
    }

    @Override // com.jakewharton.rxbinding2.widget.TextViewAfterTextChangeEvent
    @Nullable
    public Editable editable() {
        return this.editable;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof TextViewAfterTextChangeEvent)) {
            return false;
        }
        TextViewAfterTextChangeEvent textViewAfterTextChangeEvent = (TextViewAfterTextChangeEvent) obj;
        if (this.view.equals(textViewAfterTextChangeEvent.view())) {
            Editable editable = this.editable;
            if (editable == null) {
                if (textViewAfterTextChangeEvent.editable() == null) {
                    return true;
                }
            } else if (editable.equals(textViewAfterTextChangeEvent.editable())) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        int iHashCode = (this.view.hashCode() ^ 1000003) * 1000003;
        Editable editable = this.editable;
        return iHashCode ^ (editable == null ? 0 : editable.hashCode());
    }

    public String toString() {
        return "TextViewAfterTextChangeEvent{view=" + this.view + ", editable=" + ((Object) this.editable) + "}";
    }

    @Override // com.jakewharton.rxbinding2.widget.TextViewAfterTextChangeEvent
    @NonNull
    public TextView view() {
        return this.view;
    }
}
