package com.jakewharton.rxbinding2.widget;

import android.widget.TextView;
import androidx.annotation.NonNull;

/* loaded from: classes4.dex */
final class AutoValue_TextViewBeforeTextChangeEvent extends TextViewBeforeTextChangeEvent {
    private final int after;
    private final int count;
    private final int start;
    private final CharSequence text;
    private final TextView view;

    public AutoValue_TextViewBeforeTextChangeEvent(TextView textView, CharSequence charSequence, int i2, int i3, int i4) {
        if (textView == null) {
            throw new NullPointerException("Null view");
        }
        this.view = textView;
        if (charSequence == null) {
            throw new NullPointerException("Null text");
        }
        this.text = charSequence;
        this.start = i2;
        this.count = i3;
        this.after = i4;
    }

    @Override // com.jakewharton.rxbinding2.widget.TextViewBeforeTextChangeEvent
    public int after() {
        return this.after;
    }

    @Override // com.jakewharton.rxbinding2.widget.TextViewBeforeTextChangeEvent
    public int count() {
        return this.count;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof TextViewBeforeTextChangeEvent)) {
            return false;
        }
        TextViewBeforeTextChangeEvent textViewBeforeTextChangeEvent = (TextViewBeforeTextChangeEvent) obj;
        return this.view.equals(textViewBeforeTextChangeEvent.view()) && this.text.equals(textViewBeforeTextChangeEvent.text()) && this.start == textViewBeforeTextChangeEvent.start() && this.count == textViewBeforeTextChangeEvent.count() && this.after == textViewBeforeTextChangeEvent.after();
    }

    public int hashCode() {
        return ((((((((this.view.hashCode() ^ 1000003) * 1000003) ^ this.text.hashCode()) * 1000003) ^ this.start) * 1000003) ^ this.count) * 1000003) ^ this.after;
    }

    @Override // com.jakewharton.rxbinding2.widget.TextViewBeforeTextChangeEvent
    public int start() {
        return this.start;
    }

    @Override // com.jakewharton.rxbinding2.widget.TextViewBeforeTextChangeEvent
    @NonNull
    public CharSequence text() {
        return this.text;
    }

    public String toString() {
        return "TextViewBeforeTextChangeEvent{view=" + this.view + ", text=" + ((Object) this.text) + ", start=" + this.start + ", count=" + this.count + ", after=" + this.after + "}";
    }

    @Override // com.jakewharton.rxbinding2.widget.TextViewBeforeTextChangeEvent
    @NonNull
    public TextView view() {
        return this.view;
    }
}
