package com.jakewharton.rxbinding2.view;

import android.view.MenuItem;
import androidx.annotation.NonNull;

/* loaded from: classes4.dex */
final class AutoValue_MenuItemActionViewCollapseEvent extends MenuItemActionViewCollapseEvent {
    private final MenuItem menuItem;

    public AutoValue_MenuItemActionViewCollapseEvent(MenuItem menuItem) {
        if (menuItem == null) {
            throw new NullPointerException("Null menuItem");
        }
        this.menuItem = menuItem;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof MenuItemActionViewCollapseEvent) {
            return this.menuItem.equals(((MenuItemActionViewCollapseEvent) obj).menuItem());
        }
        return false;
    }

    public int hashCode() {
        return this.menuItem.hashCode() ^ 1000003;
    }

    @Override // com.jakewharton.rxbinding2.view.MenuItemActionViewEvent
    @NonNull
    public MenuItem menuItem() {
        return this.menuItem;
    }

    public String toString() {
        return "MenuItemActionViewCollapseEvent{menuItem=" + this.menuItem + "}";
    }
}
