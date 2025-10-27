package com.jakewharton.rxbinding2.widget;

import android.widget.SearchView;
import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;
import com.jakewharton.rxbinding2.InitialValueObservable;
import com.jakewharton.rxbinding2.internal.Preconditions;
import io.reactivex.functions.Consumer;

/* loaded from: classes4.dex */
public final class RxSearchView {
    private RxSearchView() {
        throw new AssertionError("No instances.");
    }

    @NonNull
    @CheckResult
    public static Consumer<? super CharSequence> query(@NonNull final SearchView searchView, final boolean z2) {
        Preconditions.checkNotNull(searchView, "view == null");
        return new Consumer<CharSequence>() { // from class: com.jakewharton.rxbinding2.widget.RxSearchView.1
            @Override // io.reactivex.functions.Consumer
            public void accept(CharSequence charSequence) {
                searchView.setQuery(charSequence, z2);
            }
        };
    }

    @NonNull
    @CheckResult
    public static InitialValueObservable<SearchViewQueryTextEvent> queryTextChangeEvents(@NonNull SearchView searchView) {
        Preconditions.checkNotNull(searchView, "view == null");
        return new SearchViewQueryTextChangeEventsObservable(searchView);
    }

    @NonNull
    @CheckResult
    public static InitialValueObservable<CharSequence> queryTextChanges(@NonNull SearchView searchView) {
        Preconditions.checkNotNull(searchView, "view == null");
        return new SearchViewQueryTextChangesObservable(searchView);
    }
}
