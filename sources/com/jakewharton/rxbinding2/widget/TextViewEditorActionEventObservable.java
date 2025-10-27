package com.jakewharton.rxbinding2.widget;

import android.view.KeyEvent;
import android.widget.TextView;
import com.jakewharton.rxbinding2.internal.Preconditions;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;
import io.reactivex.functions.Predicate;

/* loaded from: classes4.dex */
final class TextViewEditorActionEventObservable extends Observable<TextViewEditorActionEvent> {
    private final Predicate<? super TextViewEditorActionEvent> handled;
    private final TextView view;

    public static final class Listener extends MainThreadDisposable implements TextView.OnEditorActionListener {
        private final Predicate<? super TextViewEditorActionEvent> handled;
        private final Observer<? super TextViewEditorActionEvent> observer;
        private final TextView view;

        public Listener(TextView textView, Observer<? super TextViewEditorActionEvent> observer, Predicate<? super TextViewEditorActionEvent> predicate) {
            this.view = textView;
            this.observer = observer;
            this.handled = predicate;
        }

        @Override // io.reactivex.android.MainThreadDisposable
        public void onDispose() {
            this.view.setOnEditorActionListener(null);
        }

        @Override // android.widget.TextView.OnEditorActionListener
        public boolean onEditorAction(TextView textView, int i2, KeyEvent keyEvent) {
            TextViewEditorActionEvent textViewEditorActionEventCreate = TextViewEditorActionEvent.create(this.view, i2, keyEvent);
            try {
                if (isDisposed() || !this.handled.test(textViewEditorActionEventCreate)) {
                    return false;
                }
                this.observer.onNext(textViewEditorActionEventCreate);
                return true;
            } catch (Exception e2) {
                this.observer.onError(e2);
                dispose();
                return false;
            }
        }
    }

    public TextViewEditorActionEventObservable(TextView textView, Predicate<? super TextViewEditorActionEvent> predicate) {
        this.view = textView;
        this.handled = predicate;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super TextViewEditorActionEvent> observer) {
        if (Preconditions.checkMainThread(observer)) {
            Listener listener = new Listener(this.view, observer, this.handled);
            observer.onSubscribe(listener);
            this.view.setOnEditorActionListener(listener);
        }
    }
}
