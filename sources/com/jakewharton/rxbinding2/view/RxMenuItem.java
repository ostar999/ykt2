package com.jakewharton.rxbinding2.view;

import android.graphics.drawable.Drawable;
import android.view.MenuItem;
import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;
import com.jakewharton.rxbinding2.internal.Functions;
import com.jakewharton.rxbinding2.internal.Preconditions;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

/* loaded from: classes4.dex */
public final class RxMenuItem {
    private RxMenuItem() {
        throw new AssertionError("No instances.");
    }

    @NonNull
    @CheckResult
    public static Observable<MenuItemActionViewEvent> actionViewEvents(@NonNull MenuItem menuItem) {
        Preconditions.checkNotNull(menuItem, "menuItem == null");
        return new MenuItemActionViewEventObservable(menuItem, Functions.PREDICATE_ALWAYS_TRUE);
    }

    @NonNull
    @CheckResult
    @Deprecated
    public static Consumer<? super Boolean> checked(@NonNull final MenuItem menuItem) {
        Preconditions.checkNotNull(menuItem, "menuItem == null");
        return new Consumer<Boolean>() { // from class: com.jakewharton.rxbinding2.view.RxMenuItem.1
            @Override // io.reactivex.functions.Consumer
            public void accept(Boolean bool) {
                menuItem.setChecked(bool.booleanValue());
            }
        };
    }

    @NonNull
    @CheckResult
    public static Observable<Object> clicks(@NonNull MenuItem menuItem) {
        Preconditions.checkNotNull(menuItem, "menuItem == null");
        return new MenuItemClickOnSubscribe(menuItem, Functions.PREDICATE_ALWAYS_TRUE);
    }

    @NonNull
    @CheckResult
    @Deprecated
    public static Consumer<? super Boolean> enabled(@NonNull final MenuItem menuItem) {
        Preconditions.checkNotNull(menuItem, "menuItem == null");
        return new Consumer<Boolean>() { // from class: com.jakewharton.rxbinding2.view.RxMenuItem.2
            @Override // io.reactivex.functions.Consumer
            public void accept(Boolean bool) {
                menuItem.setEnabled(bool.booleanValue());
            }
        };
    }

    @NonNull
    @CheckResult
    @Deprecated
    public static Consumer<? super Drawable> icon(@NonNull final MenuItem menuItem) {
        Preconditions.checkNotNull(menuItem, "menuItem == null");
        return new Consumer<Drawable>() { // from class: com.jakewharton.rxbinding2.view.RxMenuItem.3
            @Override // io.reactivex.functions.Consumer
            public void accept(Drawable drawable) {
                menuItem.setIcon(drawable);
            }
        };
    }

    @NonNull
    @CheckResult
    @Deprecated
    public static Consumer<? super Integer> iconRes(@NonNull final MenuItem menuItem) {
        Preconditions.checkNotNull(menuItem, "menuItem == null");
        return new Consumer<Integer>() { // from class: com.jakewharton.rxbinding2.view.RxMenuItem.4
            @Override // io.reactivex.functions.Consumer
            public void accept(Integer num) {
                menuItem.setIcon(num.intValue());
            }
        };
    }

    @NonNull
    @CheckResult
    @Deprecated
    public static Consumer<? super CharSequence> title(@NonNull final MenuItem menuItem) {
        Preconditions.checkNotNull(menuItem, "menuItem == null");
        return new Consumer<CharSequence>() { // from class: com.jakewharton.rxbinding2.view.RxMenuItem.5
            @Override // io.reactivex.functions.Consumer
            public void accept(CharSequence charSequence) {
                menuItem.setTitle(charSequence);
            }
        };
    }

    @NonNull
    @CheckResult
    @Deprecated
    public static Consumer<? super Integer> titleRes(@NonNull final MenuItem menuItem) {
        Preconditions.checkNotNull(menuItem, "menuItem == null");
        return new Consumer<Integer>() { // from class: com.jakewharton.rxbinding2.view.RxMenuItem.6
            @Override // io.reactivex.functions.Consumer
            public void accept(Integer num) {
                menuItem.setTitle(num.intValue());
            }
        };
    }

    @NonNull
    @CheckResult
    @Deprecated
    public static Consumer<? super Boolean> visible(@NonNull final MenuItem menuItem) {
        Preconditions.checkNotNull(menuItem, "menuItem == null");
        return new Consumer<Boolean>() { // from class: com.jakewharton.rxbinding2.view.RxMenuItem.7
            @Override // io.reactivex.functions.Consumer
            public void accept(Boolean bool) {
                menuItem.setVisible(bool.booleanValue());
            }
        };
    }

    @NonNull
    @CheckResult
    public static Observable<MenuItemActionViewEvent> actionViewEvents(@NonNull MenuItem menuItem, @NonNull Predicate<? super MenuItemActionViewEvent> predicate) {
        Preconditions.checkNotNull(menuItem, "menuItem == null");
        Preconditions.checkNotNull(predicate, "handled == null");
        return new MenuItemActionViewEventObservable(menuItem, predicate);
    }

    @NonNull
    @CheckResult
    public static Observable<Object> clicks(@NonNull MenuItem menuItem, @NonNull Predicate<? super MenuItem> predicate) {
        Preconditions.checkNotNull(menuItem, "menuItem == null");
        Preconditions.checkNotNull(predicate, "handled == null");
        return new MenuItemClickOnSubscribe(menuItem, predicate);
    }
}
