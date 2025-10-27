package com.jakewharton.rxbinding2.widget;

import android.widget.AbsListView;
import com.jakewharton.rxbinding2.internal.Preconditions;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;

/* loaded from: classes4.dex */
final class AbsListViewScrollEventObservable extends Observable<AbsListViewScrollEvent> {
    private final AbsListView view;

    public static final class Listener extends MainThreadDisposable implements AbsListView.OnScrollListener {
        private int currentScrollState = 0;
        private final Observer<? super AbsListViewScrollEvent> observer;
        private final AbsListView view;

        public Listener(AbsListView absListView, Observer<? super AbsListViewScrollEvent> observer) {
            this.view = absListView;
            this.observer = observer;
        }

        @Override // io.reactivex.android.MainThreadDisposable
        public void onDispose() {
            this.view.setOnScrollListener(null);
        }

        @Override // android.widget.AbsListView.OnScrollListener
        public void onScroll(AbsListView absListView, int i2, int i3, int i4) {
            if (isDisposed()) {
                return;
            }
            this.observer.onNext(AbsListViewScrollEvent.create(this.view, this.currentScrollState, i2, i3, i4));
        }

        @Override // android.widget.AbsListView.OnScrollListener
        public void onScrollStateChanged(AbsListView absListView, int i2) {
            this.currentScrollState = i2;
            if (isDisposed()) {
                return;
            }
            AbsListView absListView2 = this.view;
            this.observer.onNext(AbsListViewScrollEvent.create(absListView2, i2, absListView2.getFirstVisiblePosition(), this.view.getChildCount(), this.view.getCount()));
        }
    }

    public AbsListViewScrollEventObservable(AbsListView absListView) {
        this.view = absListView;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super AbsListViewScrollEvent> observer) {
        if (Preconditions.checkMainThread(observer)) {
            Listener listener = new Listener(this.view, observer);
            observer.onSubscribe(listener);
            this.view.setOnScrollListener(listener);
        }
    }
}
