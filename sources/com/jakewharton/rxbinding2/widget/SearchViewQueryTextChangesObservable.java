package com.jakewharton.rxbinding2.widget;

import android.widget.SearchView;
import com.jakewharton.rxbinding2.InitialValueObservable;
import com.jakewharton.rxbinding2.internal.Preconditions;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;

/* loaded from: classes4.dex */
final class SearchViewQueryTextChangesObservable extends InitialValueObservable<CharSequence> {
    private final SearchView view;

    public static final class Listener extends MainThreadDisposable implements SearchView.OnQueryTextListener {
        private final Observer<? super CharSequence> observer;
        private final SearchView view;

        public Listener(SearchView searchView, Observer<? super CharSequence> observer) {
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
            this.observer.onNext(str);
            return true;
        }

        @Override // android.widget.SearchView.OnQueryTextListener
        public boolean onQueryTextSubmit(String str) {
            return false;
        }
    }

    public SearchViewQueryTextChangesObservable(SearchView searchView) {
        this.view = searchView;
    }

    @Override // com.jakewharton.rxbinding2.InitialValueObservable
    public void subscribeListener(Observer<? super CharSequence> observer) {
        if (Preconditions.checkMainThread(observer)) {
            Listener listener = new Listener(this.view, observer);
            this.view.setOnQueryTextListener(listener);
            observer.onSubscribe(listener);
        }
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.jakewharton.rxbinding2.InitialValueObservable
    public CharSequence getInitialValue() {
        return this.view.getQuery();
    }
}
