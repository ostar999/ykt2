package com.jakewharton.rxbinding2.widget;

import android.view.View;
import android.widget.AdapterView;
import com.jakewharton.rxbinding2.internal.Preconditions;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;
import io.reactivex.functions.Predicate;

/* loaded from: classes4.dex */
final class AdapterViewItemLongClickEventObservable extends Observable<AdapterViewItemLongClickEvent> {
    private final Predicate<? super AdapterViewItemLongClickEvent> handled;
    private final AdapterView<?> view;

    public static final class Listener extends MainThreadDisposable implements AdapterView.OnItemLongClickListener {
        private final Predicate<? super AdapterViewItemLongClickEvent> handled;
        private final Observer<? super AdapterViewItemLongClickEvent> observer;
        private final AdapterView<?> view;

        public Listener(AdapterView<?> adapterView, Observer<? super AdapterViewItemLongClickEvent> observer, Predicate<? super AdapterViewItemLongClickEvent> predicate) {
            this.view = adapterView;
            this.observer = observer;
            this.handled = predicate;
        }

        @Override // io.reactivex.android.MainThreadDisposable
        public void onDispose() {
            this.view.setOnItemLongClickListener(null);
        }

        @Override // android.widget.AdapterView.OnItemLongClickListener
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i2, long j2) {
            if (isDisposed()) {
                return false;
            }
            AdapterViewItemLongClickEvent adapterViewItemLongClickEventCreate = AdapterViewItemLongClickEvent.create(adapterView, view, i2, j2);
            try {
                if (!this.handled.test(adapterViewItemLongClickEventCreate)) {
                    return false;
                }
                this.observer.onNext(adapterViewItemLongClickEventCreate);
                return true;
            } catch (Exception e2) {
                this.observer.onError(e2);
                dispose();
                return false;
            }
        }
    }

    public AdapterViewItemLongClickEventObservable(AdapterView<?> adapterView, Predicate<? super AdapterViewItemLongClickEvent> predicate) {
        this.view = adapterView;
        this.handled = predicate;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super AdapterViewItemLongClickEvent> observer) {
        if (Preconditions.checkMainThread(observer)) {
            Listener listener = new Listener(this.view, observer, this.handled);
            observer.onSubscribe(listener);
            this.view.setOnItemLongClickListener(listener);
        }
    }
}
