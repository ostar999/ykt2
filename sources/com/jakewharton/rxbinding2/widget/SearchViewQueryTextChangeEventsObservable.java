package com.jakewharton.rxbinding2.widget;

import android.widget.SearchView;
import com.jakewharton.rxbinding2.InitialValueObservable;
import com.jakewharton.rxbinding2.internal.Preconditions;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;

/* loaded from: classes4.dex */
final class SearchViewQueryTextChangeEventsObservable extends InitialValueObservable<SearchViewQueryTextEvent> {
    private final SearchView view;

    public static final class Listener extends MainThreadDisposable implements SearchView.OnQueryTextListener {
        private final Observer<? super SearchViewQueryTextEvent> observer;
        private final SearchView view;

        public Listener(SearchView searchView, Observer<? super SearchViewQueryTextEvent> observer) {
            this.view = searchView;
            this.observer = observer;
        }

        @Override // io.reactivex.android.MainThreadDisposable
        public void onDispose() {
            this.view.setOnQueryTextListener(null);
        }

        @Override // android.widget.SearchView.OnQueryTextListener
        public boolean onQueryTextChange(String str) {
            if (isDisposed()) {
                return false;
            }
            this.observer.onNext(SearchViewQueryTextEvent.create(this.view, str, false));
            return true;
        }

        @Override // android.widget.SearchView.OnQueryTextListener
        public boolean onQueryTextSubmit(String str) {
            if (isDisposed()) {
                return false;
            }
            this.observer.onNext(SearchViewQueryTextEvent.create(this.view, str, true));
            return true;
        }
    }

    public SearchViewQueryTextChangeEventsObservable(SearchView searchView) {
        this.view = searchView;
    }

    @Override // com.jakewharton.rxbinding2.InitialValueObservable
    public void subscribeListener(Observer<? super SearchViewQueryTextEvent> observer) {
        if (Preconditions.checkMainThread(observer)) {
            Listener listener = new Listener(this.view, observer);
            this.view.setOnQueryTextListener(listener);
            observer.onSubscribe(listener);
        }
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.jakewharton.rxbinding2.InitialValueObservable
    public SearchViewQueryTextEvent getInitialValue() {
        SearchView searchView = this.view;
        return SearchViewQueryTextEvent.create(searchView, searchView.getQuery(), false);
    }
}
