package io.reactivex.rxjava3.internal.operators.single;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.SingleSource;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Supplier;
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.reactivestreams.Publisher;

/* loaded from: classes8.dex */
public final class SingleInternalHelper {

    public enum NoSuchElementSupplier implements Supplier<NoSuchElementException> {
        INSTANCE;

        @Override // io.reactivex.rxjava3.functions.Supplier
        public NoSuchElementException get() {
            return new NoSuchElementException();
        }
    }

    public enum ToFlowable implements Function<SingleSource, Publisher> {
        INSTANCE;

        @Override // io.reactivex.rxjava3.functions.Function
        public Publisher apply(SingleSource v2) {
            return new SingleToFlowable(v2);
        }
    }

    public static final class ToFlowableIterable<T> implements Iterable<Flowable<T>> {
        private final Iterable<? extends SingleSource<? extends T>> sources;

        public ToFlowableIterable(Iterable<? extends SingleSource<? extends T>> sources) {
            this.sources = sources;
        }

        @Override // java.lang.Iterable
        public Iterator<Flowable<T>> iterator() {
            return new ToFlowableIterator(this.sources.iterator());
        }
    }

    public static final class ToFlowableIterator<T> implements Iterator<Flowable<T>> {
        private final Iterator<? extends SingleSource<? extends T>> sit;

        public ToFlowableIterator(Iterator<? extends SingleSource<? extends T>> sit) {
            this.sit = sit;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.sit.hasNext();
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Iterator
        public Flowable<T> next() {
            return new SingleToFlowable(this.sit.next());
        }
    }

    private SingleInternalHelper() {
        throw new IllegalStateException("No instances!");
    }

    public static Supplier<NoSuchElementException> emptyThrower() {
        return NoSuchElementSupplier.INSTANCE;
    }

    public static <T> Iterable<? extends Flowable<T>> iterableToFlowable(final Iterable<? extends SingleSource<? extends T>> sources) {
        return new ToFlowableIterable(sources);
    }

    public static <T> Function<SingleSource<? extends T>, Publisher<? extends T>> toFlowable() {
        return ToFlowable.INSTANCE;
    }
}
