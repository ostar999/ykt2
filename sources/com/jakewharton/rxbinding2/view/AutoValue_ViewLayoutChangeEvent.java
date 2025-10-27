package com.jakewharton.rxbinding2.view;

import android.view.View;
import androidx.annotation.NonNull;

/* loaded from: classes4.dex */
final class AutoValue_ViewLayoutChangeEvent extends ViewLayoutChangeEvent {
    private final int bottom;
    private final int left;
    private final int oldBottom;
    private final int oldLeft;
    private final int oldRight;
    private final int oldTop;
    private final int right;

    /* renamed from: top, reason: collision with root package name */
    private final int f8881top;
    private final View view;

    public AutoValue_ViewLayoutChangeEvent(View view, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
        if (view == null) {
            throw new NullPointerException("Null view");
        }
        this.view = view;
        this.left = i2;
        this.f8881top = i3;
        this.right = i4;
        this.bottom = i5;
        this.oldLeft = i6;
        this.oldTop = i7;
        this.oldRight = i8;
        this.oldBottom = i9;
    }

    @Override // com.jakewharton.rxbinding2.view.ViewLayoutChangeEvent
    public int bottom() {
        return this.bottom;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ViewLayoutChangeEvent)) {
            return false;
        }
        ViewLayoutChangeEvent viewLayoutChangeEvent = (ViewLayoutChangeEvent) obj;
        return this.view.equals(viewLayoutChangeEvent.view()) && this.left == viewLayoutChangeEvent.left() && this.f8881top == viewLayoutChangeEvent.top() && this.right == viewLayoutChangeEvent.right() && this.bottom == viewLayoutChangeEvent.bottom() && this.oldLeft == viewLayoutChangeEvent.oldLeft() && this.oldTop == viewLayoutChangeEvent.oldTop() && this.oldRight == viewLayoutChangeEvent.oldRight() && this.oldBottom == viewLayoutChangeEvent.oldBottom();
    }

    public int hashCode() {
        return ((((((((((((((((this.view.hashCode() ^ 1000003) * 1000003) ^ this.left) * 1000003) ^ this.f8881top) * 1000003) ^ this.right) * 1000003) ^ this.bottom) * 1000003) ^ this.oldLeft) * 1000003) ^ this.oldTop) * 1000003) ^ this.oldRight) * 1000003) ^ this.oldBottom;
    }

    @Override // com.jakewharton.rxbinding2.view.ViewLayoutChangeEvent
    public int left() {
        return this.left;
    }

    @Override // com.jakewharton.rxbinding2.view.ViewLayoutChangeEvent
    public int oldBottom() {
        return this.oldBottom;
    }

    @Override // com.jakewharton.rxbinding2.view.ViewLayoutChangeEvent
    public int oldLeft() {
        return this.oldLeft;
    }

    @Override // com.jakewharton.rxbinding2.view.ViewLayoutChangeEvent
    public int oldRight() {
        return this.oldRight;
    }

    @Override // com.jakewharton.rxbinding2.view.ViewLayoutChangeEvent
    public int oldTop() {
        return this.oldTop;
    }

    @Override // com.jakewharton.rxbinding2.view.ViewLayoutChangeEvent
    public int right() {
        return this.right;
    }

    public String toString() {
        return "ViewLayoutChangeEvent{view=" + this.view + ", left=" + this.left + ", top=" + this.f8881top + ", right=" + this.right + ", bottom=" + this.bottom + ", oldLeft=" + this.oldLeft + ", oldTop=" + this.oldTop + ", oldRight=" + this.oldRight + ", oldBottom=" + this.oldBottom + "}";
    }

    @Override // com.jakewharton.rxbinding2.view.ViewLayoutChangeEvent
    public int top() {
        return this.f8881top;
    }

    @Override // com.jakewharton.rxbinding2.view.ViewLayoutChangeEvent
    @NonNull
    public View view() {
        return this.view;
    }
}
