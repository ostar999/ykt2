package io.reactivex.rxjava3.internal.jdk8;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.jdk8.FlowableFromCompletionStage;
import java.util.concurrent.CompletionStage;
import java.util.function.BiConsumer;

/* loaded from: classes8.dex */
public final class CompletableFromCompletionStage<T> extends Completable {
    final CompletionStage<T> stage;

    public static final class CompletionStageHandler<T> implements Disposable, BiConsumer<T, Throwable> {
        final CompletableObserver downstream;
        final FlowableFromCompletionStage.BiConsumerAtomicReference<T> whenReference;

        public CompletionStageHandler(CompletableObserver downstream, FlowableFromCompletionStage.BiConsumerAtomicReference<T> whenReference) {
            this.downstream = downstream;
            this.whenReference = whenReference;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.function.BiConsumer
        public /* bridge */ /* synthetic */ void accept(Object item, Throwable error) {
            accept2((CompletionStageHandler<T>) item, error);
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.whenReference.set(null);
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.whenReference.get() == null;
        }

        /* renamed from: accept, reason: avoid collision after fix types in other method */
        public void accept2(T item, Throwable error) {
            if (error != null) {
                this.downstream.onError(error);
            } else {
                this.downstream.onComplete();
            }
        }
    }

    public CompletableFromCompletionStage(CompletionStage<T> stage) {
        this.stage = stage;
    }

    @Override // io.reactivex.rxjava3.core.Completable
    public void subscribeActual(CompletableObserver observer) {
        FlowableFromCompletionStage.BiConsumerAtomicReference biConsumerAtomicReference = new FlowableFromCompletionStage.BiConsumerAtomicReference();
        CompletionStageHandler completionStageHandler = new CompletionStageHandler(observer, biConsumerAtomicReference);
        biConsumerAtomicReference.lazySet(completionStageHandler);
        observer.onSubscribe(completionStageHandler);
        this.stage.whenComplete(biConsumerAtomicReference);
    }
}
