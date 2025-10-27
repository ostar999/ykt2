package com.jakewharton.rxbinding2.view;

import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;

/* loaded from: classes4.dex */
final class AutoValue_ViewGroupHierarchyChildViewAddEvent extends ViewGroupHierarchyChildViewAddEvent {
    private final View child;
    private final ViewGroup view;

    public AutoValue_ViewGroupHierarchyChildViewAddEvent(ViewGroup viewGroup, View view) {
        if (viewGroup == null) {
            throw new NullPointerException("Null view");
        }
        this.view = viewGroup;
        if (view == null) {
            throw new NullPointerException("Null child");
        }
        this.child = view;
    }

    @Override // com.jakewharton.rxbinding2.view.ViewGroupHierarchyChangeEvent
    @NonNull
    public View child() {
        return this.child;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ViewGroupHierarchyChildViewAddEvent)) {
            return false;
        }
        ViewGroupHierarchyChildViewAddEvent viewGroupHierarchyChildViewAddEvent = (ViewGroupHierarchyChildViewAddEvent) obj;
        return this.view.equals(viewGroupHierarchyChildViewAddEvent.view()) && this.child.equals(viewGroupHierarchyChildViewAddEvent.child());
    }

    public int hashCode() {
        return ((this.view.hashCode() ^ 1000003) * 1000003) ^ this.child.hashCode();
    }

    public String toString() {
        return "ViewGroupHierarchyChildViewAddEvent{view=" + this.view + ", child=" + this.child + "}";
    }

    @Override // com.jakewharton.rxbinding2.view.ViewGroupHierarchyChangeEvent
    @NonNull
    public ViewGroup view() {
        return this.view;
    }
}
