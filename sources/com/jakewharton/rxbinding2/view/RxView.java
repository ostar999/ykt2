package com.jakewharton.rxbinding2.view;

import android.view.DragEvent;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import com.jakewharton.rxbinding2.InitialValueObservable;
import com.jakewharton.rxbinding2.internal.Functions;
import com.jakewharton.rxbinding2.internal.Preconditions;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import java.util.concurrent.Callable;

/* loaded from: classes4.dex */
public final class RxView {
    private RxView() {
        throw new AssertionError("No instances.");
    }

    @NonNull
    @CheckResult
    @Deprecated
    public static Consumer<? super Boolean> activated(@NonNull final View view) {
        Preconditions.checkNotNull(view, "view == null");
        return new Consumer<Boolean>() { // from class: com.jakewharton.rxbinding2.view.RxView.1
            @Override // io.reactivex.functions.Consumer
            public void accept(Boolean bool) {
                view.setActivated(bool.booleanValue());
            }
        };
    }

    @NonNull
    @CheckResult
    public static Observable<ViewAttachEvent> attachEvents(@NonNull View view) {
        Preconditions.checkNotNull(view, "view == null");
        return new ViewAttachEventObservable(view);
    }

    @NonNull
    @CheckResult
    public static Observable<Object> attaches(@NonNull View view) {
        Preconditions.checkNotNull(view, "view == null");
        return new ViewAttachesObservable(view, true);
    }

    @NonNull
    @CheckResult
    @Deprecated
    public static Consumer<? super Boolean> clickable(@NonNull final View view) {
        Preconditions.checkNotNull(view, "view == null");
        return new Consumer<Boolean>() { // from class: com.jakewharton.rxbinding2.view.RxView.2
            @Override // io.reactivex.functions.Consumer
            public void accept(Boolean bool) {
                view.setClickable(bool.booleanValue());
            }
        };
    }

    @NonNull
    @CheckResult
    public static Observable<Object> clicks(@NonNull View view) {
        Preconditions.checkNotNull(view, "view == null");
        return new ViewClickObservable(view);
    }

    @NonNull
    @CheckResult
    public static Observable<Object> detaches(@NonNull View view) {
        Preconditions.checkNotNull(view, "view == null");
        return new ViewAttachesObservable(view, false);
    }

    @NonNull
    @CheckResult
    public static Observable<DragEvent> drags(@NonNull View view) {
        Preconditions.checkNotNull(view, "view == null");
        return new ViewDragObservable(view, Functions.PREDICATE_ALWAYS_TRUE);
    }

    @NonNull
    @CheckResult
    @RequiresApi(16)
    public static Observable<Object> draws(@NonNull View view) {
        Preconditions.checkNotNull(view, "view == null");
        return new ViewTreeObserverDrawObservable(view);
    }

    @NonNull
    @CheckResult
    @Deprecated
    public static Consumer<? super Boolean> enabled(@NonNull final View view) {
        Preconditions.checkNotNull(view, "view == null");
        return new Consumer<Boolean>() { // from class: com.jakewharton.rxbinding2.view.RxView.3
            @Override // io.reactivex.functions.Consumer
            public void accept(Boolean bool) {
                view.setEnabled(bool.booleanValue());
            }
        };
    }

    @NonNull
    @CheckResult
    public static InitialValueObservable<Boolean> focusChanges(@NonNull View view) {
        Preconditions.checkNotNull(view, "view == null");
        return new ViewFocusChangeObservable(view);
    }

    @NonNull
    @CheckResult
    public static Observable<Object> globalLayouts(@NonNull View view) {
        Preconditions.checkNotNull(view, "view == null");
        return new ViewTreeObserverGlobalLayoutObservable(view);
    }

    @NonNull
    @CheckResult
    public static Observable<MotionEvent> hovers(@NonNull View view) {
        Preconditions.checkNotNull(view, "view == null");
        return new ViewHoverObservable(view, Functions.PREDICATE_ALWAYS_TRUE);
    }

    @NonNull
    @CheckResult
    public static Observable<KeyEvent> keys(@NonNull View view) {
        Preconditions.checkNotNull(view, "view == null");
        return new ViewKeyObservable(view, Functions.PREDICATE_ALWAYS_TRUE);
    }

    @NonNull
    @CheckResult
    public static Observable<ViewLayoutChangeEvent> layoutChangeEvents(@NonNull View view) {
        Preconditions.checkNotNull(view, "view == null");
        return new ViewLayoutChangeEventObservable(view);
    }

    @NonNull
    @CheckResult
    public static Observable<Object> layoutChanges(@NonNull View view) {
        Preconditions.checkNotNull(view, "view == null");
        return new ViewLayoutChangeObservable(view);
    }

