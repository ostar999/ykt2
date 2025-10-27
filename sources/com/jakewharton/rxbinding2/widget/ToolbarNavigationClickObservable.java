package com.jakewharton.rxbinding2.widget;

import android.view.View;
import android.widget.Toolbar;
import androidx.annotation.RequiresApi;
import com.jakewharton.rxbinding2.internal.Notification;
import com.jakewharton.rxbinding2.internal.Preconditions;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;

@RequiresApi(21)
/* loaded from: classes4.dex */
final class ToolbarNavigationClickObservable extends Observable<Object> {
    private final Toolbar view;

    public static final class Listener extends MainThreadDisposable implements View.OnClickListener {
        private final Observer<? super Object> observer;
        private final Toolbar view;

        public Listener(Toolbar toolbar, Observer<? super Object> observer) {
            this.view = toolbar;
            this.observer = observer;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            if (isDisposed()) {
                return;
            }
            this.observer.onNext(Notification.INSTANCE);
        }

        @Override // io.reactivex.android.MainThreadDisposable
        public void onDispose() {
            this.view.setNavigationOnClickListener(null);
        }
    }

    public ToolbarNavigationClickObservable(Toolbar toolbar) {
        this.view = toolbar;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super Object> observer) {
        if (Preconditions.checkMainThread(observer)) {
            Listener listener = new Listener(this.view, observer);
            observer.onSubscribe(listener);
            this.view.setNavigationOnClickListener(listener);
        }
    }
}
