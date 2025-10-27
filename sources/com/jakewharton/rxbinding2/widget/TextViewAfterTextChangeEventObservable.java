package com.jakewharton.rxbinding2.widget;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;
import com.jakewharton.rxbinding2.InitialValueObservable;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;

/* loaded from: classes4.dex */
final class TextViewAfterTextChangeEventObservable extends InitialValueObservable<TextViewAfterTextChangeEvent> {
    private final TextView view;

    public static final class Listener extends MainThreadDisposable implements TextWatcher {
        private final Observer<? super TextViewAfterTextChangeEvent> observer;
        private final TextView view;

        public Listener(TextView textView, Observer<? super TextViewAfterTextChangeEvent> observer) {
            this.view = textView;
            this.observer = observer;
        }

        @Override // android.text.TextWatcher
        public void afterTextChanged(Editable editable) {
            this.observer.onNext(TextViewAfterTextChangeEvent.create(this.view, editable));
        }

        @Override // android.text.TextWatcher
        public void beforeTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
        }

        @Override // io.reactivex.android.MainThreadDisposable
        public void onDispose() {
            this.view.removeTextChangedListener(this);
        }

        @Override // android.text.TextWatcher
        public void onTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
        }
    }

    public TextViewAfterTextChangeEventObservable(TextView textView) {
        this.view = textView;
    }

    @Override // com.jakewharton.rxbinding2.InitialValueObservable
    public void subscribeListener(Observer<? super TextViewAfterTextChangeEvent> observer) {
        Listener listener = new Listener(this.view, observer);
        observer.onSubscribe(listener);
        this.view.addTextChangedListener(listener);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.jakewharton.rxbinding2.InitialValueObservable
    public TextViewAfterTextChangeEvent getInitialValue() {
        TextView textView = this.view;
        return TextViewAfterTextChangeEvent.create(textView, textView.getEditableText());
    }
}
