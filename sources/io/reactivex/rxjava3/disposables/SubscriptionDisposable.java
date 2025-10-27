package io.reactivex.rxjava3.disposables;

import io.reactivex.rxjava3.annotations.NonNull;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
final class SubscriptionDisposable extends ReferenceDisposable<Subscription> {
    private static final long serialVersionUID = -707001650852963139L;

    public SubscriptionDisposable(Subscription value) {
        super(value);
    }

    @Override // io.reactivex.rxjava3.disposables.ReferenceDisposable
    public void onDisposed(@NonNull Subscription value) {
        value.cancel();
    }
}
