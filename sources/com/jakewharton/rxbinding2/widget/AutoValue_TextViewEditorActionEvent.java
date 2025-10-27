package com.jakewharton.rxbinding2.widget;

import android.view.KeyEvent;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/* loaded from: classes4.dex */
final class AutoValue_TextViewEditorActionEvent extends TextViewEditorActionEvent {
    private final int actionId;
    private final KeyEvent keyEvent;
    private final TextView view;

    public AutoValue_TextViewEditorActionEvent(TextView textView, int i2, @Nullable KeyEvent keyEvent) {
        if (textView == null) {
            throw new NullPointerException("Null view");
        }
        this.view = textView;
        this.actionId = i2;
        this.keyEvent = keyEvent;
    }

    @Override // com.jakewharton.rxbinding2.widget.TextViewEditorActionEvent
    public int actionId() {
        return this.actionId;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof TextViewEditorActionEvent)) {
            return false;
        }
        TextViewEditorActionEvent textViewEditorActionEvent = (TextViewEditorActionEvent) obj;
        if (this.view.equals(textViewEditorActionEvent.view()) && this.actionId == textViewEditorActionEvent.actionId()) {
            KeyEvent keyEvent = this.keyEvent;
            if (keyEvent == null) {
                if (textViewEditorActionEvent.keyEvent() == null) {
                    return true;
                }
            } else if (keyEvent.equals(textViewEditorActionEvent.keyEvent())) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        int iHashCode = (((this.view.hashCode() ^ 1000003) * 1000003) ^ this.actionId) * 1000003;
        KeyEvent keyEvent = this.keyEvent;
        return iHashCode ^ (keyEvent == null ? 0 : keyEvent.hashCode());
    }

    @Override // com.jakewharton.rxbinding2.widget.TextViewEditorActionEvent
    @Nullable
    public KeyEvent keyEvent() {
        return this.keyEvent;
    }

    public String toString() {
        return "TextViewEditorActionEvent{view=" + this.view + ", actionId=" + this.actionId + ", keyEvent=" + this.keyEvent + "}";
    }

    @Override // com.jakewharton.rxbinding2.widget.TextViewEditorActionEvent
    @NonNull
    public TextView view() {
        return this.view;
    }
}
