package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.disposables.EmptyDisposable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes8.dex */
public final class ObservablePublishSelector<T, R> extends AbstractObservableWithUpstream<T, R> {
    final Function<? super Observable<T>, ? extends ObservableSource<R>> selector;

    public static final class SourceObserver<T> implements Observer<T> {
        final PublishSubject<T> subject;
        final AtomicReference<Disposable> target;

        public SourceObserver(PublishSubject<T> subject, AtomicReference<Disposable> target) {
            this.subject = subject;
            this.target = target;
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            this.subject.onComplete();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable e2) {
            this.subject.onError(e2);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(T value) {
            this.subject.onNext(value);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable d3) {
            DisposableHelper.setOnce(this.target, d3);
        }
    }

    public static final class TargetObserver<R> extends AtomicReference<Disposable> implements Observer<R>, Disposable {
        private static final long serialVersionUID = 854110278590336484L;
        final Observer<? super R> downstream;
        Disposable upstream;

        public TargetObserver(Observer<? super R> downstream) {
            this.downstream = downstream;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.upstream.dispose();
            DisposableHelper.dispose(this);
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.upstream.isDisposed();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            DisposableHelper.dispose(this);
            this.downstream.onComplete();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable e2) {
            DisposableHelper.dispose(this);
            this.downstream.onError(e2);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(R value) {
            this.downstream.onNext(value);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable d3) {
            if (DisposableHelper.validate(this.upstream, d3)) {
                this.upstream = d3;
                this.downstream.onSubscribe(this);
            }
        }
    }

    public ObservablePublishSelector(final ObservableSource<T> source, final Function<? super Observable<T>, ? extends ObservableSource<R>> selector) {
        super(source);
        this.selector = selector;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer<? super R> observer) {
        PublishSubject publishSubjectCreate = PublishSubject.create();
        try {
            ObservableSource<R> observableSourceApply = this.selector.apply(publishSubjectCreate);
            Objects.requireNonNull(observableSourceApply, "The selector returned a null ObservableSource");
            ObservableSource<R> observableSource = observableSourceApply;
            TargetObserver targetObserver = new TargetObserver(observer);
            observableSource.subscribe(targetObserver);
            this.source.subscribe(new SourceObserver(publishSubjectCreate, targetObserver));
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            EmptyDisposable.error(th, observer);
        }
    }
}
