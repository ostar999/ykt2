package io.reactivex.rxjava3.internal.jdk8;

import java.util.NoSuchElementException;

/* loaded from: classes8.dex */
public final class ObservableSingleStageObserver<T> extends ObservableStageObserver<T> {
    final T defaultItem;
    final boolean hasDefault;

    public ObservableSingleStageObserver(boolean hasDefault, T defaultItem) {
        this.hasDefault = hasDefault;
        this.defaultItem = defaultItem;
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public void onComplete() {
        if (isDone()) {
            return;
        }
        T t2 = this.value;
        clear();
        if (t2 != null) {
            complete(t2);
        } else if (this.hasDefault) {
            complete(this.defaultItem);
        } else {
            completeExceptionally(new NoSuchElementException());
        }
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public void onNext(T t2) {
        if (this.value == null) {
            this.value = t2;
        } else {
            this.value = null;
            completeExceptionally(new IllegalArgumentException("Sequence contains more than one element!"));
        }
    }
}
