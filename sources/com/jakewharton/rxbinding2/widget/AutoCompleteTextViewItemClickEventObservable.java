package com.jakewharton.rxbinding2.widget;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import com.jakewharton.rxbinding2.internal.Preconditions;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;

/* loaded from: classes4.dex */
final class AutoCompleteTextViewItemClickEventObservable extends Observable<AdapterViewItemClickEvent> {
    private final AutoCompleteTextView view;

    public static final class Listener extends MainThreadDisposable implements AdapterView.OnItemClickListener {
        private final Observer<? super AdapterViewItemClickEvent> observer;
        private final AutoCompleteTextView view;

        public Listener(AutoCompleteTextView autoCompleteTextView, Observer<? super AdapterViewItemClickEvent> observer) {
            this.view = autoCompleteTextView;
            this.observer = observer;
        }

        @Override // io.reactivex.android.MainThreadDisposable
        public void onDispose() {
            this.view.setOnItemClickListener(null);
        }

        @Override // android.widget.AdapterView.OnItemClickListener
        public void onItemClick(AdapterView<?> adapterView, View view, int i2, long j2) {
            if (isDisposed()) {
                return;
            }
            this.observer.onNext(AdapterViewItemClickEvent.create(adapterView, view, i2, j2));
        }
    }

    public AutoCompleteTextViewItemClickEventObservable(AutoCompleteTextView autoCompleteTextView) {
        this.view = autoCompleteTextView;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super AdapterViewItemClickEvent> observer) {
        if (Preconditions.checkMainThread(observer)) {
            Listener listener = new Listener(this.view, observer);
            observer.onSubscribe(listener);
            this.view.setOnItemClickListener(listener);
        }
    }
}
