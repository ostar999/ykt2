package io.reactivex.rxjava3.internal.disposables;

import io.reactivex.rxjava3.disposables.Disposable;
import java.util.concurrent.atomic.AtomicReferenceArray;

/* loaded from: classes8.dex */
public final class ArrayCompositeDisposable extends AtomicReferenceArray<Disposable> implements Disposable {
    private static final long serialVersionUID = 2746389416410565408L;

    public ArrayCompositeDisposable(int capacity) {
        super(capacity);
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public void dispose() {
        Disposable andSet;
        if (get(0) != DisposableHelper.DISPOSED) {
            int length = length();
            for (int i2 = 0; i2 < length; i2++) {
                Disposable disposable = get(i2);
                DisposableHelper disposableHelper = DisposableHelper.DISPOSED;
                if (disposable != disposableHelper && (andSet = getAndSet(i2, disposableHelper)) != disposableHelper && andSet != null) {
                    andSet.dispose();
                }
            }
        }
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public boolean isDisposed() {
        return get(0) == DisposableHelper.DISPOSED;
    }

    public Disposable replaceResource(int index, Disposable resource) {
        Disposable disposable;
        do {
            disposable = get(index);
            if (disposable == DisposableHelper.DISPOSED) {
                resource.dispose();
                return null;
            }
        } while (!compareAndSet(index, disposable, resource));
        return disposable;
    }

    public boolean setResource(int index, Disposable resource) {
        Disposable disposable;
        do {
            disposable = get(index);
            if (disposable == DisposableHelper.DISPOSED) {
                resource.dispose();
                return false;
            }
        } while (!compareAndSet(index, disposable, resource));
        if (disposable == null) {
            return true;
        }
        disposable.dispose();
        return true;
    }
}
