package com.jakewharton.rxbinding2.widget;

import android.widget.SearchView;
import androidx.annotation.NonNull;
import com.yikaobang.yixue.R2;

/* loaded from: classes4.dex */
final class AutoValue_SearchViewQueryTextEvent extends SearchViewQueryTextEvent {
    private final boolean isSubmitted;
    private final CharSequence queryText;
    private final SearchView view;

    public AutoValue_SearchViewQueryTextEvent(SearchView searchView, CharSequence charSequence, boolean z2) {
        if (searchView == null) {
            throw new NullPointerException("Null view");
        }
        this.view = searchView;
        if (charSequence == null) {
            throw new NullPointerException("Null queryText");
        }
        this.queryText = charSequence;
        this.isSubmitted = z2;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof SearchViewQueryTextEvent)) {
            return false;
        }
        SearchViewQueryTextEvent searchViewQueryTextEvent = (SearchViewQueryTextEvent) obj;
        return this.view.equals(searchViewQueryTextEvent.view()) && this.queryText.equals(searchViewQueryTextEvent.queryText()) && this.isSubmitted == searchViewQueryTextEvent.isSubmitted();
    }

    public int hashCode() {
        return ((((this.view.hashCode() ^ 1000003) * 1000003) ^ this.queryText.hashCode()) * 1000003) ^ (this.isSubmitted ? R2.attr.customColorDrawableValue : R2.attr.customIsDrag);
    }

    @Override // com.jakewharton.rxbinding2.widget.SearchViewQueryTextEvent
    public boolean isSubmitted() {
        return this.isSubmitted;
    }

    @Override // com.jakewharton.rxbinding2.widget.SearchViewQueryTextEvent
    @NonNull
    public CharSequence queryText() {
        return this.queryText;
    }

    public String toString() {
        return "SearchViewQueryTextEvent{view=" + this.view + ", queryText=" + ((Object) this.queryText) + ", isSubmitted=" + this.isSubmitted + "}";
    }

    @Override // com.jakewharton.rxbinding2.widget.SearchViewQueryTextEvent
    @NonNull
    public SearchView view() {
        return this.view;
    }
}
