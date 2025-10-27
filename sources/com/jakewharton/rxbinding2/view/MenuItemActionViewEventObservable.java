package com.jakewharton.rxbinding2.view;

import android.view.MenuItem;
import com.jakewharton.rxbinding2.internal.Preconditions;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;
import io.reactivex.functions.Predicate;

/* loaded from: classes4.dex */
final class MenuItemActionViewEventObservable extends Observable<MenuItemActionViewEvent> {
    private final Predicate<? super MenuItemActionViewEvent> handled;
    private final MenuItem menuItem;

    public static final class Listener extends MainThreadDisposable implements MenuItem.OnActionExpandListener {
        private final Predicate<? super MenuItemActionViewEvent> handled;
        private final MenuItem menuItem;
        private final Observer<? super MenuItemActionViewEvent> observer;

        public Listener(MenuItem menuItem, Predicate<? super MenuItemActionViewEvent> predicate, Observer<? super MenuItemActionViewEvent> observer) {
            this.menuItem = menuItem;
            this.handled = predicate;
            this.observer = observer;
        }

        private boolean onEvent(MenuItemActionViewEvent menuItemActionViewEvent) {
            if (isDisposed()) {
                return false;
            }
            try {
                if (!this.handled.test(menuItemActionViewEvent)) {
                    return false;
                }
                this.observer.onNext(menuItemActionViewEvent);
                return true;
            } catch (Exception e2) {
                this.observer.onError(e2);
                dispose();
                return false;
            }
        }

        @Override // io.reactivex.android.MainThreadDisposable
        public void onDispose() {
            this.menuItem.setOnActionExpandListener(null);
        }

        @Override // android.view.MenuItem.OnActionExpandListener
        public boolean onMenuItemActionCollapse(MenuItem menuItem) {
            return onEvent(MenuItemActionViewCollapseEvent.create(menuItem));
        }

        @Override // android.view.MenuItem.OnActionExpandListener
        public boolean onMenuItemActionExpand(MenuItem menuItem) {
            return onEvent(MenuItemActionViewExpandEvent.create(menuItem));
        }
    }

    public MenuItemActionViewEventObservable(MenuItem menuItem, Predicate<? super MenuItemActionViewEvent> predicate) {
        this.menuItem = menuItem;
        this.handled = predicate;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super MenuItemActionViewEvent> observer) {
        if (Preconditions.checkMainThread(observer)) {
            Listener listener = new Listener(this.menuItem, this.handled, observer);
            observer.onSubscribe(listener);
            this.menuItem.setOnActionExpandListener(listener);
        }
    }
}
