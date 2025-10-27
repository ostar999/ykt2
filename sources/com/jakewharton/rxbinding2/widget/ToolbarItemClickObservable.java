package com.jakewharton.rxbinding2.widget;

import android.view.MenuItem;
import android.widget.Toolbar;
import androidx.annotation.RequiresApi;
import com.jakewharton.rxbinding2.internal.Preconditions;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;

@RequiresApi(21)
/* loaded from: classes4.dex */
final class ToolbarItemClickObservable extends Observable<MenuItem> {
    private final Toolbar view;

    public static final class Listener extends MainThreadDisposable implements Toolbar.OnMenuItemClickListener {
        private final Observer<? super MenuItem> observer;
        private final Toolbar view;

        public Listener(Toolbar toolbar, Observer<? super MenuItem> observer) {
            this.view = toolbar;
            this.observer = observer;
        }

        @Override // io.reactivex.android.MainThreadDisposable
        public void onDispose() {
            this.view.setOnMenuItemClickListener(null);
        }

        @Override // android.widget.Toolbar.OnMenuItemClickListener
        public boolean onMenuItemClick(MenuItem menuItem) {
            if (isDisposed()) {
                return false;
            }
            this.observer.onNext(menuItem);
            return true;
        }
    }

    public ToolbarItemClickObservable(Toolbar toolbar) {
        this.view = toolbar;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super MenuItem> observer) {
        if (Preconditions.checkMainThread(observer)) {
            Listener listener = new Listener(this.view, observer);
            observer.onSubscribe(listener);
            this.view.setOnMenuItemClickListener(listener);
        }
    }
}
