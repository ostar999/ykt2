package com.jakewharton.rxbinding2.view;

import android.view.View;
import androidx.annotation.NonNull;

/* loaded from: classes4.dex */
final class AutoValue_ViewScrollChangeEvent extends ViewScrollChangeEvent {
    private final int oldScrollX;
    private final int oldScrollY;
    private final int scrollX;
    private final int scrollY;
    private final View view;

    public AutoValue_ViewScrollChangeEvent(View view, int i2, int i3, int i4, int i5) {
        if (view == null) {
            throw new NullPointerException("Null view");
        }
        this.view = view;
        this.scrollX = i2;
        this.scrollY = i3;
        this.oldScrollX = i4;
        this.oldScrollY = i5;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ViewScrollChangeEvent)) {
            return false;
        }
        ViewScrollChangeEvent viewScrollChangeEvent = (ViewScrollChangeEvent) obj;
        return this.view.equals(viewScrollChangeEvent.view()) && this.scrollX == viewScrollChangeEvent.scrollX() && this.scrollY == viewScrollChangeEvent.scrollY() && this.oldScrollX == viewScrollChangeEvent.oldScrollX() && this.oldScrollY == viewScrollChangeEvent.oldScrollY();
    }

    public int hashCode() {
        return ((((((((this.view.hashCode() ^ 1000003) * 1000003) ^ this.scrollX) * 1000003) ^ this.scrollY) * 1000003) ^ this.oldScrollX) * 1000003) ^ this.oldScrollY;
    }

    @Override // com.jakewharton.rxbinding2.view.ViewScrollChangeEvent
    public int oldScrollX() {
        return this.oldScrollX;
    }

    @Override // com.jakewharton.rxbinding2.view.ViewScrollChangeEvent
    public int oldScrollY() {
        return this.oldScrollY;
    }

    @Override // com.jakewharton.rxbinding2.view.ViewScrollChangeEvent
    public int scrollX() {
        return this.scrollX;
    }

    @Override // com.jakewharton.rxbinding2.view.ViewScrollChangeEvent
    public int scrollY() {
        return this.scrollY;
    }

    public String toString() {
        return "ViewScrollChangeEvent{view=" + this.view + ", scrollX=" + this.scrollX + ", scrollY=" + this.scrollY + ", oldScrollX=" + this.oldScrollX + ", oldScrollY=" + this.oldScrollY + "}";
    }

    @Override // com.jakewharton.rxbinding2.view.ViewScrollChangeEvent
    @NonNull
    public View view() {
        return this.view;
    }
}
