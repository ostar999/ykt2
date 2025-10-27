package com.jakewharton.rxbinding2.view;

import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;

/* loaded from: classes4.dex */
final class AutoValue_ViewGroupHierarchyChildViewRemoveEvent extends ViewGroupHierarchyChildViewRemoveEvent {
    private final View child;
    private final ViewGroup view;

    public AutoValue_ViewGroupHierarchyChildViewRemoveEvent(ViewGroup viewGroup, View view) {
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
        if (!(obj instanceof ViewGroupHierarchyChildViewRemoveEvent)) {
            return false;
        }
        ViewGroupHierarchyChildViewRemoveEvent viewGroupHierarchyChildViewRemoveEvent = (ViewGroupHierarchyChildViewRemoveEvent) obj;
        return this.view.equals(viewGroupHierarchyChildViewRemoveEvent.view()) && this.child.equals(viewGroupHierarchyChildViewRemoveEvent.child());
    }

    public int hashCode() {
        return ((this.view.hashCode() ^ 1000003) * 1000003) ^ this.child.hashCode();
    }

    public String toString() {
        return "ViewGroupHierarchyChildViewRemoveEvent{view=" + this.view + ", child=" + this.child + "}";
    }

    @Override // com.jakewharton.rxbinding2.view.ViewGroupHierarchyChangeEvent
    @NonNull
    public ViewGroup view() {
        return this.view;
    }
}