    @NonNull
    @CheckResult
    public static Observable<Object> longClicks(@NonNull View view) {
        Preconditions.checkNotNull(view, "view == null");
        return new ViewLongClickObservable(view, Functions.CALLABLE_ALWAYS_TRUE);
    }

    @NonNull
    @CheckResult
    public static Observable<Object> preDraws(@NonNull View view, @NonNull Callable<Boolean> callable) {
        Preconditions.checkNotNull(view, "view == null");
        Preconditions.checkNotNull(callable, "proceedDrawingPass == null");
        return new ViewTreeObserverPreDrawObservable(view, callable);
    }

    @NonNull
    @CheckResult
    @Deprecated
    public static Consumer<? super Boolean> pressed(@NonNull final View view) {
        Preconditions.checkNotNull(view, "view == null");
        return new Consumer<Boolean>() { // from class: com.jakewharton.rxbinding2.view.RxView.4
            @Override // io.reactivex.functions.Consumer
            public void accept(Boolean bool) {
                view.setPressed(bool.booleanValue());
            }
        };
    }

    @NonNull
    @CheckResult
    @RequiresApi(23)
    public static Observable<ViewScrollChangeEvent> scrollChangeEvents(@NonNull View view) {
        Preconditions.checkNotNull(view, "view == null");
        return new ViewScrollChangeEventObservable(view);
    }

    @NonNull
    @CheckResult
    @Deprecated
    public static Consumer<? super Boolean> selected(@NonNull final View view) {
        Preconditions.checkNotNull(view, "view == null");
        return new Consumer<Boolean>() { // from class: com.jakewharton.rxbinding2.view.RxView.5
            @Override // io.reactivex.functions.Consumer
            public void accept(Boolean bool) {
                view.setSelected(bool.booleanValue());
            }
        };
    }

    @NonNull
    @CheckResult
    public static Observable<Integer> systemUiVisibilityChanges(@NonNull View view) {
        Preconditions.checkNotNull(view, "view == null");
        return new ViewSystemUiVisibilityChangeObservable(view);
    }

    @NonNull
    @CheckResult
    public static Observable<MotionEvent> touches(@NonNull View view) {
        Preconditions.checkNotNull(view, "view == null");
        return new ViewTouchObservable(view, Functions.PREDICATE_ALWAYS_TRUE);
    }

    @NonNull
    @CheckResult
    public static Consumer<? super Boolean> visibility(@NonNull View view) {
        Preconditions.checkNotNull(view, "view == null");
        return visibility(view, 8);
    }

    @NonNull
    @CheckResult
    public static Observable<DragEvent> drags(@NonNull View view, @NonNull Predicate<? super DragEvent> predicate) {
        Preconditions.checkNotNull(view, "view == null");
        Preconditions.checkNotNull(predicate, "handled == null");
        return new ViewDragObservable(view, predicate);
    }

    @NonNull
    @CheckResult
    public static Observable<MotionEvent> hovers(@NonNull View view, @NonNull Predicate<? super MotionEvent> predicate) {
        Preconditions.checkNotNull(view, "view == null");
        Preconditions.checkNotNull(predicate, "handled == null");
        return new ViewHoverObservable(view, predicate);
    }

    @NonNull
    @CheckResult
    public static Observable<KeyEvent> keys(@NonNull View view, @NonNull Predicate<? super KeyEvent> predicate) {
        Preconditions.checkNotNull(view, "view == null");
        Preconditions.checkNotNull(predicate, "handled == null");
        return new ViewKeyObservable(view, predicate);
    }

    @NonNull
    @CheckResult
    public static Observable<Object> longClicks(@NonNull View view, @NonNull Callable<Boolean> callable) {
        Preconditions.checkNotNull(view, "view == null");
        Preconditions.checkNotNull(callable, "handled == null");
        return new ViewLongClickObservable(view, callable);
    }

    @NonNull
    @CheckResult
    public static Observable<MotionEvent> touches(@NonNull View view, @NonNull Predicate<? super MotionEvent> predicate) {
        Preconditions.checkNotNull(view, "view == null");
        Preconditions.checkNotNull(predicate, "handled == null");
        return new ViewTouchObservable(view, predicate);
    }

    @NonNull
    @CheckResult
    public static Consumer<? super Boolean> visibility(@NonNull final View view, final int i2) {
        Preconditions.checkNotNull(view, "view == null");
        if (i2 == 0) {
            throw new IllegalArgumentException("Setting visibility to VISIBLE when false would have no effect.");
        }
        if (i2 != 4 && i2 != 8) {
            throw new IllegalArgumentException("Must set visibility to INVISIBLE or GONE when false.");
        }
        return new Consumer<Boolean>() { // from class: com.jakewharton.rxbinding2.view.RxView.6
            @Override // io.reactivex.functions.Consumer
            public void accept(Boolean bool) {
                view.setVisibility(bool.booleanValue() ? 0 : i2);
            }
        };
    }
}
