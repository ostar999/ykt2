package io.reactivex.rxjava3.internal.schedulers;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.disposables.b;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;
import io.reactivex.rxjava3.processors.FlowableProcessor;
import io.reactivex.rxjava3.processors.UnicastProcessor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes8.dex */
public class SchedulerWhen extends Scheduler implements Disposable {
    private final Scheduler actualScheduler;
    private Disposable disposable;
    private final FlowableProcessor<Flowable<Completable>> workerProcessor;
    static final Disposable SUBSCRIBED = new SubscribedDisposable();
    static final Disposable DISPOSED = b.a();

    public static final class CreateWorkerFunction implements Function<ScheduledAction, Completable> {
        final Scheduler.Worker actualWorker;

        public final class WorkerCompletable extends Completable {
            final ScheduledAction action;

            public WorkerCompletable(ScheduledAction action) {
                this.action = action;
            }

            @Override // io.reactivex.rxjava3.core.Completable
            public void subscribeActual(CompletableObserver actionCompletable) {
                actionCompletable.onSubscribe(this.action);
                this.action.call(CreateWorkerFunction.this.actualWorker, actionCompletable);
            }
        }

        public CreateWorkerFunction(Scheduler.Worker actualWorker) {
            this.actualWorker = actualWorker;
        }

        @Override // io.reactivex.rxjava3.functions.Function
        public Completable apply(final ScheduledAction action) {
            return new WorkerCompletable(action);
        }
    }

    public static class DelayedAction extends ScheduledAction {
        private final Runnable action;
        private final long delayTime;
        private final TimeUnit unit;

        public DelayedAction(Runnable action, long delayTime, TimeUnit unit) {
            this.action = action;
            this.delayTime = delayTime;
            this.unit = unit;
        }

        @Override // io.reactivex.rxjava3.internal.schedulers.SchedulerWhen.ScheduledAction
        public Disposable callActual(Scheduler.Worker actualWorker, CompletableObserver actionCompletable) {
            return actualWorker.schedule(new OnCompletedAction(this.action, actionCompletable), this.delayTime, this.unit);
        }
    }

    public static class ImmediateAction extends ScheduledAction {
        private final Runnable action;

        public ImmediateAction(Runnable action) {
            this.action = action;
        }

        @Override // io.reactivex.rxjava3.internal.schedulers.SchedulerWhen.ScheduledAction
        public Disposable callActual(Scheduler.Worker actualWorker, CompletableObserver actionCompletable) {
            return actualWorker.schedule(new OnCompletedAction(this.action, actionCompletable));
        }
    }

    public static class OnCompletedAction implements Runnable {
        final Runnable action;
        final CompletableObserver actionCompletable;

        public OnCompletedAction(Runnable action, CompletableObserver actionCompletable) {
            this.action = action;
            this.actionCompletable = actionCompletable;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                this.action.run();
            } finally {
                this.actionCompletable.onComplete();
            }
        }
    }

    public static abstract class ScheduledAction extends AtomicReference<Disposable> implements Disposable {
        public ScheduledAction() {
            super(SchedulerWhen.SUBSCRIBED);
        }

        public void call(Scheduler.Worker actualWorker, CompletableObserver actionCompletable) {
            Disposable disposable;
            Disposable disposable2 = get();
            if (disposable2 != SchedulerWhen.DISPOSED && disposable2 == (disposable = SchedulerWhen.SUBSCRIBED)) {
                Disposable disposableCallActual = callActual(actualWorker, actionCompletable);
                if (compareAndSet(disposable, disposableCallActual)) {
                    return;
                }
                disposableCallActual.dispose();
            }
        }

        public abstract Disposable callActual(Scheduler.Worker actualWorker, CompletableObserver actionCompletable);

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            getAndSet(SchedulerWhen.DISPOSED).dispose();
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return get().isDisposed();
        }
    }

    public static final class SubscribedDisposable implements Disposable {
        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return false;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public SchedulerWhen(Function<Flowable<Flowable<Completable>>, Completable> combine, Scheduler actualScheduler) {
        this.actualScheduler = actualScheduler;
        FlowableProcessor serialized = UnicastProcessor.create().toSerialized();
        this.workerProcessor = serialized;
        try {
            this.disposable = ((Completable) combine.apply(serialized)).subscribe();
        } catch (Throwable th) {
            throw ExceptionHelper.wrapOrThrow(th);
        }
    }

    @Override // io.reactivex.rxjava3.core.Scheduler
    @NonNull
    public Scheduler.Worker createWorker() {
        Scheduler.Worker workerCreateWorker = this.actualScheduler.createWorker();
        FlowableProcessor<T> serialized = UnicastProcessor.create().toSerialized();
        Flowable<Completable> map = serialized.map(new CreateWorkerFunction(workerCreateWorker));
        QueueWorker queueWorker = new QueueWorker(serialized, workerCreateWorker);
        this.workerProcessor.onNext(map);
        return queueWorker;
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public void dispose() {
        this.disposable.dispose();
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public boolean isDisposed() {
        return this.disposable.isDisposed();
    }

    public static final class QueueWorker extends Scheduler.Worker {
        private final FlowableProcessor<ScheduledAction> actionProcessor;
        private final Scheduler.Worker actualWorker;
        private final AtomicBoolean unsubscribed = new AtomicBoolean();

        public QueueWorker(FlowableProcessor<ScheduledAction> actionProcessor, Scheduler.Worker actualWorker) {
            this.actionProcessor = actionProcessor;
            this.actualWorker = actualWorker;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            if (this.unsubscribed.compareAndSet(false, true)) {
                this.actionProcessor.onComplete();
                this.actualWorker.dispose();
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.unsubscribed.get();
        }

        @Override // io.reactivex.rxjava3.core.Scheduler.Worker
        @NonNull
        public Disposable schedule(@NonNull final Runnable action, final long delayTime, @NonNull final TimeUnit unit) {
            DelayedAction delayedAction = new DelayedAction(action, delayTime, unit);
            this.actionProcessor.onNext(delayedAction);
            return delayedAction;
        }

        @Override // io.reactivex.rxjava3.core.Scheduler.Worker
        @NonNull
        public Disposable schedule(@NonNull final Runnable action) {
            ImmediateAction immediateAction = new ImmediateAction(action);
            this.actionProcessor.onNext(immediateAction);
            return immediateAction;
        }
    }
}
