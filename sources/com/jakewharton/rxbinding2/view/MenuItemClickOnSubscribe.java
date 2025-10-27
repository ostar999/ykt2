package com.jakewharton.rxbinding2.view;

import android.view.MenuItem;
import com.jakewharton.rxbinding2.internal.Notification;
import com.jakewharton.rxbinding2.internal.Preconditions;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;
import io.reactivex.functions.Predicate;

/* loaded from: classes4.dex */
final class MenuItemClickOnSubscribe extends Observable<Object> {
    private final Predicate<? super MenuItem> handled;
    private final MenuItem menuItem;

    public static final class Listener extends MainThreadDisposable implements MenuItem.OnMenuItemClickListener {
        private final Predicate<? super MenuItem> handled;
        private final MenuItem menuItem;
        private final Observer<? super Object> observer;

        public Listener(MenuItem menuItem, Predicate<? super MenuItem> predicate, Observer<? super Object> observer) {
            this.menuItem = menuItem;
            this.handled = predicate;
            this.observer = observer;
        }

        @Override // io.reactivex.android.MainThreadDisposable
        public void onDispose() {
            this.menuItem.setOnMenuItemClickListener(null);
        }

        @Override // android.view.MenuItem.OnMenuItemClickListener
        public boolean onMenuItemClick(MenuItem menuItem) {
            if (isDisposed()) {
                return false;
            }
            try {
                if (!this.handled.test(this.menuItem)) {
                    return false;
                }
                this.observer.onNext(Notification.INSTANCE);
                return true;
            } catch (Exception e2) {
                this.observer.onError(e2);
                dispose();
                return false;
            }
        }
    }

    public MenuItemClickOnSubscribe(MenuItem menuItem, Predicate<? super MenuItem> predicate) {
        this.menuItem = menuItem;
        this.handled = predicate;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super Object> observer) {
        if (Preconditions.checkMainThread(observer)) {
            Listener listener = new Listener(this.menuItem, this.handled, observer);
            observer.onSubscribe(listener);
            this.menuItem.setOnMenuItemClickListener(listener);
        }
    }
}
