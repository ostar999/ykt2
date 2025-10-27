package io.reactivex.rxjava3.internal.disposables;

import androidx.camera.view.j;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.ProtocolViolationException;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes8.dex */
public enum DisposableHelper implements Disposable {
    DISPOSED;

    public static boolean dispose(AtomicReference<Disposable> field) {
        Disposable andSet;
        Disposable disposable = field.get();
        DisposableHelper disposableHelper = DISPOSED;
        if (disposable == disposableHelper || (andSet = field.getAndSet(disposableHelper)) == disposableHelper) {
            return false;
        }
        if (andSet == null) {
            return true;
        }
        andSet.dispose();
        return true;
    }

    public static boolean isDisposed(Disposable d3) {
        return d3 == DISPOSED;
    }

    public static boolean replace(AtomicReference<Disposable> field, Disposable d3) {
        Disposable disposable;
        do {
            disposable = field.get();
            if (disposable == DISPOSED) {
                if (d3 == null) {
                    return false;
                }
                d3.dispose();
                return false;
            }
        } while (!j.a(field, disposable, d3));
        return true;
    }

    public static void reportDisposableSet() {
        RxJavaPlugins.onError(new ProtocolViolationException("Disposable already set!"));
    }

    public static boolean set(AtomicReference<Disposable> field, Disposable d3) {
        Disposable disposable;
        do {
            disposable = field.get();
            if (disposable == DISPOSED) {
                if (d3 == null) {
                    return false;
                }
                d3.dispose();
                return false;
            }
        } while (!j.a(field, disposable, d3));
        if (disposable == null) {
            return true;
        }
        disposable.dispose();
        return true;
    }

    public static boolean setOnce(AtomicReference<Disposable> field, Disposable d3) {
        Objects.requireNonNull(d3, "d is null");
        if (j.a(field, null, d3)) {
            return true;
        }
        d3.dispose();
        if (field.get() == DISPOSED) {
            return false;
        }
        reportDisposableSet();
        return false;
    }

    public static boolean trySet(AtomicReference<Disposable> field, Disposable d3) {
        if (j.a(field, null, d3)) {
            return true;
        }
        if (field.get() != DISPOSED) {
            return false;
        }
        d3.dispose();
        return false;
    }

    public static boolean validate(Disposable current, Disposable next) {
        if (next == null) {
            RxJavaPlugins.onError(new NullPointerException("next is null"));
            return false;
        }
        if (current == null) {
            return true;
        }
        next.dispose();
        reportDisposableSet();
        return false;
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public void dispose() {
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public boolean isDisposed() {
        return true;
    }
}
