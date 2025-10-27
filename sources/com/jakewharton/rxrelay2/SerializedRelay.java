package com.jakewharton.rxrelay2;

import io.reactivex.Observer;

/* loaded from: classes4.dex */
final class SerializedRelay<T> extends Relay<T> {
    private final Relay<T> actual;
    private boolean emitting;
    private AppendOnlyLinkedArrayList<T> queue;

    public SerializedRelay(Relay<T> relay) {
        this.actual = relay;
    }

    private void emitLoop() {
        AppendOnlyLinkedArrayList<T> appendOnlyLinkedArrayList;
        while (true) {
            synchronized (this) {
                appendOnlyLinkedArrayList = this.queue;
                if (appendOnlyLinkedArrayList == null) {
                    this.emitting = false;
                    return;
                }
                this.queue = null;
            }
            appendOnlyLinkedArrayList.accept(this.actual);
        }
    }

    @Override // com.jakewharton.rxrelay2.Relay, io.reactivex.functions.Consumer
    public void accept(T t2) {
        synchronized (this) {
            if (!this.emitting) {
                this.emitting = true;
                this.actual.accept(t2);
                emitLoop();
            } else {
                AppendOnlyLinkedArrayList<T> appendOnlyLinkedArrayList = this.queue;
                if (appendOnlyLinkedArrayList == null) {
                    appendOnlyLinkedArrayList = new AppendOnlyLinkedArrayList<>(4);
                    this.queue = appendOnlyLinkedArrayList;
                }
                appendOnlyLinkedArrayList.add(t2);
            }
        }
    }

    @Override // com.jakewharton.rxrelay2.Relay
    public boolean hasObservers() {
        return this.actual.hasObservers();
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super T> observer) {
        this.actual.subscribe(observer);
    }
}
